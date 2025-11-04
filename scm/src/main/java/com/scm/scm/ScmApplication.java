package com.scm.scm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import io.github.cdimascio.dotenv.Dotenv;

@Controller
@SpringBootApplication
public class ScmApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
		SpringApplication.run(ScmApplication.class, args);
	}
}
