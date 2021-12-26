package com.sedia.resume.repository;

import com.sedia.resume.entity.EducationEntity;

import java.util.List;
import java.util.Optional;

public interface EducationMapper {

    // 查詢第一個符合資料
    Optional<EducationEntity> firstEducation(int id, int uid);

    // 學歷資料表 查詢資料清單
    List<EducationEntity> listEducation(int uid);

    // 學歷資料表 新增資料
    void insertEducation(EducationEntity user);

    // 學歷資料表 修改資料
    void updateEducation(EducationEntity user);

    // 學歷資料表 刪除資料
    void deleteEducation(int id, int uid);

    // 學歷資料表 是否已存在
    boolean isExistEducation(int id, int uid);

}
