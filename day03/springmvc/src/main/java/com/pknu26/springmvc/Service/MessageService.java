package com.pknu26.springmvc.Service;

import org.springframework.stereotype.Service;

// 어노테이션 추가
@Service
public class MessageService {

    public String getHomeMessage() {
        return "반갑습니다. Spring MVC 공부해요~";
    }
}
