package com.pknu26.studygroup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.studygroup.dto.Category;
import com.pknu26.studygroup.dto.LoginUser;
import com.pknu26.studygroup.service.CategoryService;
import com.pknu26.studygroup.validation.CategoryForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin/categories")  // localhost:8080/admin/categories/...
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // localhost:8080/admin/categories 리스트로
    @GetMapping
    public String list(Model model, HttpSession session) {
        checkAdmin(session); // 정말 관리자인지 한번 더 체크
        model.addAttribute("categoryList", categoryService.getCategoryList());
        return "admin/category/list"; // html
    }

    // localhost:8080/admin/categories/create
    @GetMapping("/create")
    public String createForm(Model model, HttpSession session) {
        checkAdmin(session);

        model.addAttribute("categoryForm", new CategoryForm());
        return "admin/category/form";
    }

    // localhost:8080/admin/categories/create
    @PostMapping("/create")
    public String create(@Valid CategoryForm categoryForm, BindingResult bindingResult,
                         Model model, HttpSession session) {
        checkAdmin(session);

        if (bindingResult.hasErrors()) {
            return "admin/category/form";
        }

        this.categoryService.createCategory(categoryForm);
        return "redirect:/admin/categories"; // URL 호출 http://localhost:8080/admin/categories
    }

    // localhost:8080/admin/categories/edit/2
    @GetMapping("/edit/{categoryId}")
    public String editForm(@PathVariable Long categoryId,
                           Model model,
                           HttpSession session) {
        checkAdmin(session);

        Category category = this.categoryService.getCategory(categoryId);
        
        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setCategoryId(category.getCategoryId());
        categoryForm.setCategoryName(category.getCategoryName());

        model.addAttribute("categoryForm", categoryForm);
        return "admin/category/form";
    }

    @PostMapping("/edit/{categoryId}")
    public String edit(@PathVariable Long categoryId,                       
                        @Valid @ModelAttribute("categoryForm") CategoryForm categoryForm,
                        BindingResult bindingResult, HttpSession session) {
        checkAdmin(session);

        if (bindingResult.hasErrors()) {
            return "admin/category/form";
        }

        categoryForm.setCategoryId(categoryId);
        this.categoryService.updateCategory(categoryForm);
        return "redirect:/admin/categories";        
    }

    @PostMapping("/delete/{categoryId}")
    public String delete(@PathVariable Long categoryId, HttpSession session) {
        checkAdmin(session);
        this.categoryService.deleteCategory(categoryId);
        return "redirect:/admin/categories";
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
