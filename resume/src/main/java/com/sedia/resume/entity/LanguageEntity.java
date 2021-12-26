package com.sedia.resume.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LanguageEntity extends GenericEntity {

    // user ID
    private int uid;

    /* Language info */

    // 語言
    private String language;

    // 聽力 - 3:精通、2:中等、1:略懂
    private String listening;

    // 口說 - 3:精通、2:中等、1:略懂
    private String speaking;

    // 閱讀 - 3:精通、2:中等、1:略懂
    private String reading;

    // 寫作 - 3:精通、2:中等、1:略懂
    private String writing;

}
