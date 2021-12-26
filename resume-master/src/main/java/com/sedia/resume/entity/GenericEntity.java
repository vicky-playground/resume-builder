package com.sedia.resume.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import java.time.LocalDateTime;

@Data
class GenericEntity {

    @Hidden
    private Integer id;

    @Hidden
    private String createUser;

    @Hidden
    private LocalDateTime createDate;

    @Hidden
    private String updateUser;

    @Hidden
    private LocalDateTime updateDate;

}
