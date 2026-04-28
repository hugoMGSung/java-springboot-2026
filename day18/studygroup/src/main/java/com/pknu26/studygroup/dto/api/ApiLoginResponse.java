package com.pknu26.studygroup.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiLoginResponse {

    private String tokenType;  // 타입종류 Bearer, Basic, Digest
    private String accessToken;  // JWT에서 발급한 토큰

    private Long userId;  // PK
    private String loginId;  // 실제 아이디
    private String name;   // 이름
    private String role;    // 권한
}
