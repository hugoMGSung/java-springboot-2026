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
import com.pknu26.webboard.validation.ReplyForm;

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
    public String showDetail(Model model, ReplyForm replyForm, @PathVariable("bno") Long bno) {
        Board board = this.boardService.getBoardOne(bno);

        model.addAttribute("board", board);
        return "board_detail";  // board_detail.html
    }

    // 게시글 작성
    @GetMapping("/create")
    public String showCreate(Model model, BoardForm boardForm) {
        model.addAttribute("mode", "create"); // 같은 화면의 Post액션을 분리. 생성
        // BoardForm 객체는 thymeleaf에서 전달가능
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

    // 게시글 수정화면 전환
    @GetMapping("/modify/{bno}")
    public String showModify(Model model, BoardForm boardForm, @PathVariable("bno") Long bno) {
        // bno 번호로 데이터를 조회한 다음 html 화면으로 전달
        Board board = this.boardService.getBoardOne(bno);
        boardForm.setTitle(board.getTitle()); // 실제 DB에서 넘어온 board 데이터를 입력용 BoardForm 클래스 멤버변수로 할당
        boardForm.setContent(board.getContent());

        model.addAttribute("mode", "modify");  // 수정
        model.addAttribute("bno", board.getBno());
        return "board_create"; // board_create.html 에서 수정도 가능
    }
    
    // 게시글 수정 DB처리
    @PostMapping("/modify/{bno}")
    public String modifyBoard(@Valid BoardForm boardForm, BindingResult bindingResult, @PathVariable("bno") Long bno) {
        if (bindingResult.hasErrors()) {
            return "board_create";  // 
        }
        // boardService에 수정용 메서드를 추가
        Board board = this.boardService.getBoardOne(bno);
        this.boardService.putBoardOne(board, boardForm.getTitle(), boardForm.getContent());
        
        return "redirect:/board/list"; // 수정 후 목록으로 돌아감
    }
    
    // 게시글 삭제 처리
    @GetMapping("/delete/{bno}")
    public String deleteBoard(@PathVariable("bno") Long bno) {
        Board board = this.boardService.getBoardOne(bno);  // 삭제할 데이터 가져오기

        // 필요 처리 후
        this.boardService.deleteBoardOne(board);

        return "redirect:/board/list"; // 삭제 후 목록으로 돌아감
    }    
}
