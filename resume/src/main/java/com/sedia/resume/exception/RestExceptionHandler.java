package com.sedia.resume.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /** 攔截自定義的 exception，轉換成指定的 response 格式 */
    @ExceptionHandler(ApiException.class)
    public void applicationExceptionHandle(ApiException e) {
        log.error("發生自定義的錯誤。", e);
        throw e;
    }

    /** 攔截其他所有的 Exception */
    @ExceptionHandler(Exception.class)
    public void exceptionHandle(Exception e) throws Exception {
        log.error("發生預期外的錯誤。", e);
        throw e;
    }

}
