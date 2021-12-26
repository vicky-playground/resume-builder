package com.sedia.resume.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordTokenEntity extends GenericEntity {

    private int uid;

    private String token;

    private LocalDateTime expiryDate;

    private boolean isUsed;

}
