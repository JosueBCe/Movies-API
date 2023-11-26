package dev.josue.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@SpringBootApplication
@RestController
public class MovieAPI {

	public static void main(String[] args) {
		SpringApplication.run(MovieAPI.class, args);

	}
	@GetMapping("/")
	public String apiRoot(){
		return "Hello World";
	}

}
