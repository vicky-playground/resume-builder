
package com.sedia.resume.service;

import com.sedia.resume.entity.LanguageEntity;
import com.sedia.resume.entity.UserEntity;
import com.sedia.resume.repository.LanguageMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.sedia.resume.exception.ApiException;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LanguageService {

    final LanguageMapper languageMapper;
    final UserService userService;

    // 檢查語言ID是否存在
    public boolean checkLanguage(int id, int uid) {
        if (languageMapper.isExistLanguage(id, uid))
            return true;
        else
            return false;

    }

    // 取得語言
    public LanguageEntity getLanguage(int id) {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return languageMapper.firstLanguage(id, uid).orElseThrow(() -> new ApiException("找不到語言"));

    }

    // 取得語言清單技能
    public List<LanguageEntity> getLanguageList() {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return languageMapper.listLanguage(uid);

    }

    // 編輯語言
    public boolean updateLanguage(LanguageEntity language) {
        try {
            // 取得當前使用者token
            UserEntity currentUser = userService.getCurrentUser();
            // 讓取得使用者的uid
            language.setUid(currentUser.getId());
            // 取得更新技能的updateUser
            language.setUpdateUser(currentUser.getAccount());
            // 取得更新技能的updateDateTime(時分秒)
            language.setUpdateDate(LocalDateTime.now());
            languageMapper.updateLanguage(language);
            return true;
        } catch (Exception e) {
            log.error("更新失敗");
            return false;
        }

    }

    // 新增語言
    public boolean insertLanguage(LanguageEntity language) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            language.setUid(currentUser.getId());
            // 取得cr_user與cr_localdatetime
            language.setCreateUser(currentUser.getAccount());
            language.setCreateDate(LocalDateTime.now());
            languageMapper.insertLanguage(language);
            return true;
        } catch (Exception e) {
            log.error("新增失敗", e);
            return false;
        }

    }

    // 刪除語言
    public boolean deleteLanguage(int id) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            int uid = currentUser.getId();
            languageMapper.deleteLanguage(id, uid);
            return true;
        } catch (Exception e) {
            log.error("刪除失敗", e);
            return false;
        }

    }

    public boolean replaceLanguage(List<LanguageEntity> languages) {
        try {

            List<Integer> originalLanguage = getLanguageList().stream().map(LanguageEntity::getId)
                    .collect(Collectors.toList());
            originalLanguage.retainAll(languages.stream().filter(language -> language.getId() != null)
                    .map(LanguageEntity::getId).collect(Collectors.toList()));

            if (CollectionUtils.isEmpty(originalLanguage)) {
                getLanguageList().forEach(language -> this.deleteLanguage(language.getId()));
            } else {
                languageMapper.getLanguageNotInIds(originalLanguage)
                        .forEach(language -> this.deleteLanguage(language.getId()));
            }

            languages.forEach(language -> {
                if (language.getId() != null) {
                    updateLanguage(language);
                } else {
                    insertLanguage(language);
                }
            });

            return true;
        } catch (Exception e) {
            log.error("修改失敗", e);
            return false;
        }
    }

}
