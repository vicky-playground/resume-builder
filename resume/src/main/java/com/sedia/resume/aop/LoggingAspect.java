package com.sedia.resume.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* *..controller*..*(..))")
    public void before(JoinPoint joinPoint) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            log.info("呼叫 API:{}.{} 來自IP: [{}], 參數: [{}]", joinPoint.getTarget().getClass().getSimpleName(),
                    joinPoint.getSignature().getName(), request.getRemoteAddr(), joinPoint.getArgs());

        } catch (Exception e) {
            log.error("AOP 發生錯誤, [{}]", ExceptionUtils.getRootCause(e).getMessage());
        }
    }

}
