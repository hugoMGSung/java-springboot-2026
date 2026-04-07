package com.pknu26.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		// StringBuilder : String 을 자유롭게 활용하게 만든 클래스
		StringBuilder sb = new StringBuilder();
		sb.append("Hello");
		sb.append(" SpringBoot!");

		System.out.println(sb);		
	}
}
