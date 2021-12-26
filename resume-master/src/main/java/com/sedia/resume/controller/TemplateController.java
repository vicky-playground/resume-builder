package com.sedia.resume.controller;

import com.sedia.resume.entity.TemplateEntity;
import com.sedia.resume.service.TemplateService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/template")
public class TemplateController {

    final TemplateService service;

    @GetMapping
    public List<TemplateEntity> getTemplates() {

        return service.getTemplates();
    }

    @GetMapping("/{id}") // 傳入template的id
    public TemplateEntity getTemplate(@PathVariable int id) {

        return service.getTemplate(id);
    }

}
