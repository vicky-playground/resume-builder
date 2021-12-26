package com.sedia.resume.repository;

import com.sedia.resume.entity.ResetPasswordTokenEntity;

public interface ResetPasswordTokenMapper {

    ResetPasswordTokenEntity checkToken(String token);

    void resetPassword(ResetPasswordTokenEntity currentToken);

    void save(ResetPasswordTokenEntity newUserToken);
}
