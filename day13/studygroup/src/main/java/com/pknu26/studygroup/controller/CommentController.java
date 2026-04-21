package com.pknu26.studygroup.controller;

import com.pknu26.studygroup.service.StudyApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.studygroup.dto.Comment;
import com.pknu26.studygroup.dto.LoginUser;
import com.pknu26.studygroup.service.CommentService;
import com.pknu26.studygroup.service.StudyPostService;
import com.pknu26.studygroup.validation.CommentForm;
import com.pknu26.studygroup.validation.StudyApplicationForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final StudyApplicationService studyApplicationService;
    private final CommentService commentService;
    private final StudyPostService studyPostService;

    @PostMapping("/create")    
    public String create(@Valid CommentForm commentForm,
                         BindingResult bindingResult,
                         Model model,
                         HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }  // 로그인을 안했으면 로그인으로 이동

        // 폼에서 댓글 내용을 입력하지 않고 댓글저장 버튼누르면 오류표시
        if (bindingResult.hasErrors()) {
            // post/detail.html 을 전부 초기화. 모델값 받아서 전달.
            model.addAttribute("post", this.studyPostService.getPostDetail(commentForm.getPostId()));
            model.addAttribute("commentList", this.commentService.getCommentList(commentForm.getPostId()));

            model.addAttribute("commentForm", commentForm);
            model.addAttribute("applicationList", this.studyApplicationService.getApplicationListByPostId(commentForm.getPostId()));

            StudyApplicationForm studyApplicationForm = new StudyApplicationForm();
            studyApplicationForm.setPostId(commentForm.getPostId());
            studyApplicationForm.setUserId(loginUser.getUserId());
            model.addAttribute("studyApplicationForm", studyApplicationForm);

            return "/post/detail";
        }

        // 로그인 사용자를 추가
        commentForm.setUserId(loginUser.getUserId());
        this.commentService.createComment(commentForm); // DB 저장 처리시작

        // 스터디포스트 상세화면으로 이동
        return "redirect:/studypost/detail/" + commentForm.getPostId();
    }

    @PostMapping("/delete/{commentId}")
    public String delete(@PathVariable Long commentId,
                         HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }  // 로그인한 상태강 아니면 사용불가

        Comment comment = this.commentService.getComment(commentId);
        if (comment == null) {
            return "redirect:/studypost/list";
        } // 해당 댓글 ID 글이 없으면 목록으로

        boolean isWriter = comment.getUserId().equals(loginUser.getUserId()); // 댓글 작성자와 동일인확인
        boolean isAdmin = "ROLE_ADMIN".equals(loginUser.getRole()); // 관리자인지 확인

        if (!isWriter && !isAdmin) { // 댓글 삭제불가
            return "redirect:/studypost/detail/" + comment.getPostId();
        }

        this.commentService.deleteComment(commentId);
        return "redirect:/studypost/detail/" + comment.getPostId();
    }
}
