package kr.co.tj.member;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller // http://localhost:9000/member/insert
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/checknickname")
	@ResponseBody
	public Map<String, String> checkNickName(String nickName) {
		Map<String, String> map = new HashMap<>();
		
		MemberDTO dto = memberService.getMember(nickName);
		
		if(dto == null) {
			map.put("result", "사용 가능");

		}
		
		map.put("result", "사용 불가");

				
		return map; 
		
	}
	
	@GetMapping("/signup") // http://localhost:9000/member/signup
	public String signup() {
		
		return "member/signup";
	}
	@PostMapping("/signup") // http://localhost:9000/member/signup
	public String signup(MemberForm memberForm) {
		
	
			memberService.save(memberForm);
	
		return "redirect:/"; 
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public String list(Model model, Principal principal, RedirectAttributes ratts) {
		String nickName = principal.getName();
		MemberDTO member = memberService.getMember(nickName);
		
		if(!member.getRole().equals("adm")) {
			ratts.addFlashAttribute("err", "권한이 없습니다.");
			
			return "redirect:/member/login";
		}
		
		List<MemberDTO> list = memberService.findAll();
		model.addAttribute("list", list);
		
		return "member/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/read/{nickName}") // http://localhost:9000/member/read/m001
	public String read(@PathVariable("nickName") String nickName, Model model,Principal principal, RedirectAttributes ratts) {
		
		String lognier = principal.getName();
		
		MemberDTO dto_loginer = memberService.getMember(lognier);
		
		if(!dto_loginer.getRole().equals("adm")) {
			
			boolean result = isSameMember(nickName, lognier);
				
			if(!result) {
			ratts.addFlashAttribute("err", "권한이 없습니다.");
			
			return "redirect:/member/login";
		}
		
		
		}
		
		MemberDTO dto = memberService.getMember(nickName);
		
		
		
		model.addAttribute("dto", dto);
		
		return "/member/read";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/update/{nickName}")
	public String update(@PathVariable("nickName") String nickName, Model model,Principal principal ,RedirectAttributes ratts) {
		
		MemberDTO dto = memberService.getMember(nickName);
		
		boolean result =isSameMember(nickName, principal.getName());
		
		if(!result) {
			ratts.addFlashAttribute("err", "권한이 없습니다.");
				
				return "redirect:/member/login";
			
					
		}
		
		model.addAttribute("dto", dto);
		
		if(!dto.getName().equals(principal.getName())) {
			ratts.addFlashAttribute("err", "권한이 없습니다.");
		}
		
		return "/member/update";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update")
	public String update(MemberDTO dto, Principal principal, RedirectAttributes rttr) {
		String nickName = dto.getNickName();
		String loginer = principal.getName();
		
		boolean result = isSameMember(nickName, loginer);
		
		if(!result) {
			rttr.addFlashAttribute("err", "권한이없습니다.");
			return "redirect:/member/login";
		}
		
		dto =  memberService.save(dto);
		
		if(dto != null) {
			
			
			return "redirect:/member/read/"+nickName;
		}else {
			return "redirect:/member/update/"+nickName;
		}
		
		
			}
		
		
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{nickName}")
			public String delete(@PathVariable("nickName") String nickName, Model model, Principal principal, RedirectAttributes rttr) {
				
		
		boolean result = isSameMember(nickName, principal.getName());
		
		if(!result) {
			
			rttr.addFlashAttribute("err", "권한이 없습니다.");
			
			return "redirect:/member/login";
		}
		
		MemberDTO dto = memberService.getMember(nickName);
				
				
				model.addAttribute("dto", dto);
				
				return "member/delete";
		
	
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/delete")
	public String delete(MemberDTO dto, Principal principal, RedirectAttributes rttr) {
		
		String nickName = dto.getNickName();
		String loginer = principal.getName();
		
		boolean result = isSameMember(nickName, loginer);
		
		if(!result) {
			rttr.addFlashAttribute("err", "권한이 없습니다.");
			
			return "redirect:/member/login";
		}
		
		memberService.delete(dto);
		
		return "redirect:/member/logout";
	}
	
	@GetMapping("/login")
	public String login(@ModelAttribute("err") String err) {
		return "/member/login";
	}
//	Spring Security가 이 작업을 가로채서 진행하므로 만들지 않음
//	@PostMapping("/login")
//	public String~

	public boolean isSameMember(String nickName, String loginer) {
	
		
		return nickName.equals(loginer);
	}
	
	
	
	
	
	
	
	
	
}
