package com.pknu26.studygroup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.Comment;
import com.pknu26.studygroup.mapper.CommentMapper;
import com.pknu26.studygroup.validation.CommentForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    public List<Comment> getCommentList(Long postId) {
        return commentMapper.findByPostId(postId);
    }

    public Comment getComment(Long commentId) {
        return commentMapper.findById(commentId);
    }

    public void createComment(CommentForm commentForm) {
        Comment comment = new Comment();
        comment.setPostId(commentForm.getPostId());
        comment.setUserId(commentForm.getUserId());
        comment.setContent(commentForm.getContent());

        commentMapper.insertComment(comment);
    }

    public void deleteComment(Long commentId) {
        commentMapper.deleteComment(commentId);
    }
}
