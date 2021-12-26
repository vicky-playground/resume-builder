package com.sedia.resume.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExperienceEntity {
    /* 統一小駝峰式命名 */

    // 流水號
    private int sn;
    // userID
    private int uid;
    // 公司名稱
    private String companyName;
    // 職稱
    private String position;
    // 到職日
    // DB用timestamp,程式一樣用LocalDateTime
    // 是否需要改為LocalDate
    private String startDate;
    // 離職日
    private String endDate;
    // 職務簡介(100字)
    private String experienceDesc;
    // 技能(table表未定義明確型別)
    private String ability;
    // 建立人
    private String crUser;
    // 建立時間
    private LocalDateTime crDatetime;
    // 更新人
    private String upUser;
    // 更新時間
    private LocalDateTime upDatetime;

}
