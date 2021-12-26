package com.sedia.resume.controller;

import com.sedia.resume.entity.LanguageEntity;
import com.sedia.resume.service.LanguageService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LanguageController {

    final LanguageService service;

    // 取得所有語言列表
    @GetMapping("/language")
    public List<LanguageEntity> getLanguageList() {
        return service.getLanguageList();
    }

    // 取得語言資料
    @GetMapping("/language/{id}")
    public LanguageEntity getLanguage(@PathVariable int id) {
        return service.getLanguage(id);
    }

    // 新增語言資料
    @PostMapping("/language")
    public boolean createSkill(@RequestBody LanguageEntity language) {
        return service.insertLanguage(language);
    }

    // 修改語言資料
    @PutMapping("/language")
    public boolean updateLanguage(@RequestBody LanguageEntity language) {
        return service.updateLanguage(language);
    }

    // 刪除語言資料
    @DeleteMapping("/language/{id}")
    public boolean deleteLanguage(@PathVariable int id) {

        return service.deleteLanguage(id);
    }

    @PutMapping("/language/replace")
    public boolean replaceLanguage(@RequestBody List<LanguageEntity> languages) {
        return service.replaceLanguage(languages);
    }

}
