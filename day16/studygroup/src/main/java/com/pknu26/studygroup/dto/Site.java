package com.pknu26.studygroup.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Site {
    private Long id;
    private String contentKey;
    private String contentBody;
    private LocalDateTime createdAt;

    private String useYn; // 사용여부
}
