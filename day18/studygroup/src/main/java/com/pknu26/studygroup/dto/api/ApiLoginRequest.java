package com.pknu26.studygroup.dto.api;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiLoginRequest {

    @NotBlank(message = "아이디를 입력하세요")
    private String loginId;

    @NotBlank(message = "패스워드를 입력하세요")
    private String password;
}
