package com.pknu26.studygroup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.StudyApplication;
import com.pknu26.studygroup.dto.StudyPost;
import com.pknu26.studygroup.mapper.StudyApplicationMapper;
import com.pknu26.studygroup.mapper.StudyPostMapper;
import com.pknu26.studygroup.validation.StudyApplicationForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyApplicationService {

    private final StudyApplicationMapper studyApplicationMapper;
    private final StudyPostMapper studyPostMapper; // 스터디 신청하려면 스터디 게시글이 필요

    public void apply(StudyApplicationForm form) {
        // HACK : 아래가 비즈니스 로직
        // 해당 스터디포스트 찾음
        StudyPost post = this.studyPostMapper.findById(form.getPostId());

        if (post == null) { // 글이 없으면
            throw new IllegalArgumentException("게시글이 없습니다.");
        }

        if (post.getUserId().equals(form.getUserId())) { // 자기가 작성한 스터디포스트에는 직접 신청할 수 없음
            throw new IllegalArgumentException("본인 글에는 신청할 수 없습니다.");
        }

        if (!"OPEN".equals(post.getStatus())) { // 스터디포스트 상태가 OPEN이 아니면 신청불가
            throw new IllegalArgumentException("마감된 스터디입니다.");
        }

        int exists = this.studyApplicationMapper.countByPostIdAndUserId(form.getPostId(), form.getUserId());
        if (exists > 0) { // 신청을 한 번했는데 또 할 수 없음
            throw new IllegalArgumentException("이미 신청한 스터디입니다.");
        }

        int approvedCount = this.studyApplicationMapper.countApprovedByPostId(form.getPostId());
        if (approvedCount >= post.getMaxMembers()) {  // 게시글에 정해진 최대인원을 넘어서서 신청할 수 없음
            throw new IllegalArgumentException("모집 인원이 마감되었습니다.");
        }

        StudyApplication studyApplication = new StudyApplication();
        studyApplication.setPostId(form.getPostId());
        studyApplication.setUserId(form.getUserId());
        studyApplication.setMessage(form.getMessage());
        // 스터디 신청 등록
        this.studyApplicationMapper.insertApplication(studyApplication);
    }

    // 스터디포스트에 총 신청목록
    public List<StudyApplication> getApplicationListByPostId(Long postId) {
        return studyApplicationMapper.findByPostId(postId);
    }

    // 사용자별 스터디신청목록
    public List<StudyApplication> getMyApplicationList(Long userId) {
        return studyApplicationMapper.findByUserId(userId);
    }

    // 스터디 신청 한건 조회
    public StudyApplication getApplication(Long applicationId) {
        return studyApplicationMapper.findById(applicationId);
    }

    // 260422. 스터디신청 제약을 위해서 StudyPost 도 전달
    public void approve(Long applicationId, StudyPost post) {
        
        // 스터디포스트 상태가 마감이면 더이상 승인불가
        if ("CLOSED".equals(post.getStatus())) {            
            throw new IllegalArgumentException("모집이 마감되었습니다.");
        }

        // 전체 멤버수를 넘어서는 신청은 거부        
        int approvedCount = this.studyApplicationMapper.countByPostIdApproved(post.getPostId());

        if (approvedCount >= post.getMaxMembers()) {
            throw new IllegalArgumentException("모집인원이 다 찼습니다.");
        }
        
        // 전체 멤버수를 채우는 신청의 경우는 게시글포스트의 상태를 CLOSED로 변경
        // 신청 승인 로직
        studyApplicationMapper.updateStatus(applicationId, "APPROVED");

        // 승인 후 다시 카운트
        approvedCount = this.studyApplicationMapper.countByPostIdApproved(post.getPostId());
        // 정원이 다 찼으면 CLOSED
        if (approvedCount >= post.getMaxMembers()) {
            // 상태만 바꾸는 메서드 추가
            this.studyPostMapper.updatePostStatus(post.getPostId(), "CLOSED");
        }
    }

    // 260422. 스터디 거절후 상태 변경 로직추가
    public void reject(Long applicationId, StudyPost post) {
        // 거절처리
        studyApplicationMapper.updateStatus(applicationId, "REJECTED");

        // 거절 후 승인인원 재조회
        int approvedCount = this.studyApplicationMapper.countByPostIdApproved(post.getPostId());

        // 승인인원이 정원보다 적으면 다시 OPEN
        if (approvedCount < post.getMaxMembers()) {
            // 상태를 OPEN으로 변경
            this.studyPostMapper.updatePostStatus(post.getPostId(), "OPEN");
        }
    }
}
