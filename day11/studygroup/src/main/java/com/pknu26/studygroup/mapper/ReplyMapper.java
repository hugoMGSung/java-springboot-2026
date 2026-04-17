package com.pknu26.studygroup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pknu26.studygroup.dto.Reply;

@Mapper
public interface ReplyMapper {
       
    List<Reply> findByBoardId(Long boardId);

    int insertReply(Reply reply);

    int deleteReply(Long replyId);
}
