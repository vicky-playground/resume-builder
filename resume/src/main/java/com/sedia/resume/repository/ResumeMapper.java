package com.sedia.resume.repository;

import java.util.List;
import java.util.Optional;

import com.sedia.resume.entity.ResumeEntity;

public interface ResumeMapper {

    Optional<ResumeEntity> firstResume(int id, int uid);

    List<ResumeEntity> listResume(int uid);

    void insertResume(ResumeEntity resume);

    void updateResume(ResumeEntity resume);

    void deleteResume(int id, int uid);

    void updateBasicInfo(String basicInfo, int id, int uid);

    void updateFilePath(String filePath, int id, int uid);

    void updateBio(int id, int uid, boolean bioCh, boolean bioEn);

    void updateTemplateId(int id, int uid, int tid);

}
