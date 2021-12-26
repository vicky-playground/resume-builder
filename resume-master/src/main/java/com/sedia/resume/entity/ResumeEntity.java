package com.sedia.resume.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResumeEntity extends GenericEntity {

    private int uid;
    private int templateID;
    private String resumeName;
    private String basicInfo;
    private String filePath;
    private boolean bioCh;
    private boolean bioEn;

}
