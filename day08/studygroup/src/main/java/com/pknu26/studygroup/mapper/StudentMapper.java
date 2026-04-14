package com.pknu26.studygroup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pknu26.studygroup.dto.Student;

// JpaRepository와 동일한 기능. 직접 구현
@Mapper
public interface StudentMapper {

    List<Student> findAll();

    Student findById(Long id);

    int insert(Student student);
}
