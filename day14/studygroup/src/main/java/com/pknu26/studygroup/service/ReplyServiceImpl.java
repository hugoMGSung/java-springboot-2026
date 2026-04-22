package com.pknu26.studygroup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.Reply;
import com.pknu26.studygroup.mapper.ReplyMapper;
import com.pknu26.studygroup.validation.ReplyForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyMapper replyMapper;

    @Override
    public List<Reply> getReplyListByBoardId(Long boardId) {
        return replyMapper.findByBoardId(boardId);
    }

    @Override
    public void createReply(ReplyForm replyForm) {
        Reply reply = new Reply();
        reply.setBoardId(replyForm.getBoardId());
        reply.setReplyContent(replyForm.getReplyContent());
        reply.setReplyWriter(replyForm.getReplyWriter());

        replyMapper.insertReply(reply);
    }

    @Override
    public void deleteReply(Long replyId) {
        replyMapper.deleteReply(replyId);
    }
}
