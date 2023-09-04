package kr.co.tj.reply;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/reply")
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;
	
	//bid 추가
	@GetMapping("/{bid}")
	public ResponseEntity<?> findByBid(@PathVariable("bid") Long bid) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<ReplyDTO> list = replyService.findByBid(bid);
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
		
	
	}
	
	
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id){
		
		Map<String, Object> map = new HashMap<>();

		if (id == null) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			ReplyDTO dto = replyService.findById(id);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "댓글 가져오기 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
		
	
	
	
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<ReplyDTO> list=replyService.findAll();
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "댓글 목록 가져오기 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody ReplyDTO dto) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			dto = replyService.update(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}

	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody ReplyDTO dto) {

		Map<String, Object> map = new HashMap<>();

		if (dto == null) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			dto = replyService.save(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "댓글 작성 실패");
			return ResponseEntity.badRequest().body(map);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> map = new HashMap<>();

		try {
			replyService.delete(id);
			map.put("result", "댓글 삭제 성공");
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "댓글 삭제 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
}