package com.pknu26.webboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.webboard.entity.Board;
import com.pknu26.webboard.service.BoardService;
import com.pknu26.webboard.service.ReplyService;
import com.pknu26.webboard.validation.ReplyForm;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/reply")
@Controller
public class ReplyController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyService replyService;

    @PostMapping("/create/{bno}")
    public String createReply(Model model, @Valid ReplyForm replyForm, 
                              BindingResult bindingResult, @PathVariable("bno") Long bno) {
        Board board = this.boardService.getBoardOne(bno);

        // 댓글 내용을 안넣고 댓글저장 버튼을 누르면
        if (bindingResult.hasErrors()) { 
            model.addAttribute("board", board);
            return "board_detail";
        }

        this.replyService.setReply(board, replyForm.getContent());
        return "redirect:/board/list";
    }
}
