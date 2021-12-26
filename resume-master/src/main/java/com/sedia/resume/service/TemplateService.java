
package com.sedia.resume.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sedia.resume.entity.TemplateEntity;
import com.sedia.resume.entity.UserEntity;
import com.sedia.resume.exception.ApiException;
import com.sedia.resume.repository.TemplateMapper;

@Service
@Transactional
@RequiredArgsConstructor
public class TemplateService {

    final TemplateMapper templateMapper;
    final UserService userService;

    public TemplateEntity getTemplate(int id) {
        return templateMapper.firstTemplate(id).orElseThrow(() -> new ApiException("找不到技能"));

    }

    public List<TemplateEntity> getTemplates() {
        return templateMapper.listTemplate();
    }

}
