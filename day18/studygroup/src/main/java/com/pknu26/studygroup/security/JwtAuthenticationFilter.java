package com.pknu26.studygroup.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pknu26.studygroup.config.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JWT 인증 필터
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * JWT 토큰을 내부에서 확인 메서드
     * @Param request  - 요청정보
     * @Param response - 응답정보
     * @Param filterChain - 필터체인
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);  // 요청헤더값에서 JWT토큰만 추출

        // 토큰이 유효하면
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String loginId = jwtTokenProvider.getLoginId(token); 

            // loginId로 DB에서 사용자 상세정보 로드
            CustomUserDetails userDetails = (CustomUserDetails)
                        customUserDetailsService.loadUserByUsername(loginId);

            // SpringSecurity에서 사용할 인증객체 생성
            // (사용자정보객체, 비밀번호 null, 권한)
            UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userDetails, 
                                                            null,
                                                            userDetails.getAuthorities());
            
            // 인증객체에 요청상세 각종정보 설정
            authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
            );
            // SecutiryContext에 인증정보 저장
            // 여기서부터 인증된 사용자로 처리시작
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 필터체인 진행
        filterChain.doFilter(request, response);
    }

    /**
     * HTTP 요청헤더에서 Authorization 값 찾아, 토큰문자열만 추출
     * @param request
     * @return
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 뒤의 문자열만 토큰값으로 반환
        } else {
            return null;
        }
    }
}
