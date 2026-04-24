package com.pknu26.studygroup.config;

import com.pknu26.studygroup.dto.LoginUser;

import jakarta.servlet.http.HttpSession;

// 사용자 정의 클래스
public class AdminHelper {

    // 관리자 세션 체크
    public static void checkAdmin(HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null || !"ROLE_ADMIN".equals(loginUser.getRole())) {            
            throw new RuntimeException("관리자만 접근할 수 있습니다.");
        }
    }
}
