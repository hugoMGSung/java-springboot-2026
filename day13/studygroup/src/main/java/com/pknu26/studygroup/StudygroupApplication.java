package com.pknu26.studygroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class StudygroupApplication {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encoded = encoder.encode("p@ssw0rd!");
		System.out.println("password - " + encoded);

		SpringApplication.run(StudygroupApplication.class, args);
	}

}
