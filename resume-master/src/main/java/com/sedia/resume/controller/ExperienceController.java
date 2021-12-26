package com.sedia.resume.controller;

import com.sedia.resume.entity.ExperienceEntity;
import com.sedia.resume.entity.UserEntity;
import com.sedia.resume.service.ExperienceService;
import com.sedia.resume.service.UserService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.serviceloader.ServiceListFactoryBean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ExperienceController {

    final ExperienceService service;

    // 取得使用者所有經歷列表
    @GetMapping("/experience")
    public List<ExperienceEntity> getExperienceList() {
        return service.getExperienceList();
    }

    // 取得使用者一筆經歷
    @GetMapping("/experience/{sn}")
    public ExperienceEntity getExperience(@PathVariable int sn) {
        return service.getExperience(sn);
    }

    // 新增經歷列表
    @PostMapping("/experience")
    public boolean intserExperience(@RequestBody ExperienceEntity experience) {
        return service.insertExperience(experience);
    }

    // 更新經歷列表
    // 同一uid才能更新自身的經歷，同時sn在這邊已自動填入在Entity。
    @PutMapping("/experience")
    public boolean editexperience(@RequestBody ExperienceEntity experience) {
        return service.editExperience(experience);
    }

    // 刪除經歷列表
    // 同一uid才能刪除自身的經歷，同時他們不會知道自己uid,sn或別人uid,sn
    @DeleteMapping("/experience/{sn}")
    public boolean deleteExperience(@PathVariable int sn) {
        return service.deleteExperience(sn);
    }

}
