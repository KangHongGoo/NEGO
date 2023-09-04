package kr.co.tj.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody MemberDTO dto) {
		Map<String, Object> map = new HashMap<>();
		
		if(dto.getUsername() != null && !dto.getUsername().equals("") && dto.getPassword() != null && !dto.getPassword().equals("")) {
			
			try {
				memberService.delete(dto);
				map.put("result", "성공");
				return ResponseEntity.ok().body(map);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("err", e.getMessage());
				return ResponseEntity.badRequest().body(map);
			}
			}else {
				return ResponseEntity.badRequest().body("삭제 할 데이터가 없습니다.");
			}
		
	}
	
	@PutMapping("")
	   public ResponseEntity<?> update(@RequestBody MemberDTO dto) {
	      
	      Map<String, Object> map = new HashMap<>();
	      

	      
	        if(dto == null) { map.put("result", "잘못된정보입니다."); return
	        ResponseEntity.badRequest().body(map); }
	       
	      
	      
	      try {
	         dto = memberService.update(dto);
	         map.put("result", dto);
	         return ResponseEntity.ok().body(map);
	      } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	         map.put("result", e.getMessage());
	         return ResponseEntity.badRequest().body(map);
	      }
	   }
	
	
	@GetMapping("/name/{username}")
	public ResponseEntity<?> findByUsername(@PathVariable("username") String username) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			MemberDTO dto = memberService.findByUsername(username);
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
