package com.pknu26.webboard.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyForm {

    @Size(max = 1000)
    @NotBlank(message = "댓글내용은 필수입니다")
    private String content;
}
