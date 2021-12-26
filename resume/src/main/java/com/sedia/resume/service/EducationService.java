
package com.sedia.resume.service;

import com.sedia.resume.entity.EducationEntity;
import com.sedia.resume.repository.EducationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.sedia.resume.entity.UserEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import com.sedia.resume.exception.ApiException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EducationService {

    final EducationMapper educationMapper;
    final UserService userService;

    // 檢查學歷ID是否存在
    public boolean checkEducationID(int id, int uid) {
        if (educationMapper.isExistEducation(id, uid))
            return true;
        else
            return false;
    }

    // 取得學歷
    public EducationEntity getEducation(int id) {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return educationMapper.firstEducation(id, uid).orElseThrow(() -> new ApiException("找不到學歷"));

    }

    // 取得學歷清單
    public List<EducationEntity> getEducationList() {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return educationMapper.listEducation(uid);

    }

    // 編輯學歷
    public boolean updateEducation(EducationEntity school) {
        try {
            // 取得當前使用者token
            UserEntity currentUser = userService.getCurrentUser();
            // 讓取得使用者的uid
            school.setUid(currentUser.getId());
            // 取得更新技能的updateUser
            school.setUpdateUser(currentUser.getAccount());
            // 取得更新技能的updateDateTime(時分秒)
            school.setUpdateDate(LocalDateTime.now());
            educationMapper.updateEducation(school);
            return true;
        } catch (Exception e) {
            log.error("更新失敗");
            return false;
        }

    }

    // 新增學歷
    public boolean insertEducation(EducationEntity school) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            school.setUid(currentUser.getId());
            // 取得cr_user與cr_localdatetime
            school.setCreateUser(currentUser.getAccount());
            school.setCreateDate(LocalDateTime.now());
            educationMapper.insertEducation(school);
            return true;
        } catch (Exception e) {
            log.error("新增失敗", e);
            return false;
        }

    }

    // 刪除學歷
    public boolean deleteEducation(int id) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            int uid = currentUser.getId();
            educationMapper.deleteEducation(id, uid);
            return true;
        } catch (Exception e) {
            log.error("刪除失敗", e);
            return false;
        }

    }

}
