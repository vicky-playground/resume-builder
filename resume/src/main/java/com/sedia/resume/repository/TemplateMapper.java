package com.sedia.resume.repository;

import java.util.List;
import java.util.Optional;

import com.sedia.resume.entity.TemplateEntity;

public interface TemplateMapper {
    // 查詢第一個符合資料
    Optional<TemplateEntity> firstTemplate(int id);

    // 查詢資料清單
    List<TemplateEntity> listTemplate();
}
