package com.pknu26.studygroup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.studygroup.dto.LoginUser;
import com.pknu26.studygroup.dto.Site;
import com.pknu26.studygroup.service.SiteService;
import com.pknu26.studygroup.validation.SiteForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin/site")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        checkAdmin(session);
        model.addAttribute("siteList", siteService.getSiteList());
        return "/admin/site/list"; 
    }

    @GetMapping("/create")
    public String createForm(Model model, HttpSession session) {
        checkAdmin(session);

        model.addAttribute("siteForm", new SiteForm());
        return "/admin/site/form";
    }

    @PostMapping("/create")
    public String create(@Valid SiteForm siteForm, BindingResult bindingResult,
                         Model model, HttpSession session) {
        checkAdmin(session);

        if (bindingResult.hasErrors()) {
            return "/admin/site/form";
        }

        this.siteService.create(siteForm);
        return "redirect:/admin/site/list"; 
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,
                           Model model,
                           HttpSession session) {
        checkAdmin(session);

        Site site = this.siteService.getSite(id);
        
        SiteForm siteForm = new SiteForm();
        siteForm.setId(site.getId());
        siteForm.setContentKey(site.getContentKey());
        siteForm.setContentBody(site.getContentBody());

        model.addAttribute("siteForm", siteForm);
        return "/admin/site/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,                       
                        @Valid @ModelAttribute("siteForm") SiteForm siteForm,
                        BindingResult bindingResult, HttpSession session) {
        checkAdmin(session);

        if (bindingResult.hasErrors()) {
            return "/admin/site/form";
        }

        siteForm.setId(id);
        this.siteService.update(siteForm);
        return "redirect:/admin/site/list";        
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        checkAdmin(session);
        this.siteService.delete(id);
        return "redirect:/admin/site/list";
    }
    
    // 한번더 관리자 체크
    private void checkAdmin(HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null || !"ROLE_ADMIN".equals(loginUser.getRole())) {            
            throw new RuntimeException("관리자만 접근할 수 있습니다.");
            // TODO : 에러페이지 추가 필요!
        }
    }
}
