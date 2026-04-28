package com.pknu26.studygroup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pknu26.studygroup.dto.Category;

@Mapper // ! 'com.pknu26.studygroup.mapper.CategoryMapper' that could not be found. 에러발생!
public interface CategoryMapper {
    List<Category> findAll();

    Category findById(Long categoryId);

    int countByName(String categoryName);

    void insertCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Long categoryId);
}
