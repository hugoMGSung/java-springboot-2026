package com.pknu26.studygroup.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    private Long replyId;
    private Long boardId;
    private String replyContent;
    private String replyWriter;
    private LocalDateTime createdAt;
}
