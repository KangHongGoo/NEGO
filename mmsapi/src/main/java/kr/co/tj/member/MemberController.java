package kr.co.tj.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;

	
	@PostMapping("")
	public ResponseEntity<?> signup(@RequestBody MemberDTO memberDTO) {
		Map<String, Object> map = new  HashMap<>();
		
		if(memberDTO == null) {
			return ResponseEntity.badRequest().body("실패");
		}
		
		
		try {
			memberDTO = memberService.save(memberDTO);
			map.put("result", memberDTO);
			return ResponseEntity.ok().body(map);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", "실패했어요");
			return ResponseEntity.badRequest().body(map);
		}
	}

	
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody MemberDTO memberDTO) {
		Map<String, Object> map = new HashMap<>();
		
		if(memberDTO.getUsername() != null && !memberDTO.getUsername().equals("") && memberDTO.getPassword() != null && !memberDTO.getPassword().equals("")) {
			
		
		
		try {
			memberService.delete(memberDTO);
			map.put("result", "성공");
			return ResponseEntity.ok().body(map);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
		}else {
			return ResponseEntity.badRequest().body("삭제 할 값이 없습니다 ");
		}
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody MemberDTO memberDTO) {
		
		if(memberDTO == null) {
			return ResponseEntity.badRequest().body("X");
		}
		
		if(memberDTO.getOrgPassword() == null) {
			return ResponseEntity.badRequest().body("X2");
		}
		
		if(memberDTO.getUsername() == null) {
			return ResponseEntity.badRequest().body("X3");
		}
		
		String password = memberDTO.getPassword();
		String password2 = memberDTO.getPassword2();
		
		if(password == null) { 
			return ResponseEntity.badRequest().body("X4");
		}
		
		if(password2 == null) {
			return ResponseEntity.badRequest().body("X5");
		}
		
		if(!password.equals(password2)) {
			return ResponseEntity.badRequest().body("X6");
		}
		
		 MemberDTO db_memberDTO = memberService.findByUsernameAndPassword(memberDTO.getUsername(), memberDTO.getOrgPassword());
		 
		 if(db_memberDTO == null) {
				return ResponseEntity.badRequest().body("something wrong7");
			}
			
		 db_memberDTO.setPassword(password);
		
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			memberDTO = memberService.update(memberDTO);
			map.put("result", memberDTO);
			return ResponseEntity.ok().body(map);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", e.getMessage());
			return ResponseEntity.ok().body(map);
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> findByUsername(@PathVariable("username") String username) {
		Map<String, Object> map =new HashMap<>();
		
		try {
			MemberDTO dto = memberService.findByUsername(username);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", "실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<MemberDTO> list = memberService.findAll();
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", "실패");
			return ResponseEntity.badRequest().body(map);
		}
	}

}
