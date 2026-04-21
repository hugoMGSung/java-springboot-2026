package com.pknu26.studygroup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.StudyPost;
import com.pknu26.studygroup.mapper.StudyPostMapper;
import com.pknu26.studygroup.validation.StudyPostForm;

@Service
public class StudyPostService {

    @Autowired
    private StudyPostMapper studyPostMapper;

    public List<StudyPost> getPostList() {
        return this.studyPostMapper.findAll();
    }

    public StudyPost getPostDetail(Long postId) {
        this.studyPostMapper.increaseViewCount(postId);
        return this.studyPostMapper.findById(postId);
    }

    public void createPost(StudyPostForm studyPostForm) {
        StudyPost studyPost = new StudyPost();
        studyPost.setUserId(studyPostForm.getUserId());
        studyPost.setCategoryId(studyPostForm.getCategoryId());
        studyPost.setTitle(studyPostForm.getTitle());
        studyPost.setContent(studyPostForm.getContent());
        studyPost.setMaxMembers(studyPostForm.getMaxMembers());
        // status 상태는 mapper에서 OPEN으로
        // view_count = 0 입력됨

        this.studyPostMapper.insertPost(studyPost);
    }

    public void deletePost(Long postId) {
        this.studyPostMapper.deletePost(postId);
    }

    public void updatePost(StudyPostForm studyPostForm) {
        StudyPost studyPost = this.studyPostMapper.findById(studyPostForm.getPostId());
        studyPost.setCategoryId(studyPostForm.getCategoryId());
        studyPost.setTitle(studyPostForm.getTitle());
        studyPost.setContent(studyPostForm.getContent());
        studyPost.setMaxMembers(studyPostForm.getMaxMembers());

        this.studyPostMapper.updatePost(studyPost);
    }
}
