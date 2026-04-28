package com.pknu26.studygroup.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.User;
import com.pknu26.studygroup.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

// SpringSecurity로 기존 로그인로직 대체
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        
        // 사용자가 있는지 조회
        User user = userMapper.findByLoginId(loginId);  // loginId

        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }

        // 사용자 객체, 권한 리턴
        return new CustomUserDetails(user,
            List.of(new SimpleGrantedAuthority(user.getRole()))  // ROLE_USER, ROLE_ADMIN
        );
    }
}
