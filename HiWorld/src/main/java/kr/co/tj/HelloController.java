package kr.co.tj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	
	
	
	@GetMapping("/hello")
	public String hello(String id, String pw) {
		System.out.println(id);
		System.out.println(pw);
		
		return "hello";
	}
	
	@PostMapping("/say")
	public String say(String id, String pw) {
		System.out.println(id);
		System.out.println(pw);
		return "redirect:/good?test1=3&test2=100";
	}
	
	@GetMapping("/good")
	public String good(String test1, String test2) {
		System.out.println(test1);
		System.out.println(test2);
		return "good";
	}


}
