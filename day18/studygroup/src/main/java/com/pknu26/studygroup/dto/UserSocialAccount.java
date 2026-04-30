package com.pknu26.studygroup.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserSocialAccount {

    private Long socialId;
    private Long userId; // FK

    private String provider;
    private String providerUserId;
    private String email;
    private String name;
    private String profileImage;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
