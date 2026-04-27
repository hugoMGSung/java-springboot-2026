package com.pknu26.studygroup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pknu26.studygroup.dto.HomeContent;
import com.pknu26.studygroup.service.SiteImageService;
import com.pknu26.studygroup.service.SiteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SiteService siteService;
    private final SiteImageService siteImageService;

    @GetMapping("/")
    public String home(Model model) {

        HomeContent homeContent = this.siteService.getSiteListActive();
        HomeContent homeImage = this.siteImageService.getSiteImageListActive();

        // homeContent 멤버변수를 사용. homeImage 는 바로 사용안함
        homeContent.setCaro1ImagePath(homeImage.getCaro1ImagePath());
        homeContent.setCaro2ImagePath(homeImage.getCaro2ImagePath());
        homeContent.setCaro3ImagePath(homeImage.getCaro3ImagePath());

        // 리스트로 한꺼번에 받아서 반복문으로 전부 처리
        // Site site1 = this.siteService.getSiteByContentKey("CAROUSEL_1_TITLE");
        model.addAttribute("homeContent", homeContent);
        return "/home"; 
    }
}
