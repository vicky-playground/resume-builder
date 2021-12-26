package com.sedia.resume.service;

import com.sedia.resume.domain.AutobiographyRequest;
import com.sedia.resume.domain.ResetPasswordRequest;
import com.sedia.resume.entity.LinkEntity;
import com.sedia.resume.entity.ResetPasswordTokenEntity;
import com.sedia.resume.entity.UserEntity;
import com.sedia.resume.exception.ApiException;
import com.sedia.resume.repository.LinkMapper;
import com.sedia.resume.repository.ResetPasswordTokenMapper;
import com.sedia.resume.repository.UserMapper;

import com.sedia.resume.utils.AwsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FilenameUtils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    final BCryptPasswordEncoder passwordEncoder;
    final UserMapper userMapper;
    final LinkMapper linkMapper;
    final AwsUtils awsUtils;
    final ResetPasswordTokenMapper resetPasswordTokenMapper;

    final ResetPasswordTokenMapper passwordTokenMapper;

    public UserEntity getCurrentUser() {
        String account = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userMapper.findByAccount(account).orElseThrow(() -> new ApiException("找不到使用者"));
    }

    public List<UserEntity> getUsers() {
        return userMapper.findAll();
    }

    public UserEntity getUserById(int id) {
        return userMapper.findById(id).orElseThrow(() -> new ApiException("找不到 User"));
    }

    public UserEntity save(UserEntity user) {
        if (userMapper.findByAccount(user.getAccount()).isEmpty()) {
            LocalDateTime now = LocalDateTime.now();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreateDate(now);
            user.setCreateUser(user.getAccount());
            user.setUpdateDate(now);
            user.setUpdateUser(user.getAccount());
            user.setEmail(user.getAccount());
            userMapper.save(user);
            return user;
        }
        throw new ApiException("account already exists");
    }

    public UserEntity getUser() {
        UserEntity currentUser = getCurrentUser();
        currentUser.setLinks(linkMapper.findAll(currentUser.getId()));
        return currentUser;
    }

    public boolean updateBasicInfo(UserEntity user) {
        LocalDateTime now = LocalDateTime.now();

        try {
            UserEntity currentUser = getCurrentUser();
            user.setId(currentUser.getId());
            user.setUpdateDate(now);
            user.setUpdateUser(user.getAccount());
            userMapper.update(user);

            List<LinkEntity> links = user.getLinks();
            List<Integer> originalLinkIds = linkMapper.findAll(user.getId()).stream().mapToInt(LinkEntity::getId)
                    .boxed().collect(Collectors.toList());
            links.forEach(link -> {
                if (link.getId() == null) {
                    link.setUid(currentUser.getId());
                    link.setCreateDate(now);
                    link.setCreateUser(user.getAccount());
                    link.setUpdateDate(now);
                    link.setUpdateUser(user.getAccount());
                    linkMapper.save(link);
                } else {
                    link.setUpdateDate(now);
                    linkMapper.update(link);
                    originalLinkIds.remove(link.getId());
                }
            });
            originalLinkIds.forEach(id -> linkMapper.delete(id, user.getId()));
            return true;
        } catch (Exception e) {
            log.error("更新失敗", e);
            return false;
        }
    }

    public boolean updateAutobiography(AutobiographyRequest request) {
        try {
            UserEntity currentUser = getCurrentUser();
            currentUser.setBioEng(request.getEnglish());
            currentUser.setBioChn(request.getChinese());
            currentUser.setUpdateDate(LocalDateTime.now());
            userMapper.updateAutobiography(currentUser);
            return true;
        } catch (Exception e) {
            log.error("更新自傳失敗", e);
            return false;
        }
    }

    public String getImgById(int id) {
        return userMapper.loadImg(id);

    }

    // * 1. 將圖片存到 resources/user/{account}/profile/{檔案名稱} (以後要換到 AWS S3 上)
    // * 2. 檔案名稱要替換，上傳新的圖片，舊的要刪掉
    // * 3. 儲存路徑寫入 user imgPath 欄位
    // * 4. 回傳成功 or 失敗
    // UserController for upload
    public boolean uptoS3(MultipartFile image) throws IOException {

        UserEntity currentUser = this.getCurrentUser();

        // 宣告預設檔名、根目錄、預設目錄、取得的圖片格式
        int uid = currentUser.getId();
        String rootPath = "user" + uid + "/";
        String fileName = "profile.jpg";

        boolean result = awsUtils.uploadFileToS3(rootPath + fileName, image.getInputStream());

        if (result) {
            currentUser.setImgPath(rootPath + fileName);
            currentUser.setUpdateUser(currentUser.getAccount());
            currentUser.setUpdateDate(LocalDateTime.now());
            userMapper.upLoadImg(currentUser);
            return true;
        }
        return false;

    }

    // image.transferTo(new File(name + "." + ext));將接收到的文件傳輸到給定的目標文件
    // #The default implementation simply copies the file input

    // 確認圖片符合格式jpg,gif,png,bmp
    private boolean checkImgExt(MultipartFile image) {
        // 獲得上傳檔案副檔名
        String imageformat = FilenameUtils.getExtension(image.getOriginalFilename());
        switch (imageformat) {
        case ("jpg"):
            return true;
        case ("gif"):
            return true;
        case ("png"):
            return true;
        case ("bmp"):
            return true;
        default:
            return false;
        }
    }

    // 上傳檔案，不統一格式
    private BufferedOutputStream reImgName(InputStream input, String newname) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(input);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newname));

        int result;
        while ((result = bis.read()) != -1) { // 從輸入串流讀取資料
            bos.write(result); // 將讀取的資料寫出至輸出串流
        }
        // 將緩衝區的資料全部寫出
        bos.flush();

        bis.close();
        bos.close();

        return bos;

    }

    // 1. 根據 token 從 DB 查出 reset password token
    // 2. 檢查該筆 token 還在有效期內且沒有被使用過
    // 3. 回傳有效或無效
    public boolean checkTokenMessage(String token) {

        ResetPasswordTokenEntity currentToken = passwordTokenMapper.checkToken(token);

        Optional<UserEntity> checkUser = userMapper.findById(currentToken.getUid());

        Duration duration = Duration.between(LocalDateTime.now(), currentToken.getExpiryDate());
        // 1:時效超過24與否、2:isUsed()為true與false、3:不在現有資料庫中
        if (duration.toNanos() > 0 && !currentToken.isUsed()) {
            return true;
        }

        else
            return false;

    }

    // 1. 根據 token 從 DB 查出 reset password token
    // 2. 檢查該筆 token 還在有效期內且沒有被使用過
    // 3. 變更使用者密碼，需要加密，參考 save user
    // 4. 修改 token 為已使用
    // 5. 回傳重置密碼是否成功
    public boolean resetPasswordMessage(ResetPasswordRequest request) {

        // 確認token有效性 時效或未使用
        if (checkTokenMessage(request.getToken())) {

            // 取得DB內的token。可能為空值或有值
            ResetPasswordTokenEntity currentToken = passwordTokenMapper.checkToken(request.getToken());

            // 使用token內的uid查詢DB。token如未使用，uid可能為空
            // 取得符合uid的USER。token內的uid為空該如何處理?代表此token為新token，也代表未經過send，不合法的token

            // 取得使用者Entity
            Optional<UserEntity> checkUser = userMapper.findById(currentToken.getUid());
            UserEntity currentUser = checkUser.get();

            // 變更使用者密碼
            currentUser.setPassword(passwordEncoder.encode(request.getPassword()));
            currentUser.setUpdateUser(currentUser.getAccount());
            currentUser.setUpdateDate(LocalDateTime.now());
            userMapper.resetPassword(currentUser);

            // 修改token為已使用
            currentToken.setUsed(true);
            currentToken.setUpdateUser(currentUser.getAccount());
            currentToken.setUpdateDate(LocalDateTime.now());
            passwordTokenMapper.resetPassword(currentToken);

            return true;
        } else
            return false;

    }

}
