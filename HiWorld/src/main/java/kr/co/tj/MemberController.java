package kr.co.tj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/member")
public class MemberController {
	
	@GetMapping("/update/{id}")
	public String update(@PathVariable("id") Integer id, Model model) {
		return "/member/update";
	}
	
	
	 // querystring : localhost:9000/member/update?id=1
	@GetMapping("/update")
	public void update(Integer id)  { 
		// 반환형이 void일 때, jsp 파일은 어떻게 결정될까
		// uri를 따라간다
		
		
	}
	
	@GetMapping("/read/{id}") // localhost:9000/member/read/1
	public String read(@PathVariable("id") Integer id,Model model) {
		System.out.println("DB로부터 id에 해당하는 회원의 정보를 가져옵니다.");
		
		MemberDTO dto = new MemberDTO(id, "111", "kim");
		
		model.addAttribute("dto", dto);
		
		return "member/read";
	}
	

	@GetMapping("/list")
	public String list(Model model) {
		System.out.println("list 화면입니다.");
		
		List<MemberDTO> list = new ArrayList<>();
		
		list.add(new MemberDTO(1, "111", "kim"));
		list.add(new MemberDTO(2, "222", "lee"));
		list.add(new MemberDTO(3, "333", "park"));
		
		model.addAttribute("list", list);
		
		return "member/list";
	}
	
	@PostMapping("/insert")
	public String insert(MemberDTO dto) {
		
		System.out.println(dto.getId());
		System.out.println(dto.getPw());
		System.out.println(dto.getName());
		return "redirect:/member/list";
	}
	
	
	@GetMapping("/insert")
	public String insert() {
		return "member/insert";
	}
	
}
