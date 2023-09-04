package kr.co.tj.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.member.MemberDTO;
import kr.co.tj.member.MemberService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login (@RequestBody MemberDTO dto){
		Map<String, Object> map = new HashMap<>();
		
		if(dto==null) {
			return ResponseEntity.badRequest().body("dto정보가 없습니다.");
		}
		
		if(dto.getUsername()==null || dto.getUsername().equals("")) {
			return ResponseEntity.badRequest().body("유저 없음");
		}
		
		if(dto.getPassword()==null || dto.getPassword().equals("")) {
			return ResponseEntity.badRequest().body("비번 없음");
		}
		
		try {
			dto = memberService.login(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	
	//회원가입
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody MemberDTO dto){
		
		
		Map<String, Object> map = new HashMap<>();
		
		if(dto==null) { //입력정보가 안들어왔을때
			throw new RuntimeException("dto정보가 없습니다");
		}

		if(dto.getUsername()==null || dto.getUsername()=="" || dto.getNickname()==null || dto.getNickname()=="") {
			throw new RuntimeException("유저정보가 없습니다");
		}
		
		if(!dto.getPassword().equals(dto.getOrgPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}

		try {
			dto = memberService.save(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
		
	}
}
