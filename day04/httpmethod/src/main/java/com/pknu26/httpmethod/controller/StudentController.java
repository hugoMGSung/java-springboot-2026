package com.pknu26.httpmethod.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;  // GetMapping, PostMapping, RequestMapping 모두 사용하겠다

import com.pknu26.httpmethod.entity.Student;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/students")
public class StudentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/list")
    public String getAllStudent() {
        return "list";  // list.html 과 연결
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());  
        // student라는 이름의 모델을 Student 클래스 객체생성 후 create.html 페이지로 전달
        return "create";   // create.html 과 연결
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Student student, Model model) {
        logger.info(student.getName());
        logger.info(String.valueOf(student.getAge()));

        model.addAttribute("student", student);
        // return "redirect:/students/list"; // 다시 첫화면으로 이동
        return "result";  // result.html 화면에 띄움
    }

    @GetMapping("/search")
    public String search(@RequestParam String name, @RequestParam int age, Model model) {
        logger.info("검색 이름 : {}", name);
        logger.warn("검색 나이 : {}", age);

        model.addAttribute("result", "검색 완료!");
        return "list";
    }

    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable int id, Model model) {
        logger.info("조회 ID : {}", id);

        // 실제로는 DB 조회
        Student student = new Student();
        student.setName("애슐리");
        student.setAge(42);

        model.addAttribute("student", student);

        return "create";
    }
}
