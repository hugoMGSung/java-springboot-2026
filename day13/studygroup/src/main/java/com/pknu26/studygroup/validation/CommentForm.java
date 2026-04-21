package com.pknu26.studygroup.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {

    private Long userId;  // 사용자 ID

    @NotNull(message = "게시글 정보가 없습니다")
    private Long postId;

    @NotBlank(message = "댓글 내용을 입력하세요")
    private String content;
}
