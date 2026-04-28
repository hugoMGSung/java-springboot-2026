package com.pknu26.studygroup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pknu26.studygroup.dto.StudyApplication;

@Mapper
public interface StudyApplicationMapper {

    // 신청등록
    void insertApplication(StudyApplication studyApplication);

    // 신청여부 카운트
    int countByPostIdAndUserId(@Param("postId") Long postId,
                               @Param("userId") Long userId);


    // 승인상태인 신청건수 확인
    int countByPostIdApproved(Long postId);

    // 스터디게시글에 해당하는 신청목록 조회                               
    List<StudyApplication> findByPostId(Long postId);

    // 사용자 아이디별로 신청한 목록
    List<StudyApplication> findByUserId(Long userId);

    // 신청상세
    StudyApplication findById(Long applicationId);

    // 상태변화
    void updateStatus(@Param("applicationId") Long applicationId,
                      @Param("status") String status);

    // 스터디포스트 신청승인한 갯수
    int countApprovedByPostId(Long postId);

    // 신청완전 삭제
    void deleteApplicationByPostId(Long postId);
}
