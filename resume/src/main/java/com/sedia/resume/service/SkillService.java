
package com.sedia.resume.service;

import com.sedia.resume.entity.SkillEntity;
import com.sedia.resume.entity.UserEntity;
import com.sedia.resume.exception.ApiException;
import com.sedia.resume.repository.SkillMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SkillService {

    final SkillMapper skillMapper;
    final UserService userService;

    // 檢查技能是否存在
    public boolean checkSkill(int id, int uid) {
        if (skillMapper.isExistSkill(id, uid))
            return true;
        else
            return false;
    }

    // 取得技能
    public SkillEntity getSkill(int id) {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return skillMapper.firstSkill(id, uid).orElseThrow(() -> new ApiException("找不到技能"));

    }

    // 取得技能清單技能
    public List<SkillEntity> getSkillList() {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return skillMapper.listSkill(uid);

    }

    // 編輯技能
    public boolean updateSkill(SkillEntity skill) {
        try {
            // 取得當前使用者token
            UserEntity currentUser = userService.getCurrentUser();
            // 讓取得使用者的uid
            skill.setUid(currentUser.getId());
            // 取得更新技能的updateUser
            skill.setUpdateUser(currentUser.getAccount());
            // 取得更新技能的updateDateTime(時分秒)
            skill.setUpdateDate(LocalDateTime.now());
            skillMapper.updateSkill(skill);
            return true;
        } catch (Exception e) {
            log.error("更新失敗");
            return false;
        }

    }

    // 新增技能
    public boolean insertSkill(SkillEntity skill) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            skill.setUid(currentUser.getId());
            // 取得create user與create localdatetime
            skill.setCreateUser(currentUser.getAccount());
            skill.setCreateDate(LocalDateTime.now());
            skillMapper.insertSkill(skill);
            return true;
        } catch (Exception e) {
            log.error("新增失敗", e);
            return false;
        }

    }

    // 刪除技能
    public boolean deleteSkill(int id) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            int uid = currentUser.getId();
            skillMapper.deleteSkill(id, uid);
            return true;
        } catch (Exception e) {
            log.error("刪除失敗", e);
            return false;
        }

    }

    public boolean replaceSkill(List<SkillEntity> skills) {
        try {
            List<Integer> originalSkill = getSkillList().stream().map(SkillEntity::getId).collect(Collectors.toList());
            originalSkill.retainAll(skills.stream().filter(skill -> skill.getId() != null).map(SkillEntity::getId)
                    .collect(Collectors.toList()));

            if (CollectionUtils.isEmpty(originalSkill)) {
                getSkillList().forEach(skill -> this.deleteSkill(skill.getId()));
            } else {
                skillMapper.getSkillNotInIds(originalSkill).forEach(skill -> this.deleteSkill(skill.getId()));
            }

            skills.forEach(skill -> {
                if (skill.getId() != null) {
                    updateSkill(skill);
                } else {
                    insertSkill(skill);
                }
            });
            return true;
        } catch (Exception e) {
            log.error("修改失敗", e);
            return false;
        }
    }

}
