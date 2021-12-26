package com.sedia.resume.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsUtils {

    @Value("${cloud.aws.s3.bucket-name}")
    String bucketName;

    final AmazonS3 s3Client;

    public boolean uploadFileToS3(File file, String path) {
        // String path = "test/";
        String fileName = file.getName();
        if (StringUtils.isNotEmpty(path)) {
            fileName = path + fileName;
        }

        log.info("Uploading file: [{}] to aws s3.", fileName);
        try {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
            log.info("Upload to aws s3 successful.");
        } catch (Exception e) {
            log.error("Upload to aws s3 failed.", e);
            return false;
        }
        return true;
    }

    public boolean uploadFileToS3(String fileName, InputStream input) {
        // String path = "test/";
        // log.info("Uploading file: [{}] to aws s3.", fileName);
        try {
            // s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
            s3Client.putObject(bucketName, fileName, input, null);
            log.info("Upload to aws s3 successful.");
        } catch (Exception e) {
            log.error("Upload to aws s3 failed.", e);
            return false;
        }
        return true;
    }

    public InputStream downloadFileFromS3(String fileName) {
        log.info("Downloading file: [{}] from aws s3.", fileName);
        try {
            return s3Client.getObject(bucketName, fileName).getObjectContent();
        } catch (Exception e) {
            log.error("Download file: [{}] from aws s3 failed.", fileName, e);
            throw e;
        }
    }

}
