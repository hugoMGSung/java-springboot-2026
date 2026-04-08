package com.pknu26.restapi.entity;

public class Student {
    private String name;
    private int age;

    // 생성자
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }    
}
