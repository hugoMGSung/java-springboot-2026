package com.pknu26.studygroup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.studygroup.dto.Board;
import com.pknu26.studygroup.service.BoardService;
import com.pknu26.studygroup.validation.BoardForm;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// Controller에서 중요한 것은 mapping url. 메서드명 중요치않음
@Controller
@RequestMapping("/board") 
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 목록
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("boardList", this.boardService.readBoardList());
        return "/board/list"; // board 폴더 밑에 위치한 list.html을 리턴하라
    }
    
    // 목록상세보기
    @GetMapping("/detail/{boardId}")
    public String detail(@PathVariable("boardId") Long boardId, Model model) {
        Board board = this.boardService.readBoardById(boardId);
        model.addAttribute("board", board);
        return "/board/detail";
    }

    // 글쓰기 GET
    @GetMapping("/create")  // http://localhost:8080/board/create
    public String showCreateForm(BoardForm boardForm) {
        return "/board/form"; // .html 작성안함. /baord/form.html 화면 띄움
    }

    // 글쓰기 POST
    // BoardForm 입력검증 클래스, BindingResult 검증처리
    @PostMapping("/create") 
    public String create(@Valid BoardForm boardForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/board/form";
        }

        this.boardService.createBoard(boardForm);
        return "redirect:/board/list";
    }

    // 글수정 GET
    // 글수정 POST
    // 글삭제 POST
    @PostMapping("/delete/{boardId}")
    public String delete(@PathVariable("boardId") Long boardId) {
        this.boardService.deleteBoard(boardId);
        return "redirect:/board/list";
    }
}