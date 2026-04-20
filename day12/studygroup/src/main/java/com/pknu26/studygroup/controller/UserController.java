package com.pknu26.studygroup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.studygroup.dto.LoginUser;
import com.pknu26.studygroup.dto.User;
import com.pknu26.studygroup.service.UserService;
import com.pknu26.studygroup.validation.UserJoinForm;
import com.pknu26.studygroup.validation.UserLoginForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입 화면 진입
    @GetMapping("/join")
    public String showJoinForm(Model model) {
        model.addAttribute("userJoinForm", new UserJoinForm());

        return "/user/join"; // /user/join.html
    }

    // 회원가입 DB처리
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("userJoinForm") UserJoinForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/join";
        }

        try {
            this.userService.join(form);
        } catch (IllegalArgumentException e) {
            bindingResult.reject("error", e.getMessage());
            return "/user/join"; // 에러메시지가 html에 출려됨
        }

        return "redirect:/user/login";  // 회원가입이 끝나면 로그인화면으로 전환
    }

    // 로그인 화면
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginForm", new UserLoginForm());
        return "/user/login";
    }

    // 로그인처리
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("userLoginForm") UserLoginForm form, 
                        BindingResult bindingResult, 
                        HttpSession session) {
        // BindingResult -> 입력 에러 체크
        if (bindingResult.hasErrors()) {
            return "/user/login";  // 로그인화면으로 돌아가면서 에러메시지 출력
        }

        User user = this.userService.login(form);

        if (user == null) {
            bindingResult.reject("error", "아이디 또는 패스워드가 올바르지 않습니다");
            return "/user/login";
        }

        LoginUser loginUser = new LoginUser(
                                    user.getUserId(), 
                                    user.getLoginId(), 
                                    user.getName(), 
                                    user.getRole()
                                );
        
        // HttpSession에 로그인 사용자 저장
        session.setAttribute("loginUser", loginUser);  // HTML 어디서나 사용할 수 있음

        return "redirect:/board/list";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 세션정보 날리기
        return "redirect:/board/list";
    }
}