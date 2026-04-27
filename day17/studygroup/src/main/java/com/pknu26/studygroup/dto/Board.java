package com.pknu26.studygroup.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data -> @Getter/@Setter/@ToString/@RequiredArgsConstructor/...
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    private Long boardId;  // DB, board_id    
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt; // DB, created_at
    private LocalDateTime updatedAt; // DB, updated_at    

    // 테이블과 관련없는 추가내용
    private int replyCount;  // 댓글개수 변수 추가
    private String name;  /// 작성자 계정이름
}
