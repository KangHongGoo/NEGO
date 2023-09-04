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
	public ResponseEntity<?> login(@RequestBody MemberDTO dto) {
		Map<String, Object> map = new HashMap<>();
		
		if(dto == null) {
			map.put("result", "잘못된 데이터");
			return ResponseEntity.badRequest().body(map);
		}
	
		if(dto.getUsername() == null) {
			map.put("result", "잘못된 데이터");
			return ResponseEntity.badRequest().body(map);
		}
		
		
		if(dto.getPassword() == null) {
			map.put("result", "잘못된 데이터");
			return ResponseEntity.badRequest().body(map);
		}
		
		try {
			dto =  memberService.login(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
		
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody MemberDTO dto){
		Map<String, Object> map = new HashMap<>();
		
		if(dto == null) {
			return ResponseEntity.badRequest().body("실패");
		}
		if(dto.getUsername() == null) {
			return ResponseEntity.badRequest().body("아이디실패");
		}
		
		if(dto.getName() == null ) {
			return ResponseEntity.badRequest().body("이름실패");
		}
		
		String password = dto.getPassword();
		if(password == null) {
			return ResponseEntity.badRequest().body("비밀번호 실패");
		
		}
		
		
		try {
			dto = memberService.insert(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		} 
	}
	
	

}
