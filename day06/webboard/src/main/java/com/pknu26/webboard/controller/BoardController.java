package com.pknu26.webboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.webboard.entity.Board;
import com.pknu26.webboard.service.BoardService;
import com.pknu26.webboard.validation.BoardForm;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/board")  // 공통 URL http://localhost:8080/board
@Controller
public class BoardController {    

    @Autowired
    private BoardService boardService;

    // BoardRepository와 같은 객체를 불러오면 Contoller에서 사용할 수 있도록 주입(DI)
    // public BoardController(BoardRepository boardRepository) {
    //     this.boardRepository = boardRepository;
    // }
    // public BoardController(BoardService boardService) {
    //     this.boardService = boardService;
    // }

    // Model은 파라미터만 지정하면 사용가능
    @GetMapping("/list")  // 상세 URL http://localhost:8080/board/list
    public String showList(Model model) {
        List<Board> boardList = this.boardService.getBoardList();  // Board 테이블 데이터 리스트

        model.addAttribute("boardList", boardList); // HTML로 보낼 모델데이터 설정
        return "board_list";  // board_list.html 파일 필요
    }


    // 게시글 한건 상세보기
    @GetMapping("/detail/{bno}")
    public String showDetail(Model model, @PathVariable("bno") Long bno) {
        Board board = this.boardService.getBoardOne(bno);

        model.addAttribute("board", board);
        return "board_detail";  // board_detail.html
    }

    // 게시글 작성
    @GetMapping("/create")
    public String showCreate(BoardForm boardForm) {
        // Model을 사용하지 않아도 html로 전달됨!!
        return "board_create";  // board_create.html 
    }

    @PostMapping("/create")
    public String createBoard(@Valid BoardForm boardForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board_create"; // board_create.html 다시 돌아감
        }

        // 실제 저장
        this.boardService.setBoardOne(boardForm.getTitle(), boardForm.getContent());
                
        return "redirect:/board/list";
    }
    
}
