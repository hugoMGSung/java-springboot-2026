package com.pknu26.studygroup.controller;

import com.pknu26.studygroup.service.StudyApplicationService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

// import com.pknu26.studygroup.dto.LoginUser;
import com.pknu26.studygroup.dto.PageRequest;
import com.pknu26.studygroup.dto.StudyPost;
import com.pknu26.studygroup.security.CustomUserDetails;
import com.pknu26.studygroup.service.CategoryService;
import com.pknu26.studygroup.service.CommentService;
import com.pknu26.studygroup.service.StudyPostService;
import com.pknu26.studygroup.validation.CommentForm;
import com.pknu26.studygroup.validation.StudyApplicationForm;
import com.pknu26.studygroup.validation.StudyPostForm;

// import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 260420. 첫 작성
 */
@Controller
@RequestMapping("/studypost")
@RequiredArgsConstructor
public class StudyPostController {

    private final StudyApplicationService studyApplicationService;

    private final StudyPostService studyPostService;

    // 스터디포스트 입력시 카테고리를 선택할 콤보박스 바인딩용
    private final CategoryService categoryService;

    private final CommentService commentService; // 260421. 댓글서비스 추가

    // 260422 페이징 추가
    @GetMapping("/list")  // http://localhost:8080/studypost/list
    public String list(@ModelAttribute PageRequest pageRequest, Model model, @AuthenticationPrincipal CustomUserDetails loginUser) {
        // HTML 상에서 #authentication.getPrincipal().getUsername() 사용가능
        // LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login"; // 로그인안한 사람은 게시판 글 못씀
        }

