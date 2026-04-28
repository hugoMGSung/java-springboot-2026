package com.pknu26.studygroup.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyApplication {
    private Long applicationId;
    private Long postId;
    private Long userId;
    private String message;
    private String status;  // PENDING(신청최초), REJECTED(신청취소), APPROVED(신청승인)
    private LocalDateTime createdAt;

    // 조인용
    private String applicantName;
    private String postTitle;
}
