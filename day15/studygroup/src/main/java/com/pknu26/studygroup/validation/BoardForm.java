package com.pknu26.studygroup.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BoardForm {

    private Long boardId;  // 수정시 값 확인용

    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 200, message = "제목은 200자 이하로 입력하세요")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    private String content;

    // @NotBlank(message = "작성자는 필수입니다")
    // @Size(max = 20, message = "작성자는 20자 이하로 입력하세요")
    private String writer;

    private String writerId;  // 작성자 아이디(계정 아이디: pknu\...)
}
