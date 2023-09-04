package kr.co.tj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWorldApplication {

	public static void main(String[] args) {
		System.out.println("헬로우월드");
		SpringApplication.run(HelloWorldApplication.class, args);
	}

}
