package com.sedia.resume.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import com.sedia.resume.security.JwtUtil;
import com.sedia.resume.service.ResumeService;
import com.sedia.resume.service.UserService;
import com.sedia.resume.utils.AwsUtils;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 這是方便測試使用的 controller，這裡的方法不需要登入使用者也可以操作
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Slf4j
public class TestController {

    final AwsUtils awsUtils;

    @Value("${sendgrid.api-key}")
    private String sendGridKey;

    final UserService userService;

    final Configuration freemarker;

    final ResumeService resumeService;

    @GetMapping("/upload")
    public String testUpload() throws IOException {

        Resource resource = new ClassPathResource("temp/test.txt");

        InputStream inputStream = resource.getInputStream();
        // File file = new File("test.txt");
        // // append = false
        // try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
        // int read;
        // byte[] bytes = new byte[8192];
        // while ((read = inputStream.read(bytes)) != -1) {
        // outputStream.write(bytes, 0, read);
        // }
        // }

        // File file = resource.getFile();
        // boolean result = awsUtils.uploadFileToS3(file);
        boolean result = awsUtils.uploadFileToS3("test1.txt", inputStream);
        if (result) {
            return "上傳成功";
        }
        return "上傳失敗";
    }

    @GetMapping("/download-s3")
    public ResponseEntity<InputStreamResource> testDownload(String fileName) {

        // String fileName = "test1.txt";

        InputStream source = awsUtils.downloadFileFromS3(fileName);
        InputStreamResource resource = new InputStreamResource(source);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/mail")
    public String testSendMail() throws IOException {

        Email from = new Email("wade5141000@outlook.com");
        Email to = new Email("wade5141000@gmail.com");
        from.setName("resume-service");
        String subject = "Sending with Twilio SendGrid is Fun";
        Content content = new Content("text/html", "and <em>easy</em> to do anywhere with <strong>Java</strong>");

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        System.out.printf("response code: %d", response.getStatusCode());
        // System.out.println(response.getHeaders());
        // System.out.println(response.getBody());
        return "成功發信";
    }

    @GetMapping("/mock-login")
    @ResponseBody
    public String mockLogin(String username) {
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + JwtUtil.EXPIRATION_TIME)).signWith(JwtUtil.key)
                .compact();
    }

    @GetMapping("/cv")
    @ResponseBody
    public void htmlToPdf(String html) {
        try {
            // File template = new ClassPathResource("templates/" + html + ".html").getFile();

            // String htmlString = IOUtils.toString(new FileInputStream(template), UTF_8);
            // System.out.println(htmlString);

            File outFile = new File("src/main/resources/temp/test.pdf");
            if (!outFile.exists()) {
                FileUtils.touch(outFile);
            }

            // File outFile = new ClassPathResource("temp/test.pdf").getFile();

            PdfWriter writer = new PdfWriter(outFile);
            // PdfWriter writer = new PdfWriter("test.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            PageSize a4 = PageSize.A4;
            a4.applyMargins(0, 0, 0, 0, false);
            pdf.setDefaultPageSize(a4);
            ConverterProperties prop = new ConverterProperties();

            FontProvider fontProvider = new DefaultFontProvider();
            String font = "src/main/resources/templates/font/TaipeiSansTCBeta-Regular.ttf";
            FontProgram fontProgram = FontProgramFactory.createFont(font);
            fontProvider.addFont(fontProgram);
            prop.setFontProvider(fontProvider);
            prop.setBaseUri("src/main/resources/templates/");
            // HtmlConverter.convertToPdf(new FileInputStream(template), pdf, prop);
            HtmlConverter.convertToPdf(processTemplate(), pdf, prop);

        } catch (Exception e) {
            log.error("產生PDF失敗", e);
        }
    }

    @GetMapping("/download-cv")
    @ResponseBody
    public ResponseEntity<Resource> downloadPdf() throws IOException {
        File file = new ClassPathResource("temp/test.pdf").getFile();
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "test.pdf");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/image")
    public void getImage(HttpServletResponse response) throws IOException {
        File file = new ClassPathResource("templates/images/pic.jpg").getFile();
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(new FileInputStream(file), response.getOutputStream());
    }

    @PostMapping(value = "/image/upload", consumes = "multipart/form-data")
    public boolean uploadImage(@RequestParam("image") MultipartFile image) {
        return false;
    }

    @GetMapping("/hello")
    public String hello() {
        log.info("Hello from test.");

        // test
        return "hello";
    }

    @GetMapping("/freemarker")
    public void freemarker() throws IOException, TemplateException {

        processTemplate();

    }

    @GetMapping("/freemarker2")
    public void freemarker2(int resumeId) throws Exception {

        resumeService.applyResume(resumeId);

    }

    String processTemplate() throws IOException, TemplateException {
        Map<String, String> user = new HashMap<>();
        user.put("name", "吳韋德");
        user.put("age", "30");

        StringWriter sw = new StringWriter();

        freemarker.getTemplate("template_A4.html").process(user, sw);
        System.out.println(sw);

        // return new ByteArrayInputStream(sw.toString().getBytes(StandardCharsets.UTF_8));
        return sw.toString();

    }

}
