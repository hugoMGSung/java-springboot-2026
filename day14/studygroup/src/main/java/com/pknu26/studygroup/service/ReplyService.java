package com.pknu26.studygroup.service;

import java.util.List;

import com.pknu26.studygroup.dto.Reply;
import com.pknu26.studygroup.validation.ReplyForm;

public interface ReplyService {

    List<Reply> getReplyListByBoardId(Long boardId);

    void createReply(ReplyForm replyForm);

    void deleteReply(Long replyId);
}
