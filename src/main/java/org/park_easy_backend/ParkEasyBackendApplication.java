package org.park_easy_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ParkEasyBackendApplication {
	public static void main(String[] args) {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		SpringApplication.run(ParkEasyBackendApplication.class, args);


	}

	@GetMapping("/test")
	public String endpointTest() {
		return "ok";
	}
}
