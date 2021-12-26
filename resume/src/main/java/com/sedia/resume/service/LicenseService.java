package com.sedia.resume.service;

import com.sedia.resume.repository.LicenseMapper;
import com.sedia.resume.entity.LicenseEntity;
import com.sedia.resume.entity.UserEntity;
import com.sedia.resume.exception.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LicenseService {

    // @RequiredArgsConstructor不需做初始化
    final LicenseMapper licenseMapper;
    final UserService userService;

    // 確認證照ID是否存在
    public boolean checkLicenseID(int sn, int uid) {
        if (licenseMapper.isExistLicense(sn, uid))
            return true;
        else
            return false;
    }

    // 取得證照
    public LicenseEntity getLicense(int sn) {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return licenseMapper.findFirstLicense(sn, uid).orElseThrow(() -> new ApiException("找不到 證照"));
    }

    // 取得證照清單
    public List<LicenseEntity> getLicenseList() {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return licenseMapper.listLicense(uid);
    }

    // 新增證照
    public boolean insertLicense(LicenseEntity license) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            license.setUid(currentUser.getId());
            license.setCrUser(currentUser.getAccount());
            license.setCrDatetime(LocalDateTime.now());
            licenseMapper.insertLicense(license);
            return true;
        } catch (Exception e) {
            log.error("新增失敗", e);
            return false;
        }
    }

    // 編輯證照
    // 畫面返回前端會再自己處理，不須回傳entity
    public boolean editLicense(LicenseEntity license) {
        try {
            // 取得當前使用者
            UserEntity currentUser = userService.getCurrentUser();
            // 讓使用者取得自身的證照
            license.setUid(currentUser.getId());
            // 取得更新證照的upUser
            license.setUpUser(currentUser.getAccount());
            // 取得更新經歷的upDateTime(時分秒)
            license.setUpDatetime(LocalDateTime.now());
            licenseMapper.updateLicense(license);
            return true;
        } catch (Exception e) {
            log.error("更新失敗", e);
            return false;
        }

    }

    // 刪除證照
    public boolean deleteLicense(int sn) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            int uid = currentUser.getId();
            licenseMapper.deleteLicense(sn, uid);
            return true;
        } catch (Exception e) {
            log.error("刪除失敗", e);
            return false;
        }
    }

    public boolean replaceLicense(List<LicenseEntity> licenses) {
        try {
            List<Integer> originalLicense = getLicenseList().stream().map(LicenseEntity::getSn)
                    .collect(Collectors.toList());
            originalLicense.retainAll(licenses.stream().filter(license -> license.getSn() != null)
                    .map(LicenseEntity::getSn).collect(Collectors.toList()));

            if (CollectionUtils.isEmpty(originalLicense)) {
                getLicenseList().forEach(license -> this.deleteLicense(license.getSn()));
            } else {
                licenseMapper.getLicenseNotInIds(originalLicense)
                        .forEach(license -> this.deleteLicense(license.getSn()));
            }

            licenses.forEach(license -> {
                if (license.getSn() != null) {
                    editLicense(license);
                } else {
                    insertLicense(license);
                }
            });
            return true;
        } catch (Exception e) {
            log.error("修改失敗", e);
            return false;
        }
    }

}
