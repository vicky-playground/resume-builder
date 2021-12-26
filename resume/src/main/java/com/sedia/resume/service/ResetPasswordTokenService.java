package com.sedia.resume.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sedia.resume.entity.ResetPasswordTokenEntity;
import com.sedia.resume.repository.ResetPasswordTokenMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordTokenService {

    final ResetPasswordTokenMapper resetPasswordTokenMapper;

    // token存入DB
    public boolean save(ResetPasswordTokenEntity reset) {

        try {
            reset.setToken(reset.getToken());
            // EXPIRY_DATE = 24小時
            int expiryDate = 60 * 24;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calendar.getTime());
            calendar.add(Calendar.MINUTE, expiryDate);
            Date deadline = new Date(calendar.getTime().getTime());
            java.time.LocalDateTime ldt = deadline.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            reset.setExpiryDate(ldt);
            reset.setUsed(false);
            LocalDateTime now = LocalDateTime.now();
            reset.setCreateDate(now);
            reset.setUpdateDate(now);
            resetPasswordTokenMapper.save(reset);
            return true;
        } catch (Exception e) {
            log.error("token儲存失敗", e);
            return false;
        }

    }

}
