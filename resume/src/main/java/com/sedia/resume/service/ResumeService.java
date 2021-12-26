
package com.sedia.resume.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.font.FontProvider;
import com.sedia.resume.domain.ResumeRelation;
import com.sedia.resume.domain.TemplateModel;
import com.sedia.resume.entity.TemplateEntity;
import com.sedia.resume.repository.LinkMapper;
import com.sedia.resume.utils.AwsUtils;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sedia.resume.entity.ResumeEntity;
import com.sedia.resume.entity.UserEntity;
import com.sedia.resume.exception.ApiException;
import com.sedia.resume.repository.ResumeMapper;
import com.sedia.resume.repository.ResumeRelationMapper;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ResumeService {

    final ResumeMapper resumeMapper;
    final UserService userService;
    final ResumeRelationMapper resumeRelationMapper;
    final TemplateService templateService;
    final Configuration freemarker;
    final EducationService educationService;
    final ExperienceService experienceService;
    final LanguageService languageService;
    final LicenseService licenseService;
    final SkillService skillService;
    final AwsUtils awsUtils;
    final LinkMapper linkMapper;

    public ResumeEntity getResume(int id) {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return resumeMapper.firstResume(id, uid).orElseThrow(() -> new ApiException("找不到履歷"));

    }

    public List<ResumeEntity> getResumeList() {
        UserEntity currentUser = userService.getCurrentUser();
        int uid = currentUser.getId();
        return resumeMapper.listResume(uid);

    }

    public boolean updateResume(ResumeEntity resume) {
        try {

            UserEntity currentUser = userService.getCurrentUser();
            resume.setUid(currentUser.getId());
            resume.setUpdateUser(currentUser.getAccount());
            resume.setUpdateDate(LocalDateTime.now());
            resumeMapper.updateResume(resume);
            return true;
        } catch (Exception e) {
            log.error("更新失敗");
            return false;
        }

    }

    public int insertResume(ResumeEntity resume) {
        try {
            log.debug("{}", resume);
            UserEntity currentUser = userService.getCurrentUser();
            resume.setUid(currentUser.getId());
            // 取得cr_user與cr_localdatetime
            resume.setCreateUser(currentUser.getAccount());
            resume.setCreateDate(LocalDateTime.now());
            resumeMapper.insertResume(resume);
            return resume.getId();
        } catch (Exception e) {
            log.error("新增失敗", e);
            return 0;
        }

    }

    public boolean deleteResume(int id) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            int uid = currentUser.getId();
            resumeMapper.deleteResume(id, uid);
            return true;
        } catch (Exception e) {
            log.error("刪除失敗", e);
            return false;
        }

    }

    public boolean updateBasicInfo(int id, List<String> basicInfo) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            int uid = currentUser.getId();
            String UpdatebasicInfo = String.join(",", basicInfo);
            resumeMapper.updateBasicInfo(UpdatebasicInfo, id, uid);
            return true;
        } catch (Exception e) {
            log.error("更新失敗", e);
            return false;
        }
    }

    public boolean updateTemplateId(int id, int tid) {
        try {
            UserEntity currentUser = userService.getCurrentUser();
            int uid = currentUser.getId();
            resumeMapper.updateTemplateId(id, uid, tid);
            return true;
        } catch (Exception e) {
            log.error("更新失敗", e);
            return false;
        }
    }

    // 使用者挑選resumeId根據自己需要的eduId加入
    public boolean updateResumeEducation(int id, List<Integer> educationId) {
        // 資料庫rel_resume_edu table的RID跟EDU_ID都是PrimeKey
        resumeRelationMapper.deleteResumeEducation(id);

        for (int i = 0; i < educationId.size(); i++) {
            resumeRelationMapper.insertResumeEducation(id, educationId.get(i));
        }

        return true;
    }

    public boolean updateResumeExperience(int id, List<Integer> experienceId) {
        resumeRelationMapper.deleteResumeExperience(id);

        for (int i = 0; i < experienceId.size(); i++) {
            resumeRelationMapper.insertResumeExperience(id, experienceId.get(i));
        }

        return true;
    }

    public boolean updateResumeLanguage(int id, List<Integer> languageId) {
        resumeRelationMapper.deleteResumeLanguage(id);

        for (int i = 0; i < languageId.size(); i++) {
            resumeRelationMapper.insertResumeLanguage(id, languageId.get(i));
        }

        return true;
    }

    public boolean updateResumeLicense(int id, List<Integer> licenseId) {
        resumeRelationMapper.deleteResumeLicense(id);

        for (int i = 0; i < licenseId.size(); i++) {
            resumeRelationMapper.insertResumeLicense(id, licenseId.get(i));
        }

        return true;
    }

    public boolean updateResumeSkill(int id, List<Integer> skillId) {
        resumeRelationMapper.deleteResumeSkill(id);

        for (int i = 0; i < skillId.size(); i++) {
            resumeRelationMapper.insertResumeSkill(id, skillId.get(i));
        }

        return true;
    }

    public boolean updateBio(int id, int uid, boolean bioCh, boolean bioEn) {
        resumeMapper.updateBio(id, uid, bioCh, bioEn);
        return true;
    }

    public boolean applyResume(int resumeId) throws Exception {

        UserEntity user = userService.getCurrentUser();
        int userId = user.getId();

        ResumeEntity resume = resumeMapper.firstResume(resumeId, userId).orElseThrow();

        String[] basicInfoColumns = resume.getBasicInfo().split(",");

        TemplateEntity template = templateService.getTemplate(resume.getTemplateID());

        // TODO template size 判斷??

        Map<String, String> basicInfoMap = new LinkedHashMap<>();

        for (String infoColumn : basicInfoColumns) {
            switch (infoColumn) {
            case "name":
                basicInfoMap.put("姓名", user.getName());
                break;
            case "birthday":
                basicInfoMap.put("生日", user.getBirthday().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                break;
            case "sex":
                basicInfoMap.put("性別", user.getSex());
                break;
            case "phone":
                basicInfoMap.put("聯絡電話", user.getPhone());
                break;
            case "militaryService":
                basicInfoMap.put("兵役狀況", user.getMilitaryService());
                break;
            case "address":
                basicInfoMap.put("地址", user.getAddress());
                break;
            case "email":
                basicInfoMap.put("email", user.getEmail());
                break;
            case "driverLicense":
                basicInfoMap.put("駕照", user.getDriverLicense());
                break;
            case "specialIdentity":
                basicInfoMap.put("特殊身分", user.getSpecialIdentity());
                break;
            }
        }

        TemplateModel templateModel = new TemplateModel();
        templateModel.setName(user.getName());
        templateModel.setBasicInfo(basicInfoMap);

        templateModel.setBioCh(user.getBioChn().replaceAll("(\n)", "<br />"));
        templateModel.setBioEn(user.getBioEng().replaceAll("(\n)", "<br />"));

        templateModel.setFeature(user.getFeature());

        templateModel.setIntro(user.getIntroduction());

        templateModel.setLinks(linkMapper.findAll(userId));

        templateModel.setEducations(resumeRelationMapper.getEducationIdByResumeId(resumeId).stream()
                .map(educationService::getEducation).collect(Collectors.toList()));
        templateModel.setExperiences(resumeRelationMapper.getExperienceIdByResumeId(resumeId).stream()
                .map(experienceService::getExperience).collect(Collectors.toList()));
        templateModel.setLanguages(resumeRelationMapper.getLanguageIdByResumeId(resumeId).stream()
                .map(languageService::getLanguage).collect(Collectors.toList()));
        templateModel.setLicenses(resumeRelationMapper.getLicenseIdByResumeId(resumeId).stream()
                .map(licenseService::getLicense).collect(Collectors.toList()));
        templateModel.setSkills(resumeRelationMapper.getSkillIdByResumeId(resumeId).stream().map(skillService::getSkill)
                .collect(Collectors.toList()));

        if (StringUtils.isNotBlank(user.getImgPath())) {
            InputStream inputStream = awsUtils.downloadFileFromS3(user.getImgPath());
            byte[] bytes = IOUtils.toByteArray(inputStream);
            String base64 = Base64.getEncoder().encodeToString(bytes);
            templateModel.setImage("data:image/jpeg;base64, " + base64);
        }

        File outFile = null;
        File chFile = null;
        File enFile = null;
        File resultFile = null;

        List<String> bio = new ArrayList<>();
        String filePath = "user" + userId + "/";
        try {
            outFile = new File("src/main/resources/temp/resume" + resumeId + ".pdf");
            chFile = new File("src/main/resources/temp/ch" + resumeId + ".pdf");
            enFile = new File("src/main/resources/temp/en" + resumeId + ".pdf");
            resultFile = new File("src/main/resources/temp/result" + resumeId + ".pdf");

            makePdfAndSave(processTemplate(template.getName(), templateModel, "index"), outFile);

            if (resume.isBioCh()) {
                bio.add("ch");
                makePdfAndSave(processTemplate(template.getName(), templateModel, "ch"), chFile);
            }

            if (resume.isBioEn()) {
                bio.add("en");
                makePdfAndSave(processTemplate(template.getName(), templateModel, "en"), enFile);
            }

            if (bio.size() > 0) {
                PdfDocument pdf = new PdfDocument(new PdfWriter(resultFile));
                PdfMerger merger = new PdfMerger(pdf);

                PdfDocument firstSourcePdf = new PdfDocument(new PdfReader(outFile));
                merger.merge(firstSourcePdf, 1, firstSourcePdf.getNumberOfPages());

                for (int i = 0; i < bio.size(); i++) {
                    PdfDocument secondSourcePdf;
                    if (bio.get(i).equals("ch")) {
                        secondSourcePdf = new PdfDocument(new PdfReader(chFile));
                    } else {
                        secondSourcePdf = new PdfDocument(new PdfReader(enFile));
                    }
                    merger.merge(secondSourcePdf, 1, secondSourcePdf.getNumberOfPages());
                    secondSourcePdf.close();
                }
                firstSourcePdf.close();
                pdf.close();
                awsUtils.uploadFileToS3(resultFile, filePath);
                resumeMapper.updateFilePath(filePath + "result" + resumeId + ".pdf", resumeId, userId);
            } else {
                awsUtils.uploadFileToS3(outFile, filePath);
                resumeMapper.updateFilePath(filePath + "resume" + resumeId + ".pdf", resumeId, userId);
            }
        } finally {
            if (outFile != null && outFile.exists()) {
                FileUtils.forceDelete(outFile);
            }
            if (chFile != null && chFile.exists()) {
                FileUtils.forceDelete(chFile);
            }
            if (enFile != null && enFile.exists()) {
                FileUtils.forceDelete(enFile);
            }
            if (resultFile != null && resultFile.exists()) {
                FileUtils.forceDelete(resultFile);
            }
        }
        return true;
    }

    @SneakyThrows
    private void makePdfAndSave(String html, File outFile) {

        try {
            if (!outFile.exists()) {
                FileUtils.touch(outFile);
            }

            PdfWriter writer = new PdfWriter(outFile);
            PdfDocument pdf = new PdfDocument(writer);
            PageSize a4 = PageSize.A4;
            a4.applyMargins(0, 0, 0, 0, false);
            pdf.setDefaultPageSize(a4);
            ConverterProperties prop = new ConverterProperties();

            FontProvider fontProvider = new DefaultFontProvider();

            String uri = ResumeService.class.getResource("/templates/").toString();
            log.debug("base uri:{}", uri);
            String font = uri + "font/TaipeiSansTCBeta-Regular.ttf";
            log.debug("font:{}", font);

            FontProgram fontProgram = FontProgramFactory.createFont(font);
            fontProvider.addFont(fontProgram);
            prop.setFontProvider(fontProvider);
            prop.setBaseUri(uri);

            HtmlConverter.convertToPdf(html, pdf, prop);

        } catch (Exception e) {
            log.error("產生PDF失敗", e);
            throw e;
        }

    }

    private String processTemplate(String template, TemplateModel templateModel, String html) throws Exception {

        StringWriter sw = new StringWriter();

        // freemarker.getTemplate("template_A4.html").process(user, sw);
        freemarker.getTemplate("html/" + template + "/" + html + ".html").process(templateModel, sw);
        log.info("Process template result: {}", sw.toString());

        // return new ByteArrayInputStream(sw.toString().getBytes(StandardCharsets.UTF_8));
        return sw.toString();

    }

    public ResumeRelation getResumeRelation(int id) {

        return ResumeRelation.builder().education(resumeRelationMapper.getEducationIdByResumeId(id))
                .experience(resumeRelationMapper.getExperienceIdByResumeId(id))
                .skill(resumeRelationMapper.getSkillIdByResumeId(id))
                .language(resumeRelationMapper.getLanguageIdByResumeId(id))
                .license(resumeRelationMapper.getLicenseIdByResumeId(id)).build();

    }
}
