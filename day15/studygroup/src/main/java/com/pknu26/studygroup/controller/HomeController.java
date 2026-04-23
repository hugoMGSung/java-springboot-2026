package com.pknu26.studygroup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pknu26.studygroup.dto.HomeContent;
import com.pknu26.studygroup.service.SiteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SiteService siteService;

    @GetMapping("/")
    public String home(Model model) {

        HomeContent homeContent = this.siteService.getSiteListActive();
        // 리스트로 한꺼번에 받아서 반복문으로 전부 처리
        // Site site1 = this.siteService.getSiteByContentKey("CAROUSEL_1_TITLE");
        model.addAttribute("homeContent", homeContent);
        return "home"; 
    }
}
