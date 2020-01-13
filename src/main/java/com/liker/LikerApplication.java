package com.liker;

import com.liker.service.CliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LikerApplication implements CommandLineRunner {

	private final CliService cliService;

	@Autowired
	public LikerApplication(CliService cliService) {
		this.cliService = cliService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LikerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		cliService.run();
	}
}
