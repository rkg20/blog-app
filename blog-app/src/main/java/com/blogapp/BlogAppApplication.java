package com.blogapp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
		System.out.println("Welcome to Blog Application");
		
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}



	public void run(String... args)  throws Exception{
		System.out.println("Password : "+this.passwordEncoder.encode("rahul"));
		// $2a$10$2LCdaWf/eD44UTLinmeSU.aTdlZopaVF9FianuG5NkvI6RdzZgHgG   =admin  
		// $2a$10$cz.Fp3tg5GBFL91p0WQiR.wwKAuKwssI9eW7Jy3JNbXFAVs08nmsC   =rahul
	}
}