        // 단순 리스트조회 시 사용한 방법
        // List<StudyPost> postList = this.studyPostService.getPostList();
        // model.addAttribute("postList", postList);
        // 페이징으로 변경 후 
        model.addAttribute("response", this.studyPostService.getPostList(pageRequest));
        return "/post/list";  // templates/post/list.html 
    }
    
    @GetMapping("/create")
    public String createForm(Model model, @AuthenticationPrincipal CustomUserDetails loginUser) {
        // LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login"; // 로그인안한 사람은 게시판 글 못씀
        }

        StudyPostForm studyPostForm = new StudyPostForm();
        
        model.addAttribute("studyPostForm", studyPostForm);
        // 콤보박스용 데이터 모델
        model.addAttribute("categoryList", this.categoryService.getCategoryList());

        return "/post/form"; // templates/post/form.html
    }

    @PostMapping("/create")
    public String create(@Valid StudyPostForm studyPostForm, 
                         BindingResult bindingResult, 
                         Model model,
                         @AuthenticationPrincipal CustomUserDetails loginUser) {
        // LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login"; // 로그인안한 사람은 게시판 글 못씀
        }

        if (bindingResult.hasErrors()) {
            // 콤보박스용 데이터 모델
            model.addAttribute("categoryList", this.categoryService.getCategoryList());
            return "/post/form";
        }

        studyPostForm.setUserId(loginUser.getUserId()); // 세션에 로그인 한 사용자 아이디       
        
        studyPostService.createPost(studyPostForm);
        return "redirect:/studypost/list";
    }

    // 댓글 기능 추가
    // 스터디게시글 상세보기
    @GetMapping("/detail/{postId}")
    public String detail(@PathVariable Long postId, Model model, @AuthenticationPrincipal CustomUserDetails loginUser) {
        // LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        StudyPost post = this.studyPostService.getPostDetail(postId);
        if (post == null) {
            return "redirect:/studypost/list";
        }

        boolean canEdit = false;
        boolean canDelete = false;
        boolean canProc = false;
        boolean canReq = true;

        if (loginUser != null) {
            boolean isWriter = post.getUserId().equals(loginUser.getUserId());
            boolean isAdmin = loginUser.getAuthorities().stream().anyMatch(au -> au.getAuthority().equals("ROLE_ADMIN"));

            canEdit = isWriter;
            canReq = !isWriter;  // 작성자는 신청할 수 없으므로
            canDelete = canProc = isWriter || isAdmin;
        }

        // templates/post/detail.html로 아래 model 객체를 전달
        model.addAttribute("canEdit", canEdit);  // 수정여부
        model.addAttribute("canDelete", canDelete);  // 삭제여부
        model.addAttribute("canProc", canProc);  // 승인/거절 사용여부
        model.addAttribute("canReq", canReq);   // 신청가능 여부
        model.addAttribute("post", post);  // 01. 최초 
        // 02.댓글리스트 추가
        model.addAttribute("commentList", this.commentService.getCommentList(postId)); 

        // 03.댓글작성 영역 추가
        CommentForm commentForm = new CommentForm();
        commentForm.setPostId(postId);
        model.addAttribute("commentForm", commentForm);  // 댓글작성폼

        // 04.스터디신청 리스트 추가
        model.addAttribute("applicationList", this.studyApplicationService.getApplicationListByPostId(postId));

        // 05.스터디신청 영역 추가
        StudyApplicationForm form = new StudyApplicationForm();
        form.setPostId(postId);
        model.addAttribute("studyApplicationForm", form);

        return "/post/detail";   // templates/post/detail.html
    }

    @PostMapping("/delete/{postId}")
    public String delete(@PathVariable Long postId,
                        @AuthenticationPrincipal CustomUserDetails loginUser) {
        // 로그인된 사용자가 없으면 로그인화면으로
        // LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        // 삭제할 스터디포스트 내용이 없으면 목록으로
        StudyPost post = this.studyPostService.getPostDetail(postId);

        if (post == null) {
            return "redirect:/studypost/list";
        }

        // 작성자만 삭제 가능
        if (!post.getUserId().equals(loginUser.getUserId())) {
            return "redirect:/studypost/detail/" + postId;
        }

        this.studyPostService.deletePost(postId);

        // 스터디포스트 목록으로.
        return "redirect:/studypost/list";
    }

    @GetMapping("/edit/{postId}")
    public String editForm(@PathVariable Long postId,
                        Model model,
                        @AuthenticationPrincipal CustomUserDetails loginUser) {
        // LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        StudyPost post = this.studyPostService.getPostDetail(postId);

        if (post == null) {
            return "redirect:/studypost/list";
        }

        // 작성자만 수정 가능
        if (!post.getUserId().equals(loginUser.getUserId())) {
            return "redirect:/studypost/detail/" + postId;
        }

        StudyPostForm studyPostForm = new StudyPostForm();
        studyPostForm.setPostId(post.getPostId());
        studyPostForm.setCategoryId(post.getCategoryId());
        studyPostForm.setTitle(post.getTitle());
        studyPostForm.setMaxMembers(post.getMaxMembers());
        studyPostForm.setContent(post.getContent());

        model.addAttribute("studyPostForm", studyPostForm);
        model.addAttribute("categoryList", this.categoryService.getCategoryList());

        return "/post/form";
    }

    @PostMapping("/edit/{postId}")
    public String edit(@PathVariable Long postId,
                    @Valid StudyPostForm studyPostForm,
                    BindingResult bindingResult,
                    Model model,
                    @AuthenticationPrincipal CustomUserDetails loginUser) {
        // LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        StudyPost post = this.studyPostService.getPostDetail(postId);

        if (post == null) {
            return "redirect:/studypost/list";
        }

        // 작성자만 수정 가능
        if (!post.getUserId().equals(loginUser.getUserId())) {
            return "redirect:/studypost/detail/" + postId;
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", this.categoryService.getCategoryList());
            return "/post/form";
        }

        studyPostForm.setPostId(postId);
        studyPostForm.setUserId(loginUser.getUserId());

        this.studyPostService.updatePost(studyPostForm);

        return "redirect:/studypost/detail/" + postId;
    }
}
