package com.pknu26.studygroup.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyApplicationForm {

    @NotNull(message = "게시글 정보가 없습니다")
    private Long postId;

    private Long userId;

    @NotBlank(message = "신청메시지를 입력하세요")
    private String message;
}
