package com.sedia.resume.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private String message;

    private String field;

    private String value;

}
