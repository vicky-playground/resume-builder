package com.sedia.resume.controller;

import com.sedia.resume.domain.AutobiographyRequest;
import com.sedia.resume.domain.ResetPasswordRequest;
import com.sedia.resume.entity.ResetPasswordTokenEntity;
import com.sedia.resume.entity.UserEntity;
import com.sedia.resume.repository.ResetPasswordTokenMapper;
import com.sedia.resume.repository.UserMapper;
import com.sedia.resume.service.ResetPasswordTokenService;
import com.sedia.resume.service.UserService;
import com.sedia.resume.utils.AwsUtils;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user") // 在user根路徑
public class UserController {

    final UserService service;
    final UserMapper userMapper;
    final ResetPasswordTokenMapper resetPasswordTokenMapper;
    final ResetPasswordTokenService resetPasswordTokenService;
    final AwsUtils awsUtils;

    @GetMapping("/all")
    public List<UserEntity> getAllUser() {
        return service.getUsers();
    }

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return service.save(user);
    }

    @PutMapping("/basic-info")
    public boolean updateBasicInfo(@RequestBody UserEntity user) {
        log.debug("Update basic info for: {}", user);
        return service.updateBasicInfo(user);
    }

    @GetMapping
    public UserEntity getUser() {
        return service.getUser();
    }

    @PutMapping("/autobiography")
    public boolean updateAutobiography(@RequestBody AutobiographyRequest request) {
        return service.updateAutobiography(request);
    }

    @PostMapping(value = "/image/upload", consumes = "multipart/form-data")
    public boolean uploadImage(@RequestParam("image") MultipartFile image) throws IOException {

        return service.uptoS3(image);
    }

    @GetMapping("/image")
    public void getImage(HttpServletResponse response) throws IOException {
        int id = service.getCurrentUser().getId();
        String imgPath = service.getImgById(id);
        if (StringUtils.isBlank(imgPath)) {
            response.getWriter().print("");
        } else {
            InputStream source = awsUtils.downloadFileFromS3(imgPath);
            // InputStreamResource resource = new InputStreamResource(source);

            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            IOUtils.copy(source, response.getOutputStream());

        }
    }

    @PostMapping("/send-token")
    public boolean sendToken(@RequestParam("email") String email, @Value("${sendgrid.api-key}") String sendGridKey,
            @Value("${resume.mail.from}") String from, @Value("${resume.mail.from-name}") String fromName,
            @Value("${resume.mail.frontend-host}") String host) throws Exception {

        // 根據輸入的 email 查詢 user (查詢條件是 account), 查不到就 throw ApiException (含 message)
        UserEntity user = userMapper.findByAccount(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // get uid
        int id = user.getId();

        String token = UUID.randomUUID().toString();

        // 存入一筆 reset password token 到 DB
        ResetPasswordTokenEntity reset = new ResetPasswordTokenEntity();
        reset.setUid(id);
        reset.setToken(token);
        reset.setCreateUser(user.getAccount());
        reset.setUpdateUser(user.getAccount());
        resetPasswordTokenService.save(reset);

        // 發送 email, 回傳 email 發送是否成功
        Email fr = new Email(from);
        Email to = new Email(user.getAccount());
        fr.setName(fromName);
        String subject = "Reset Password";
        String url = host + "/resetpw?token=" + token;
        log.debug("reset password url: {}", url);
        Content content = new Content("text/html", "<h2>請點擊連結：<a href='" + url + "'>重置你的密碼</a></h2>");

        Mail mail = new Mail(fr, subject, to, content);

        SendGrid sg = new SendGrid(sendGridKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);
        if (response.getStatusCode() >= 300) {
            log.debug("response code: {}", response.getStatusCode());
            return false;
        } else {
            log.debug("response code: {}", response.getStatusCode());
            return true;
        }

    }

    // 1. 根據 token 從 DB 查出 reset password token
    // 2. 檢查該筆 token 還在有效期內且沒有被使用過
    // 3. 回傳有效或無效
    @PostMapping("/check-token") // 檢查 token 是否有效，並且跳轉到修改密碼頁面
    public boolean checkToken(@RequestParam("token") String token) {
        return service.checkTokenMessage(token);
    }

    // 1. 根據 token 從 DB 查出 reset password token
    // 2. 檢查該筆 token 還在有效期內且沒有被使用過
    // 3. 變更使用者密碼，需要加密，參考 save user
    // 4. 修改 token 為已使用
    // 5. 回傳重置密碼是否成功
    @PostMapping("/reset-password")
    public boolean resetPassword(@RequestBody ResetPasswordRequest request) {
        return service.resetPasswordMessage(request);
    }

}
