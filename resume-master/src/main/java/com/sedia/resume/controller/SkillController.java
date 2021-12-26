package com.sedia.resume.controller;

import com.sedia.resume.entity.SkillEntity;
import com.sedia.resume.service.SkillService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillController {

    final SkillService service;

    // 取得所有技能列表
    @GetMapping("/skill")
    public List<SkillEntity> getSkillList() {
        return service.getSkillList();
    }

    // 取得一筆技能資料
    @GetMapping("/skill/{id}")
    public SkillEntity getSkill(@PathVariable int id) {
        return service.getSkill(id);
    }

    // 新增技能資料
    @PostMapping("/skill")
    public boolean createSkill(@RequestBody SkillEntity skill) {
        return service.insertSkill(skill);
    }

    // 修改技能資料
    @PutMapping("/skill")
    public boolean updateSkill(@RequestBody SkillEntity skill) {
        return service.updateSkill(skill);
    }

    // 刪除技能資料
    @DeleteMapping("/skill/{id}")
    public boolean deleteSkill(@PathVariable int id) {
        return service.deleteSkill(id);
    }

    @PutMapping("/skill/replace")
    public boolean replaceSkill(@RequestBody List<SkillEntity> skills) {
        return service.replaceSkill(skills);
    }

}
