package com.pknu26.studygroup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pknu26.studygroup.dto.StudyPost;

// CRUD 중에서 Create, Read만 존재
@Mapper
public interface StudyPostMapper {
    List<StudyPost> findAll();

    StudyPost findById(Long postId);

    void insertPost(StudyPost studyPost);

    void deletePost(Long postId);

    void increaseViewCount(Long postId); // 조회수 증가

    void updatePost(StudyPost studyPost);
}
