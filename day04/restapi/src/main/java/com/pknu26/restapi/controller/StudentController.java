package com.pknu26.restapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.pknu26.restapi.entity.Student;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class StudentController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Boot API!";
    }

    @GetMapping("/student")
    public Student getStudent() {
        return new Student("유고", 49);
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        // 수정 불가능
        return List.of(
            new Student("유고", 49),
            new Student("애슐리", 42),
            new Student("홍길동", 19)
        );
    }

    @GetMapping("/hello2")
    public String hello2(@RequestParam String name) {
        return "Hello " + name;
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable int id) {
        return "User ID: " + id;
    }
}
