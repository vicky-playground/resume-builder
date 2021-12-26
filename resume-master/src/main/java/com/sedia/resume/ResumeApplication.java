package com.sedia.resume;

import com.sedia.resume.utils.EncryptUtils;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
@Slf4j
@EnableEncryptableProperties
@MapperScan(basePackages = "com.sedia.resume.repository")
public class ResumeApplication implements CommandLineRunner {

    @Autowired
    EncryptUtils encryptUtils;

    public static void main(String[] args) {
        SpringApplication.run(ResumeApplication.class, args);
    }

    // properties 加密 https://zhuanlan.zhihu.com/p/136828364
    @Override
    public void run(String... args) {

        String row = ""; // 要加密的字串

        Optional.of(row).filter(StringUtils::isNotEmpty).map(encryptUtils::encrypt)
                .ifPresent(result -> log.info("Encrypt result: {}", result));
    }
}
