package com.pknu26.studygroup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pknu26.studygroup.dto.StudyPost;

// CRUD 중에서 Create, Read만 존재
@Mapper
public interface StudyPostMapper {

    List<StudyPost> findAll(@Param("offset") int offset, @Param("size") int size);

    // 페이징용 전체 게시글 갯수
    int getTotalCount();

    StudyPost findById(Long postId);

    void insertPost(StudyPost studyPost);

    void deletePost(Long postId);

    void increaseViewCount(Long postId); // 조회수 증가

    void updatePost(StudyPost studyPost);

    // 260422 상태변경용 추가 메서드
    void updatePostStatus(@Param("postId") Long postId, 
                          @Param("status") String status);
}
