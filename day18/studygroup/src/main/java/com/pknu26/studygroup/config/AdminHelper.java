package com.pknu26.studygroup.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// import com.pknu26.studygroup.dto.LoginUser;

import jakarta.servlet.http.HttpSession;

// 사용자 정의 클래스
public class AdminHelper {

    // 관리자 세션 체크
    public static void checkAdmin(HttpSession session) {
        // LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        // if (loginUser == null || !"ROLE_ADMIN".equals(loginUser.getRole())) {            
        //     throw new RuntimeException("관리자만 접근할 수 있습니다.");
        // }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("관리자만 접근할 수 있습니다.");
        }

        boolean isAdmin = auth.getAuthorities().stream()
                            .anyMatch(au -> au.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("관리자만 접근할 수 있습니다.");
        }
    }
}
