package kr.co.tj.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberRepository memberRepository;

	
	//회원수정
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody MemberDTO dto){
		Map<String, Object> map = new HashMap<>();
		//Nickname정보를 입력하지않거나 빈칸으로 입력했을떄 Exception날림
		if(dto==null || dto.getNickname()==null || dto.getNickname()=="") {
			throw new RuntimeException("입력된 정보가 없습니다.");
		}
		
		//비밀번호 3개중 1개의 값이 안들어올때 Exception날림
		if (dto.getPassword() == null && dto.getOrgPassword() == null && dto.getOrgPassword2() == null) {
			throw new RuntimeException("비밀번호를 확인해주세요1");
		}

		// 기존비밀번호와 입력받은 기존비밀번호가 일치하지않으면 Exception을 날림
//		MemberDTO dto2 = memberService.FindByUsername(dto.getUsername());
//		if (!dto2.getPassword().equals(dto.getPassword())) {
//			throw new RuntimeException("비밀번호를 확인해주세요2");
//		}

		//신규입력한 비밀번호 2개가 일치하지 않으면 Exception날림
		if(!dto.getOrgPassword().equals(dto.getOrgPassword2())) {
			throw new RuntimeException("비밀번호를 확인해주세요");
		}
		try {
			dto = memberService.update(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "수정실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	// passwordEncoder 적용한 회원 삭제 
	   @DeleteMapping("")
	   public ResponseEntity<?> delete(@RequestBody MemberDTO dto) {
	       Map<String, Object> map = new HashMap<>();
	       
	       // 회원 아이디나 비밀번호가 비어있는 경우
	       if(dto.getUsername() == null || dto.getUsername().equals("") || dto.getPassword() == null || dto.getPassword().equals("")) {
	           throw new RuntimeException("아이디나 비밀번호를 확인해주세요");
	       }
	       
	       try {
	           // 회원 정보 검색(username으로 entity 찾기)
	           MemberEntity entity = memberRepository.findByUsername(dto.getUsername());
	           if(entity == null) {
	               throw new RuntimeException("해당 회원 정보를 찾을 수 없습니다.");
	           }
	           
	           // 비밀번호 일치 확인(passwordEncoder로 암호화가 되어있기 때문에 passwordEncoder.matches로 비교해야 함)
	           if(!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
	               throw new RuntimeException("비밀번호가 일치하지 않습니다.");
	           }
	           
	           memberRepository.delete(entity);
	           
	           map.put("result", "삭제 성공");
	           return ResponseEntity.ok().body(map);
	       } catch (Exception e) {
	           e.printStackTrace();
	           map.put("result", "삭제 실패");
	           return ResponseEntity.badRequest().body(map);
	       }
	   }
	
	//회원 모두 불러오기 (관리자용)
	@GetMapping("")
	public ResponseEntity<?> UserList(){
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<MemberDTO> list = memberService.FindAll();
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	//회원정보보기
	@GetMapping("/{username}")
	public ResponseEntity<?> UserDetail(@PathVariable("username") String username, @RequestHeader("Authorization") String tokenHeader){
		Map<String, Object> map = new HashMap<>();
		
		if(username==null || username=="") {
			throw new RuntimeException("회원이없습니다.");
		}
		//로그인된 사용자 토큰과 회원정보의username의 토큰일치성확인
		String token = tokenHeader.substring(7);
		MemberDTO dto = memberService.findByToken(username);
		try {
			if(token.equals(dto.getToken())) {
				map.put("result", dto);
				return ResponseEntity.ok().body(map);
			}
			throw new RuntimeException("회원이 맞지않아 볼수없습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}
}
