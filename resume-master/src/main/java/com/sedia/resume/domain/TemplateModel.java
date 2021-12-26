package com.sedia.resume.domain;

import com.sedia.resume.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateModel {

    Map<String, String> basicInfo;

    List<EducationEntity> educations;

    List<ExperienceEntity> experiences;

    List<LanguageEntity> languages;

    List<LicenseEntity> licenses;

    List<SkillEntity> skills;

    List<LinkEntity> links;

    String feature;

    String image = "images/avatar_default.jpg";

    String intro;

    String name;

    String bioCh;

    String bioEn;
}
