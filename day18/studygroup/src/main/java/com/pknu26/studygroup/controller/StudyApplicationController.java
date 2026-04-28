package com.pknu26.studygroup.controller;

import com.pknu26.studygroup.service.CommentService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.pknu26.studygroup.dto.LoginUser;
import com.pknu26.studygroup.dto.StudyApplication;
import com.pknu26.studygroup.dto.StudyPost;
import com.pknu26.studygroup.security.CustomUserDetails;
import com.pknu26.studygroup.service.StudyApplicationService;
import com.pknu26.studygroup.service.StudyPostService;
import com.pknu26.studygroup.validation.CommentForm;
import com.pknu26.studygroup.validation.StudyApplicationForm;

// import jakarta.servlet.http.HttpSession;
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
                         @AuthenticationPrincipal CustomUserDetails loginUser) {
        // LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        Long postId = form.getPostId();  // 현재 스터디포스트 아이디

        if (bindingResult.hasErrors()) { // 입력에러가 있으면
            // post/detail.html에 다시 전달할 모델값 전부 초기화!!
            model.addAttribute("post", this.studyPostService.getPostDetail(postId));
            model.addAttribute("commentList", this.commentService.getCommentList(postId));

            CommentForm commentForm = new CommentForm();
            commentForm.setPostId(postId);

            model.addAttribute("commentForm", commentForm);
            model.addAttribute("applicationList", this.studyApplicationService.getApplicationListByPostId(postId));
            // 화면에서 넘어온 form 그래도 전달.
            model.addAttribute("studyApplicationForm", form);

            return "/post/detail";
        }

        try {
            form.setUserId(loginUser.getUserId());
            this.studyApplicationService.apply(form);
            
        } catch (IllegalArgumentException e) {
            bindingResult.reject("error", "이미 신청되었습니다.");
            // 오류가 났기때문에 model값들을 /post/detail.html에 초기화, 전달해줘야 함!!!
            model.addAttribute("post", this.studyPostService.getPostDetail(postId));
            model.addAttribute("commentList", this.commentService.getCommentList(postId));

            CommentForm commentForm = new CommentForm();
            commentForm.setPostId(postId);

            model.addAttribute("commentForm", commentForm);
            model.addAttribute("applicationList", this.studyApplicationService.getApplicationListByPostId(postId));
            model.addAttribute("studyApplicationForm", form);

            return "/post/detail";
        }

        return "redirect:/studypost/detail/" + form.getPostId();
    }

    // 스터디 신청 승인
    // 260422. 신청예외발생시 오류메시지 전달용 RedirectAttributes 파라미터 추가
    @PostMapping("/approve/{applicationId}")
    public String approve(@PathVariable Long applicationId,
                          @AuthenticationPrincipal CustomUserDetails loginUser,
                          RedirectAttributes redirectAttributes) {
        // LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
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

        try {
            // postId도 같이 넘겨서 모집인원이 넘어가면 더이상 승인이 안되도록 할 것        
            this.studyApplicationService.approve(applicationId, post);            
        } catch (IllegalArgumentException e) {
            // Model로 html에 전달하는 것과 동일
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        
        return "redirect:/studypost/detail/" + application.getPostId();
    }
    
    // 스터디 신청거절
    @PostMapping("/reject/{applicationId}")
    public String reject(@PathVariable Long applicationId,
                         @AuthenticationPrincipal CustomUserDetails loginUser) {
        // LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
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

        // postId 전달해서 다시 거절해서 모집인원보다 인원수가 줄어들면 다시 CLOSED -> OPEN으로 변경
        this.studyApplicationService.reject(applicationId, post);
        return "redirect:/studypost/detail/" + application.getPostId();
    }
}
