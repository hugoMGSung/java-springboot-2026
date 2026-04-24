package com.pknu26.studygroup.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteImage {

    private Long id;
    private String imageKey;
    private String imagePath;  // 이미지 경로
    private String useYn;
    private LocalDateTime createdAt;
}
