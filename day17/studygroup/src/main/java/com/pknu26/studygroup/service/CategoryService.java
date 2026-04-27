package com.pknu26.studygroup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.Category;
import com.pknu26.studygroup.mapper.CategoryMapper;
import com.pknu26.studygroup.validation.CategoryForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryMapper categoryMapper;

    public List<Category> getCategoryList() {
        return categoryMapper.findAll();
    }

    public Category getCategory(Long categoryId) {
        return categoryMapper.findById(categoryId);
    }

    public void createCategory(CategoryForm categoryForm) {
        Category category = new Category();

        category.setCategoryName(categoryForm.getCategoryName());
        categoryMapper.insertCategory(category);
    }

    // category.setCategoryId(null) 때문에 오류발생!
    public void updateCategory(CategoryForm categoryForm) {
        Category category = new Category();
        category.setCategoryId(categoryForm.getCategoryId());
        category.setCategoryName(categoryForm.getCategoryName());
        
        categoryMapper.updateCategory(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryMapper.deleteCategory(categoryId);
    }
}
