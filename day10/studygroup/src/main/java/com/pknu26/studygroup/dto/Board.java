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

    private int replyCount;  // 댓글개수 변수 추가
}
