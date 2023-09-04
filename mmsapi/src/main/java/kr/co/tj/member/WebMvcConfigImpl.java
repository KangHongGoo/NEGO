package kr.co.tj.member;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigImpl implements WebMvcConfigurer{
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("http://localhost:3001", "http://localhost:9000")
		.allowedMethods("GET", "POST", "PUT", "DELETE" )
		.allowedHeaders("*")
		.allowCredentials(true)
		.maxAge(3600);
	}

}
