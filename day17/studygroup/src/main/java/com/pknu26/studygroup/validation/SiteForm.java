package com.pknu26.studygroup.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteForm {
    private Long id;

    @NotBlank(message = "콘텐츠 키를 입력하세요.")
    @Size(max = 100, message = "콘텐츠 키는 100자 이하로 입력하세요.")
    private String contentKey;

    @NotBlank(message = "콘텐츠 내용을 입력하세요.")
    @Size(max = 4000, message = "콘텐츠 내용은 4000자 이하로 입력하세요.")
    private String contentBody;

    private String useYn;
}
