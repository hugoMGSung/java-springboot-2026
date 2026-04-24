package com.pknu26.studygroup;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pknu26.studygroup.config.FileProperties;

@SpringBootApplication
public class StudygroupApplication implements CommandLineRunner {

	@Autowired
	private FileProperties fileProperties;

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encoded = encoder.encode("p@ssw0rd!");
		System.out.println("password - " + encoded);
		
		SpringApplication.run(StudygroupApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Path uploadDir = Path.of(fileProperties.getUploadDir());
		Files.createDirectories(uploadDir);
		System.out.println("uploadDir = " + uploadDir.toAbsolutePath());
		System.out.println("accessUrl = " + fileProperties.getAccessUrl() );
	}
}
