package com.sedia.resume.repository;

import com.sedia.resume.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserMapper {

    List<UserEntity> findAll();

    Optional<UserEntity> findByAccount(String account);

    Optional<UserEntity> findById(int id);

    void save(UserEntity user);

    void update(UserEntity user);

    void updateAutobiography(UserEntity user);

    String loadImg(int id);// return the img_path

    // 上傳圖片
    void upLoadImg(UserEntity user);

    // 修改密碼
    void resetPassword(UserEntity user);

}
