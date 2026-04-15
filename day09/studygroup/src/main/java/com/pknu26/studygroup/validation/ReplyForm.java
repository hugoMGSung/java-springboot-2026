package com.pknu26.studygroup.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReplyForm {

    private Long replyId;
    private Long boardId;

    @NotBlank(message = "댓글 내용은 필수입니다")
    @Size(max = 1000, message = "댓글은 1000자 이하로 입력하세요")
    private String replyContent;

    @NotBlank(message = "작성자는 필수입니다")
    @Size(max = 20, message = "작성자는 20자 이하로 입력하세요")
    private String replyWriter;
}
