package com.pknu26.studygroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter  // 멤버변수 getter 메서드 자동생성
@Setter  // 멤버변수 setter 메서드 자동생성
@NoArgsConstructor  // 기본생성자 자동생성
@AllArgsConstructor // 파라미터생성자 자동생성
public class Student {

    private Long id;
    private String name;
    private Integer age;
    private String major;
}
