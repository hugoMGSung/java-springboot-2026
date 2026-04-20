package com.pknu26.studygroup.validation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginForm {

     @NotBlank(message = "아이디를 입력하세요")
    private String loginId;

    @NotBlank(message = "패스워드를 입력하세요")
    private String password;

}
