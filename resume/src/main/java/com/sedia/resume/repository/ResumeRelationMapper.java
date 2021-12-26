package com.sedia.resume.repository;

import java.util.List;

public interface ResumeRelationMapper {

    List<Integer> getEducationIdByResumeId(int resumeId);

    List<Integer> getExperienceIdByResumeId(int resumeId);

    List<Integer> getLanguageIdByResumeId(int resumeId);

    List<Integer> getLicenseIdByResumeId(int resumeId);

    List<Integer> getSkillIdByResumeId(int resumeId);

    // resume學歷
    void insertResumeEducation(int rId, Integer eduId);

    void deleteResumeEducation(int rId);

    // resume經歷
    void insertResumeExperience(int rId, Integer expId);

    void deleteResumeExperience(int rId);

    // resume語言
    void insertResumeLanguage(int rId, Integer languageId);

    void deleteResumeLanguage(int rId);

    // resume證照
    void insertResumeLicense(int rId, Integer licenseId);

    void deleteResumeLicense(int rId);

    // resume技能
    void insertResumeSkill(int rId, Integer skillId);

    void deleteResumeSkill(int rId);
}
