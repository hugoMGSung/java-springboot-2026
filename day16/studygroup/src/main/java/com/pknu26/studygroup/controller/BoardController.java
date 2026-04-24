package com.pknu26.studygroup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.studygroup.dto.Board;
import com.pknu26.studygroup.dto.LoginUser;
import com.pknu26.studygroup.dto.PageRequest;
import com.pknu26.studygroup.service.BoardService;
import com.pknu26.studygroup.service.ReplyService;
import com.pknu26.studygroup.validation.BoardForm;
import com.pknu26.studygroup.validation.ReplyForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// Controller에서 중요한 것은 mapping url. 메서드명 중요치않음
@Controller
@RequestMapping("/board") 
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyService replyService;  // 댓글도 가져와야 함

    // 목록, 페이징 용 변경
    @GetMapping("/list")
    public String list(@ModelAttribute PageRequest pageRequest, Model model) {
        model.addAttribute("response", this.boardService.readBoardList(pageRequest));
        return "/board/list"; // board 폴더 밑에 위치한 list.html을 리턴하라
    }
    
    // 목록상세보기    
    @GetMapping("/detail/{boardId}")
    public String detail(@PathVariable("boardId") Long boardId, Model model) {
        Board board = this.boardService.readBoardById(boardId);

        model.addAttribute("board", board);
        // 댓글 리스트 추가
        model.addAttribute("replyList", this.replyService.getReplyListByBoardId(boardId));

        ReplyForm replyForm = new ReplyForm();
        replyForm.setBoardId(boardId);
        model.addAttribute("replyForm", replyForm);

        return "/board/detail";
    }

    // 글쓰기 GET
    // #PRC01 - 웹 브라우저에서 URL로 요청
    @GetMapping("/create")  // http://localhost:8080/board/create
    public String showCreateForm(Model model, HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login"; // 로그인안한 사람은 게시판 글 못씀
        }

        // 로그인한 계정 정보를 html 전달
        BoardForm boardForm = new BoardForm();
        // 세션에서 넘어온 정보 할당
        boardForm.setWriter(loginUser.getName());
        boardForm.setWriterId(loginUser.getLoginId()); 

        model.addAttribute("boardForm", boardForm);

        // #PRC02 - 입력값 검증 BoardForm을 읽어온 후, /board/form.html에 model로 전달
        return "/board/form"; // .html 작성안함. /baord/form.html 화면 띄움
    }

    // 글쓰기 POST
    // #PRC04 - 폼화면에서 저장버튼 누르면 발생.
    // BoardForm 입력검증 클래스, BindingResult 검증처리
    @PostMapping("/create") 
    public String create(@Valid BoardForm boardForm, BindingResult bindingResult, HttpSession session) throws Exception {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login"; // 로그인안한 사람은 게시판 글 못씀
        }        

        // #PRC05 - 입력에 에러가 있으면 다시 폼화면으로 이동
        if (bindingResult.hasErrors()) {
            // throw new Exception("가짜 에러!");
            return "/board/form";
        }

        // #PRC06 - 입력값이 정확하면 서비스에 메서드 호출. 파라미터로 폼값
        this.boardService.createBoard(boardForm);
        // #PRC15 - 화면이 목록화면으로 표시
        return "redirect:/board/list";
    }

    // 글수정 GET
    @GetMapping("/edit/{boardId}")
    public String showEditForm(@PathVariable("boardId") Long boardId, Model model, HttpSession session) {
        Board board = this.boardService.readBoardById(boardId);

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login"; // 로그인안한 사람은 게시판 글 못씀
        }

        BoardForm boardForm = new BoardForm();
        boardForm.setBoardId(board.getBoardId());
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        boardForm.setWriter(loginUser.getName());
        boardForm.setWriterId(loginUser.getLoginId());

        model.addAttribute("boardForm", boardForm);
        return "/board/form";
    }

    // 글수정 POST
    @PostMapping("/edit/{boardId}")
    public String edit(@PathVariable("boardId") Long boardId,
                       @Valid @ModelAttribute("boardForm") BoardForm boardForm,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/board/form";
        }

        boardForm.setBoardId(boardId);
        this.boardService.updateBoard(boardForm);
        return "redirect:/board/detail/" + boardId;  // 수정된 자신글 상세로 이동
    }

    // 글삭제 POST
    @PostMapping("/delete/{boardId}")
    public String delete(@PathVariable("boardId") Long boardId) {
        this.boardService.deleteBoard(boardId);
        return "redirect:/board/list";
    }
}