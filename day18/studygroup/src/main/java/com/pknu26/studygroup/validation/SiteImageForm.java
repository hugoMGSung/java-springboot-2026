package com.pknu26.studygroup.validation;

import org.springframework.web.multipart.MultipartFile; // 파일첨부시 사용클래스

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteImageForm {

    private Long id;

    @NotBlank(message = "이미지 키를 입력하세요.")
    private String imageKey;

    @NotBlank(message = "사용유무를 선택하세요")
    private String useYn;

    private MultipartFile imageFile; // 이미지 파일 자체
}
