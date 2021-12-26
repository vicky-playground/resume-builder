package com.sedia.resume.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiException extends RuntimeException {

    private String message;

    private List<ApiError> errors;

    public ApiException(String message) {
        this.message = message;
    }

}