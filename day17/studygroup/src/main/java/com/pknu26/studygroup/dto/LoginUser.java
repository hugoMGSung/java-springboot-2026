package com.pknu26.studygroup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUser {

    private Long userId;
    private String loginId;
    private String name;
    private String role;
}