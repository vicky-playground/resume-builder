package com.sedia.resume.controller;

import com.sedia.resume.entity.EducationEntity;
import com.sedia.resume.entity.LanguageEntity;
import com.sedia.resume.service.EducationService;
import com.sedia.resume.service.UserService;
import com.sedia.resume.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EducationController {

    final EducationService service;

    // 取得使用者所有學歷列表
    @GetMapping("/education")
    public List<EducationEntity> getEducationList() {
        return service.getEducationList();
    }

    // 取得使用者一筆學歷資料
    @GetMapping("/education/{id}")
    public EducationEntity getEducation(@PathVariable int id) {
        return service.getEducation(id);
    }

    // 新增學歷資料
    @PostMapping("/education")
    public boolean createEducation(@RequestBody EducationEntity education) {
        return service.insertEducation(education);

    }

    // 修改學歷資料
    @PutMapping("/education")
    public boolean updateEducation(@RequestBody EducationEntity education) {
        return service.updateEducation(education);
    }

    // 刪除學歷資料
    @DeleteMapping("/education/{id}")
    public boolean deleteEducation(@PathVariable int id) {
        return service.deleteEducation(id);
    }

    /*
     * //確認資料是否存在 ??
     * 
     * @GetMapping("/education/{uid}") boolean isExistEducation(int uid) { return service.isExistEducation(uid); }
     * 
     */

}
