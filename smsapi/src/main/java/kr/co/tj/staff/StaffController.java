package kr.co.tj.staff;

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
@RequestMapping("/staff")
public class StaffController {
	
	@Autowired
	private StaffService staffService;
	
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody StaffDTO staffDTO) {
		Map<String, Object> map = new HashMap<>();
		
		
		
		if(staffDTO.getUsername() != null && !staffDTO.getUsername().equals("") && staffDTO.getPassword() != null && !staffDTO.getPassword().equals("")) {
			
			try {
				staffService.delete(staffDTO);
				map.put("result", "삭제 성공");
				return ResponseEntity.ok().body(map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("err", "실패");
				return ResponseEntity.badRequest().body(map);
			}
		} else {
			return ResponseEntity.badRequest().body("삭제 할 데이터가 없습니다.");
		}
		
		
		
	}
	/*
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody Map<String, String> map) {
		
		if(map == null) {
			return ResponseEntity.badRequest().body("삭제 할 데이터가 없습니다.");
		}
		
		String username = map.get("username");
		String password = map.get("password");
		
		if(username != null && !username.equals("") && password != null && !password.equals("")) {
			
			try {
				staffService.delete(username, password);
				map.put("result", "삭제 성공");
				return ResponseEntity.ok().body(map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("err", "실패");
				return ResponseEntity.badRequest().body(map);
			}
		} else {
			return ResponseEntity.badRequest().body("삭제 할 데이터가 없습니다.");
		}
		
		
		
	} */
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody StaffDTO staffDTO){
		
		Map<String , Object> map = new HashMap<>();
		
		if(staffDTO == null) {
			return ResponseEntity.badRequest().body("something wrong1");
		}
		
		if(staffDTO.getOrgPassword() == null) {
			return ResponseEntity.badRequest().body("something wrong2");
		}
		
		if(staffDTO.getUsername() == null) {
			return ResponseEntity.badRequest().body("something wrong3");
		}
		
		String password = staffDTO.getPassword();
		String password2 = staffDTO.getPassword2();
		
		if(password == null) {
			return ResponseEntity.badRequest().body("something wrong4");
		}
		
		if(password2 == null) {
			return ResponseEntity.badRequest().body("something wrong5");
		}
		
		if(!password.equals(password2)) {
			return ResponseEntity.badRequest().body("something wrong6");
		}

		StaffDTO db_staffDTO = staffService.findByUsernameAndPassword(
												staffDTO.getUsername(), 
												staffDTO.getOrgPassword());
		
		if(db_staffDTO == null) {
			return ResponseEntity.badRequest().body("something wrong7");
		}
		
		db_staffDTO.setPassword(password);
		
				
		try {
			staffDTO = staffService.update(db_staffDTO);
			map.put("result", staffDTO);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("err", "수정 실패");
			return ResponseEntity.badRequest().body(map);
		}
		
	}
	
	
	@PostMapping("")
	public ResponseEntity<?> save(@RequestBody StaffDTO staffDTO){
		if(staffDTO == null) {
			return ResponseEntity.badRequest().body("입력 정보에 문제가 있습니다.");
		}
		
		if(staffDTO.getId() != 0) {
			return ResponseEntity.badRequest().body("입력 정보에 문제가 있습니다.");
		}
		
		String password = staffDTO.getPassword();
		String password2 = staffDTO.getPassword2();
		
		if(password == null) {
			return ResponseEntity.badRequest().body("입력 정보에 문제가 있습니다.");
		}
		
		if(password2 == null) {
			return ResponseEntity.badRequest().body("입력 정보에 문제가 있습니다.");
		}
		
		if(!password.equals(password2)) {
			return ResponseEntity.badRequest().body("입력 정보에 문제가 있습니다.");
		}
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			staffDTO = staffService.save(staffDTO);
			map.put("result", staffDTO);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", "입력 실패");
			return ResponseEntity.badRequest().body(map);
		}
		
		
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> findAll(){
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<StaffDTO> list = staffService.findAll();
			map.put("result", list);
			return ResponseEntity.ok().body(map);
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("err", "에러 발생");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@GetMapping("/name/{username}")
	public ResponseEntity<?> findByUsername(@PathVariable("username") String username){
		if(username==null || username.equals("")) {
			return ResponseEntity.badRequest().body("이름이 없습니다.");
		}
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			StaffDTO dto = staffService.findByUsername(username);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("err", "실패");
			return ResponseEntity.badRequest().body(map);
		}
		
		
	}

}
