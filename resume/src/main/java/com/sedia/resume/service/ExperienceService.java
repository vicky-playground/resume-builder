package com.sedia.resume.service;

import com.sedia.resume.repository.ExperienceMapper;
import com.sedia.resume.entity.ExperienceEntity;
import com.sedia.resume.entity.UserEntity;
import com.sedia.resume.exception.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ExperienceService {

    final ExperienceMapper experienceMapper;
    final UserService userService;

    // 檢查經歷ID是否存在
    public boolean checkExperienceID(int sn, int uid) {
        if (experienceMapper.isExistExperience(sn, uid))
            return true;
        else
            return false;
    }

    // 取得經歷
    public ExperienceEntity getExperience(int sn) {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return experienceMapper.firstExperience(sn, uid).orElseThrow(() -> new ApiException("找不到 經歷"));
    }

    // 取得經歷清單
    public List<ExperienceEntity> getExperienceList() {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return experienceMapper.listExperience(uid);
    }

    // 編輯經歷
    public boolean editExperience(ExperienceEntity experience) {
        try {
            // 取得當前使用者token
            UserEntity currentUser = userService.getCurrentUser();
            // 讓取得使用者的uid
            experience.setUid(currentUser.getId());
            // //取得更新經歷的upUser
            experience.setUpUser(currentUser.getAccount());
            // //取得更新經歷的upDateTime(時分秒)
            experience.setUpDatetime(LocalDateTime.now());
            experienceMapper.updateExperience(experience);
            return true;
        } catch (Exception e) {
            log.error("更新失敗");
            return false;
        }

    }

    // 新增經歷
    public boolean insertExperience(ExperienceEntity experience) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            experience.setUid(currentUser.getId());
            // 取得cr_user與cr_localdatetime
            experience.setCrUser(currentUser.getAccount());
            experience.setCrDatetime(LocalDateTime.now());
            experienceMapper.insertExperience(experience);
            return true;
        } catch (Exception e) {
            log.error("新增失敗", e);
            return false;
        }

    }

    // 刪除經歷
    public boolean deleteExperience(int sn) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            int uid = currentUser.getId();
            experienceMapper.deleteExperience(sn, uid);
            return true;
        } catch (Exception e) {
            log.error("刪除失敗", e);
            return false;
        }

    }

    // //controller的儲存方法
    // /*針對介面的定義有些疑問，該如何介定那些是需要另外寫save這類方法的*/
    // public ExperienceEntity save(ExperienceEntity user) {
    // if (experienceMapper.firstExperience(user.getSn(),user.getUid()).isEmpty()) {
    //
    //// user.setCreateDate(LocalDateTime.now());
    //// license可用@setter,
    // experienceMapper.save(user);
    // return user;
    // }
    // throw new RuntimeException("License already exists");
    // }
}
