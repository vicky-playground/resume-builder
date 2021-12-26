package com.sedia.resume.repository;

import com.sedia.resume.entity.LanguageEntity;

import java.util.List;
import java.util.Optional;

public interface LanguageMapper {

    // 查詢第一個符合資料
    Optional<LanguageEntity> firstLanguage(int id, int uid);

    // 語言資料表 查詢資料清單
    List<LanguageEntity> listLanguage(int uid);

    // 語言資料表 新增資料
    void insertLanguage(LanguageEntity user);

    // 語言資料表 修改資料
    void updateLanguage(LanguageEntity user);

    // 語言資料表 刪除資料
    void deleteLanguage(int id, int uid);

    // 語言資料表 是否已存在
    boolean isExistLanguage(int id, int uid);

    List<LanguageEntity> getLanguageNotInIds(List<Integer> ids);

}
