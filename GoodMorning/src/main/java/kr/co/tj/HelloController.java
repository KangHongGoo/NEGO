package kr.co.tj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@GetMapping("/hi")
	public String hi() {
		return "hi";
	}
	
	@GetMapping("/good")
	
	public String good() {
		return "good";
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	
		
		
	}
