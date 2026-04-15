package com.pknu26.studygroup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.studygroup.service.UserService;
import com.pknu26.studygroup.validation.UserJoinForm;

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
            bindingResult.reject("joinError", e.getMessage());
            return "/user/join"; // 에러메시지가 html에 출려됨
        }

        return "redirect:/user/login";  // 회원가입이 끝나면 로그인화면으로 전환
    }
}