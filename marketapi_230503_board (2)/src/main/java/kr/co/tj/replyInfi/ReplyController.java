package kr.co.tj.replyInfi;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/replyInfi")
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	
	

	@GetMapping("/{qnaid}") // 게시물 id
	public ResponseEntity<?> findByQnaid(@PathVariable("qnaid") Long qnaid) {
		Map<String, Object> map = new HashMap<>();

		try {
			List<ReplyDTO> list = replyService.findByQnaid(qnaid);
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}

	
	// 부모 댓글 id
	@GetMapping("/replyid/{replyid}")
	public ResponseEntity<?> findByReplyid(@PathVariable("replyid") Long replyid) {

		Map<String, Object> map = new HashMap<>();

		if (replyid == null) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			ReplyDTO dto = replyService.findByReplyid(replyid);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "댓글 가져오기 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	
	/*
	 * // 모든 댓글 가져오기 (대댓글 포함)
	 * 
	 * @GetMapping("/all") public ResponseEntity<?> findAll() { Map<String, Object>
	 * map = new HashMap<>();
	 * 
	 * try { List<ReplyEntity> entities = replyService.findAll(); for (ReplyEntity
	 * entity : entities) { List<ReplyEntity> replies =
	 * replyService.getRepliesByParentReplyId(entity.getReplyid());
	 * entity.setReplies(replies); } map.put("result", entities); return
	 * ResponseEntity.ok().body(map); } catch (Exception e) { e.printStackTrace();
	 * map.put("result", "댓글 목록 가져오기 실패" + e.getMessage()); return
	 * ResponseEntity.badRequest().body(map); } }
	 */
	
	
	
	/*
	 * // 댓글 목록 가져오기 (대댓글이 없는 경우)
	 * 
	 * @GetMapping("/replyAll") public ResponseEntity<?> findAllParent() {
	 * Map<String, Object> map = new HashMap<>();
	 * 
	 * try { List<ReplyEntity> entity = replyService.findAllParent();
	 * map.put("result", entity); return ResponseEntity.ok().body(map); } catch
	 * (Exception e) { e.printStackTrace(); map.put("result", "댓글 목록 가져오기 실패" +
	 * e.getMessage()); return ResponseEntity.badRequest().body(map); } }
	 * 
	 * 
	 * // 대댓글 목록 가져오기 (부모댓글에 대댓글이 달린경우)
	 * 
	 * @GetMapping("/repliesAll") public ResponseEntity<?>
	 * findAllChildren(@RequestParam("parentReplyid") Long parentReplyid) {
	 * Map<String, Object> map = new HashMap<>();
	 * 
	 * try{ List<ReplyEntity> entity = replyService.findAllChildren(parentReplyid);
	 * map.put("result", entity); return ResponseEntity.ok().body(map);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); map.put("result",
	 * "대댓글 목록 가져오기 실패" + e.getMessage()); return
	 * ResponseEntity.badRequest().body(map); } }
	 */


	// 댓글 전체 가져오기
	 @GetMapping("/all")
	    public ResponseEntity<?> getAllReplies() {
	        Map<String, Object> map = new HashMap<>();

	        try {
	            List<ReplyDTO> replies = replyService.getAllReplies();
	            map.put("result", replies);
	            return ResponseEntity.ok().body(map);
	        } catch (Exception e) {
	            e.printStackTrace();
	            map.put("result", "댓글 목록 가져오기 실패: " + e.getMessage());
	            return ResponseEntity.badRequest().body(map);
	        }
	    }
	
	
	
	
	


	
	

	
	@PostMapping("")   // 기존 댓글 입력 구현
	public ResponseEntity<?> insert(@RequestBody ReplyDTO dto) {
	    Map<String, Object> map = new HashMap<>();
	    if (dto == null) {
	        map.put("result", "잘못된 정보입니다.");
	        return ResponseEntity.badRequest().body(map);
	    }
	    try {
	        dto = replyService.save(dto);
	        // DB에서 생성된 ID 값을 다시 할당
	        dto.setReplyid(dto.getReplyid());
	        map.put("result", dto);
	        return ResponseEntity.ok().body(map);
	    } catch (Exception e) {
	        e.printStackTrace();
	        map.put("result", "댓글 작성 실패");
	        return ResponseEntity.badRequest().body(map);
	    }
	}

	@PostMapping("/{parentReplyid}")  // 대댓글 입력 구현
	public ResponseEntity<?> insert2(@RequestBody ReplyDTO dto, @PathVariable("parentReplyid") Long replyid) {
	    Map<String, Object> map = new HashMap<>();
	    if (dto == null) {
	        map.put("result", "잘못된 정보입니다.");
	        return ResponseEntity.badRequest().body(map);
	    }
	    try {
	        ReplyDTO parent = replyService.findByReplyid(replyid);
	        if (parent == null) {
	            map.put("result", "잘못된 상위 댓글 정보입니다.");
	            return ResponseEntity.badRequest().body(map);
	        }
	        dto.setQnaid(parent.getQnaid());
	        dto.setParentReplyid(replyid); // 수정된 부분
	        dto = replyService.save(dto);
	        // DB에서 생성된 ID 값을 다시 할당
	        // dto.setReplyid(dto.getReplyid());
	        map.put("result", dto);
	        return ResponseEntity.ok().body(map);
	    } catch (Exception e) {
	        e.printStackTrace();
	        map.put("result", "댓글 작성 실패");
	        return ResponseEntity.badRequest().body(map);
	    }
	}



	
	
	
	
	
	
	// 수정
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody ReplyDTO dto) {

	    Map<String, Object> map = new HashMap<>();

	    try {
	        dto = replyService.update(dto);
	        map.put("result", dto);
	        return ResponseEntity.ok().body(map);

	    } catch (Exception e) {
	        e.printStackTrace();
	        map.put("result", e.getMessage());
	        return ResponseEntity.badRequest().body(map);
	    }
	}

	
	// 삭제
	@DeleteMapping("/{replyid}")
	public ResponseEntity<?> delete(@PathVariable Long replyid) {
	    Map<String, Object> map = new HashMap<>();

	    try {
	        replyService.delete(replyid);
	        map.put("result", "댓글 삭제 성공");
	        return ResponseEntity.ok().body(map);
	    } catch (Exception e) {
	        e.printStackTrace();
	        map.put("result", "댓글 삭제 실패");
	        return ResponseEntity.badRequest().body(map);
	    }
	}

}