package com.sedia.resume.repository;

import com.sedia.resume.entity.SkillEntity;

import java.util.List;
import java.util.Optional;

public interface SkillMapper {

    // 查詢第一個符合資料
    Optional<SkillEntity> firstSkill(int id, int uid);

    // 技能資料表 查詢資料清單
    List<SkillEntity> listSkill(int uid);

    // 技能資料表 新增資料
    void insertSkill(SkillEntity user);

    // 技能資料表 修改資料
    void updateSkill(SkillEntity user);

    // 技能資料表 刪除資料
    void deleteSkill(int id, int uid);

    // 技能資料表 是否已存在
    boolean isExistSkill(int id, int uid);

    List<SkillEntity> getSkillNotInIds(List<Integer> ids);
}
