package com.pknu26.webboard.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pknu26.webboard.entity.Board;
import com.pknu26.webboard.entity.Reply;
import com.pknu26.webboard.repository.ReplyRepository;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    public void setReply(Board board, String content) {
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setCreateDate(LocalDateTime.now());
        reply.setBoard(board);  // 게시판 연결!! 부모테이블 데이터 지정. board.bno가 Reply에 저장

        this.replyRepository.save(reply);        
    }
}
