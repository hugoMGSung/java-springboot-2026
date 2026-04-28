package com.pknu26.studygroup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pknu26.studygroup.service.ReplyService;
import com.pknu26.studygroup.validation.ReplyForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

     private final ReplyService replyService;

    @PostMapping("/create")    
    public String createReply(@Valid @ModelAttribute ReplyForm replyForm, BindingResult bindingResult, 
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) { 
            // Model로 html에 전달하는 것과 동일
            redirectAttributes.addFlashAttribute("message", "작성자 및 댓글내용을 입력하세요.");
            return "redirect:/board/detail/" + replyForm.getBoardId();    
        }

        replyService.createReply(replyForm);
        return "redirect:/board/detail/" + replyForm.getBoardId();
    }

    @PostMapping("/delete/{replyId}")
    public String deleteReply(@PathVariable Long replyId,
                              @RequestParam Long boardId) {
        replyService.deleteReply(replyId);
        return "redirect:/board/detail/" + boardId;
    }
}
