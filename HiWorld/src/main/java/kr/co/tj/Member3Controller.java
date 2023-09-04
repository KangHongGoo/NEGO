package kr.co.tj;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member3")
public class Member3Controller {
	
	@GetMapping("/insert")
	public String insert() {
		return "/member/insert";
	}
	
	@PostMapping("/insert")
	public String insert(MemberDTO dto) {
		System.out.println(dto.getId());
		
		return "redirect:/member3/list";
	}

}
