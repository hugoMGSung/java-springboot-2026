package com.pknu26.studygroup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.studygroup.config.AdminHelper;
import com.pknu26.studygroup.service.SiteImageService;
import com.pknu26.studygroup.validation.SiteImageForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/admin/image")
@RequiredArgsConstructor
public class SiteImageController {

    private final SiteImageService siteImageService;

    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        AdminHelper.checkAdmin(session);

        model.addAttribute("imageList", this.siteImageService.getImageList());
        return "/admin/siteImage/list"; // list.html 출력
    }

    @GetMapping("/create")
    public String createForm(Model model, HttpSession session) {
        AdminHelper.checkAdmin(session);

        SiteImageForm form = new SiteImageForm();
        form.setUseYn("Y");
        model.addAttribute("siteImageForm", form);

        return "/admin/siteImage/form";
    }

    @PostMapping("/create")
    public String create(@Valid SiteImageForm form, BindingResult bindingResult, Model model, HttpSession session) {
        AdminHelper.checkAdmin(session);

        if (bindingResult.hasErrors()) {
            return "/admin/siteImage/form";
        }

        try {
            this.siteImageService.create(form);  // 파일 저장, DB처리
        } catch (IllegalArgumentException e) {
            bindingResult.reject("createError", e.getMessage());
            return "/admin/siteImage/form";
        } catch (Exception e) {
            bindingResult.reject("createError", "파일 업로드 중 오류가 발생했습니다.");
            return "/admin/siteImage/form";
        }
        
        return "redirect:/admin/image/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        AdminHelper.checkAdmin(session);
        this.siteImageService.delete(id);

        return "redirect:/admin/image/list";
    }    
}
