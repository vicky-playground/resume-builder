package com.sedia.resume.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateEntity extends GenericEntity {

    private String name;
    private int basicInfo;
    private int education;
    private int experience;
    private int skill;
    private int licence;
    private int language;
    private int feature;
    private int link;

}
