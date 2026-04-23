package com.pknu26.studygroup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class FileProperties {

    @Value("${file.upload-dir}")
    private String uploadDir;  // D:/upload/studygroup 할당

    @Value("${file.access-url}")
    private String accessUrl; // /upload/site
}
