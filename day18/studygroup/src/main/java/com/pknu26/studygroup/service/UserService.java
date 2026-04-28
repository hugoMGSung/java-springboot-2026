package com.pknu26.studygroup.service;

import com.pknu26.studygroup.dto.User;
import com.pknu26.studygroup.validation.UserJoinForm;
import com.pknu26.studygroup.validation.UserLoginForm;

public interface UserService {

    void join(UserJoinForm form);

    User login(UserLoginForm form);

    boolean isLoginIdDuplicated(String loginId);
}
