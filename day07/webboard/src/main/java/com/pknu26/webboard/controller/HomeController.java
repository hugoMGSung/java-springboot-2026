package com.pknu26.webboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/home")
    public String home() {
        logger.info("/home 실행"); 
        return "home";
    }
    
}
