package com.pknu26.studygroup.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.User;
import com.pknu26.studygroup.mapper.UserMapper;
import com.pknu26.studygroup.validation.UserJoinForm;
import com.pknu26.studygroup.validation.UserLoginForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;  

    // 회원가입 메서드
    @Override
    public void join(UserJoinForm form) {
        if (isLoginIdDuplicated(form.getLoginId())) { // 동일아이디 회원가입 시도
            throw new IllegalArgumentException("이미 사용 중인 아이디이입니다.");
        }

        if (!form.getPassword().equals(form.getPasswordConfirm())) {  // 패스워드, 패스워드 확인 불일치
            throw new IllegalArgumentException("패스워드와 패스워드 확인이 일치하지 않습니다.");
        }

        User user = new User();
        user.setLoginId(form.getLoginId());
        // 패스워드를 그대로 저장하지 않고 BCrypt 암호화
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setName(form.getName());
        user.setRole("ROLE_USER");

        this.userMapper.insertUser(user);
    }

    @Override
    public User login(UserLoginForm form) {
        User user = this.userMapper.findByLoginId(form.getLoginId());

        if (user == null) return null;

        // 입력한 패스워드와 저장된 패스워의 일치여부 비교
        boolean matches = passwordEncoder.matches(form.getPassword(), user.getPassword());

        if (!matches) return null;

        return user;
    }

    // 중복회원 조회
    @Override
    public boolean isLoginIdDuplicated(String loginId) {
        return this.userMapper.findByLoginId(loginId) != null;  // 같은 아이디가 있으면 true/ 없으면 false
    }
}
