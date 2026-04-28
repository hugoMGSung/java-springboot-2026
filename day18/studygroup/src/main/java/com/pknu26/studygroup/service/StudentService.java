package com.pknu26.studygroup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.Student;
import com.pknu26.studygroup.mapper.StudentMapper;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public List<Student> getAllStudents() {
        return studentMapper.findAll();
    }

    public Student getStudentById(Long id) {
        return studentMapper.findById(id);
    }

    public int addStudent(Student student) {
        return studentMapper.insert(student);
    }
}
