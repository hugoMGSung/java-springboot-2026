package com.pknu26.studygroup.controller;

import com.pknu26.studygroup.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.studygroup.dto.LoginUser;
import com.pknu26.studygroup.dto.StudyApplication;
import com.pknu26.studygroup.dto.StudyPost;
import com.pknu26.studygroup.service.StudyApplicationService;
import com.pknu26.studygroup.service.StudyPostService;
import com.pknu26.studygroup.validation.CommentForm;
import com.pknu26.studygroup.validation.StudyApplicationForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class StudyApplicationController {

    private final CommentService commentService;
    private final StudyApplicationService studyApplicationService;
    private final StudyPostService studyPostService;

    // 스터디 신청
    @PostMapping("/create")
    public String create(@Valid StudyApplicationForm form,
                         BindingResult bindingResult,
                         Model model,
                         HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        Long postId = form.getPostId();  // 현재 스터디포스트 아이디

        if (bindingResult.hasErrors()) { // 입력에러가 있으면
            bindingResult.reject("error", "메시지를 입력하세요.");
            // post/detail.html에 다시 전달할 모델값 전부 초기화!!
            model.addAttribute("post", this.studyPostService.getPostDetail(postId));
            model.addAttribute("commentList", this.commentService.getCommentList(postId));

            CommentForm commentForm = new CommentForm();
            commentForm.setPostId(postId);

            model.addAttribute("commentForm", commentForm);
            model.addAttribute("applicationList", this.studyApplicationService.getApplicationListByPostId(postId));

            StudyApplicationForm studyApplicationForm = new StudyApplicationForm();
            studyApplicationForm.setPostId(postId);
            studyApplicationForm.setUserId(loginUser.getUserId());
            model.addAttribute("studyApplicationForm", studyApplicationForm);

            return "/post/detail";
        }

        try {
            form.setUserId(loginUser.getUserId());
            this.studyApplicationService.apply(form);
            
        } catch (IllegalArgumentException e) {
            bindingResult.reject("error", "메시지를 입력하세요.");
            // 오류가 났기때문에 model값들을 /post/detail.html에 초기화, 전달해줘야 함!!!
            model.addAttribute("post", this.studyPostService.getPostDetail(postId));
            model.addAttribute("commentList", this.commentService.getCommentList(postId));

            CommentForm commentForm = new CommentForm();
            commentForm.setPostId(postId);

            model.addAttribute("commentForm", commentForm);
            model.addAttribute("applicationList", this.studyApplicationService.getApplicationListByPostId(postId));

            StudyApplicationForm studyApplicationForm = new StudyApplicationForm();
            studyApplicationForm.setPostId(postId);
            studyApplicationForm.setUserId(loginUser.getUserId());
            model.addAttribute("studyApplicationForm", studyApplicationForm);

            return "/post/detail";
        }

        return "redirect:/studypost/detail/" + form.getPostId();
    }

    // 스터디 신청 승인
    @PostMapping("/approve/{applicationId}")
    public String approve(@PathVariable Long applicationId,
                          HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        StudyApplication application = this.studyApplicationService.getApplication(applicationId);
        if (application == null) {
            return "redirect:/studypost/list";
        }

        StudyPost post = studyPostService.getPostDetail(application.getPostId());

        boolean isWriter = post.getUserId().equals(loginUser.getUserId());
        boolean isAdmin = "ROLE_ADMIN".equals(loginUser.getRole());

        if (!isWriter && !isAdmin) {
            return "redirect:/studypost/detail/" + application.getPostId();
        }

        this.studyApplicationService.approve(applicationId);
        return "redirect:/studypost/detail/" + application.getPostId();
    }
    
    // 스터디 신청거절
    @PostMapping("/reject/{applicationId}")
    public String reject(@PathVariable Long applicationId,
                         HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        StudyApplication application = this.studyApplicationService.getApplication(applicationId);
        if (application == null) {
            return "redirect:/studypost/list";
        }

        StudyPost post = studyPostService.getPostDetail(application.getPostId());

        boolean isWriter = post.getUserId().equals(loginUser.getUserId());
        boolean isAdmin = "ROLE_ADMIN".equals(loginUser.getRole());

        if (!isWriter && !isAdmin) {
            return "redirect:/studypost/detail/" + application.getPostId();
        }

        this.studyApplicationService.reject(applicationId);
        return "redirect:/studypost/detail/" + application.getPostId();
    }
}
