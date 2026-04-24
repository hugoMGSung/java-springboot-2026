package com.pknu26.studygroup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pknu26.studygroup.dto.Comment;

@Mapper
public interface CommentMapper {
    List<Comment> findByPostId(Long postId);

    Comment findById(Long commentId);

    void insertComment(Comment comment);

    void deleteComment(Long commentId);
}
