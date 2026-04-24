package com.pknu26.studygroup.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long userId;
    private String loginId;
    private String password;
    private String name;
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}