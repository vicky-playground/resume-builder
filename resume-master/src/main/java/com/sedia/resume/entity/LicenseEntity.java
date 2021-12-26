package com.sedia.resume.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder // 自動生成建構子
// 實體資料由前端及DB存取,資料庫的table
public class LicenseEntity {

    // DB給予流水號,自動增長
    private Integer sn;

    // 使用者帳號
    private int uid;

    // ,號區隔
    private String lsName;

    // 建立人
    private String crUser;

    // 建立日期
    private LocalDateTime crDatetime;

    // 更新人
    private String upUser;
    // 更新日期
    private LocalDateTime upDatetime;
}
