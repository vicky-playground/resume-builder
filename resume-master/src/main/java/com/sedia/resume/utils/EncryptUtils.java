package com.sedia.resume.utils;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptUtils {

    final StringEncryptor stringEncryptor;

    public String encrypt(String row) {
        return stringEncryptor.encrypt(row);
    }

}
