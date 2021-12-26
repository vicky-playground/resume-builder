package com.sedia.resume.controller;

import com.sedia.resume.domain.BioRequest;
import com.sedia.resume.domain.ResumeRelation;
import com.sedia.resume.entity.ResumeEntity;
import com.sedia.resume.entity.UserEntity;
import com.sedia.resume.service.ResumeService;

import com.sedia.resume.service.UserService;
import com.sedia.resume.utils.AwsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {
    final ResumeService resumeService;
    final AwsUtils awsUtils;
    final UserService userService;

    @GetMapping
    public List<ResumeEntity> getResumes() {

        return resumeService.getResumeList();
    }

    @GetMapping("/{id}")
    public ResumeEntity getResume(@PathVariable int id) {
        return resumeService.getResume(id);
    }

    @PostMapping
    public int saveResume(@RequestBody ResumeEntity resume) {
        return resumeService.insertResume(resume);
    }

    @PutMapping
    public boolean updateResume(@RequestBody ResumeEntity resume) {
        return resumeService.updateResume(resume);
    }

    @DeleteMapping("/{id}")
    public boolean deleteResume(@PathVariable int id) {
        return resumeService.deleteResume(id);
    }

    @PutMapping("/{id}/basic-info") // 要套用的資料
    public boolean updateBasicInfo(@PathVariable int id, @RequestBody List<String> basicInfoType) {
        // TODO 更新SQL resume 的 BASIC_INFO_COLUMNS (逗號隔開)
        // List<String> -> String, 放欄位名稱 , ex: ["aaa", "bbb", "ccc"] -> "aaa,bbb,ccc"

        return resumeService.updateBasicInfo(id, basicInfoType);
    }

    @PutMapping("/{id}/template/{tid}")
    public boolean updateTemplateId(@PathVariable int id, @PathVariable int tid) {

        return resumeService.updateTemplateId(id, tid);
    }

    // ====================================================================================
    @PutMapping("/{id}/education")
    public boolean updateEducation(@PathVariable int id, @RequestBody List<Integer> educationId) {
        // TODO 存到 rel_resume_edu table
        return resumeService.updateResumeEducation(id, educationId);
    }

    @PutMapping("/{id}/experience")
    public boolean updateExperience(@PathVariable int id, @RequestBody List<Integer> experienceId) {

        return resumeService.updateResumeExperience(id, experienceId);
    }

    @PutMapping("/{id}/language")
    public boolean updateLanguage(@PathVariable int id, @RequestBody List<Integer> languageId) {
        return resumeService.updateResumeLanguage(id, languageId);
    }

    @PutMapping("/{id}/license")
    public boolean updateLicense(@PathVariable int id, @RequestBody List<Integer> licenseId) {
        return resumeService.updateResumeLicense(id, licenseId);
    }

    @PutMapping("/{id}/skill")
    public boolean updateSkill(@PathVariable int id, @RequestBody List<Integer> skillId) {
        return resumeService.updateResumeSkill(id, skillId);
    }

    @PutMapping("/{id}/bio")
    public boolean updateBio(@PathVariable int id, @RequestBody BioRequest bioRequest) {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return resumeService.updateBio(id, uid, bioRequest.isBioCh(), bioRequest.isBioEn());
    }

    @PutMapping("/apply/{resumeId}")
    public boolean applyResume(@PathVariable int resumeId) throws Exception {

        return resumeService.applyResume(resumeId);

    }

    @GetMapping("/{id}/relation")
    public ResumeRelation getResumeRelations(@PathVariable int id) {
        return resumeService.getResumeRelation(id);
    }

    @PostMapping("/download/{resumeId}")
    public ResponseEntity<InputStreamResource> downloadResume(@PathVariable int resumeId) {

        ResumeEntity resume = resumeService.getResume(resumeId);

        InputStream source = awsUtils.downloadFileFromS3(resume.getFilePath());
        InputStreamResource resource = new InputStreamResource(source);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resume.getResumeName() + ".pdf");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

}
