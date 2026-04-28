package com.pknu26.studygroup.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pknu26.studygroup.config.JwtTokenProvider;
import com.pknu26.studygroup.dto.api.ApiLoginRequest;
import com.pknu26.studygroup.dto.api.ApiLoginResponse;
import com.pknu26.studygroup.security.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// http://localhost:8080/api/auth/login
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApiAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인 요청처리 Post
     * @param request 로그인아이디, 패스워드 dto
     * @return 인증성공 후 토큰정보 포함 dto
     */
    @PostMapping("/login")  
    public ResponseEntity<ApiLoginResponse> login(@Valid @RequestBody ApiLoginRequest request) {
        // AuthenticationManager로 인증시도, ID/Password로 인증토큰 생성
        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword())  
        );

        // 사용자 정보(Principal)
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // JWT 액세스토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(userDetails);

        // ApiLoginResponse dto 생성
        ApiLoginResponse response = new ApiLoginResponse(
            "Bearer",
            accessToken,
            userDetails.getUserId(),
            userDetails.getLoginId(), 
            userDetails.getName(), 
            userDetails.getRole()
        );

        // 상태코드 200(OK) 포함, 응답객체 리턴
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
