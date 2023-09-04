package kr.co.tj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member2")
public class Member2Controller {

	@PostMapping("/insert")
	public String insert(MemberDTO dto) {
		System.out.println(" mem2 start");
		System.out.println(dto.getId());
		System.out.println(dto.getPw());
		System.out.println(dto.getName());
		System.out.println("mem2 end");
		
		return "member2/list";
	}
	
	@GetMapping("/insert")
	public String insert() {
		return "member/insert";
	}
}
