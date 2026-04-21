package com.pknu26.studygroup.validation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudyPostForm {

    private Long postId;    
    private Long userId;

    // NotBlank : 문자열 종류 입력검증
    // NotNull : 숫자타입 입력검증
    @NotNull(message = "카테고리를 선택하세요.")
    @Min(1)
    private Long categoryId;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotNull(message = "최대인원은 필수입니다.")
    @Min(1)
    private Integer maxMembers;
    
    // 조인 조회용
    private String writerName;
    private String categoryName;
}
