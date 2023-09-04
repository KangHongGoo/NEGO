package kr.co.tj.reply;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.tj.DataNotFoundException;

@Controller
@RequestMapping("/reply")

public class ReplyController {
	@Autowired
	private ReplyService replyService;

	@PreAuthorize("isAuthenticated()")
	@DeleteMapping({ "", "/" })
	public ResponseEntity<?> delete2(@RequestBody ReplyDTO dto_user, Principal principal) {
		ReplyDTO dto = replyService.findById(dto_user.getId());

		if (dto == null || !principal.getName().equals(dto.getNickName())) {
			throw new DataNotFoundException("삭제 권한 없음");
		}
		Map<String, String> map = new HashMap<>();

		try {
			replyService.delete(dto.getId());
			map.put("result", "삭제 성공");
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {

			e.printStackTrace();
			map.put("result", "삭제 실패");
			return ResponseEntity.badRequest().body(map);
		}

	}

	
	  @PreAuthorize("isAuthenticated()")
	  
	  @PostMapping({"","/"}) 
	  public ResponseEntity<?> write2(@RequestBody ReplyDTO dto ,Principal principal) {
	  
	
		  
	  Map<String, String> map = new HashMap<>();
	  
	  try {
		dto.setCreateDate(LocalDateTime.now());
		dto.setUpdateDate(LocalDateTime.now());
		replyService.save(dto);
		map.put("result", "성공");
		return ResponseEntity.ok().body(map);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		map.put("result", "실패");
		return ResponseEntity.badRequest().body(map);
	}
	  
	  
	  
	  
	  
}
	 

/*
 * @PreAuthorize("isAuthenticated()")
 * 
 * @PostMapping("/write")
 *  public String write(ReplyDTO dto, Integer bid, String nickName, Principal principal) {
 * dto.setBid(bid); dto.setNickName(nickName);
 * dto.setCreateDate(LocalDateTime.now());
 * dto.setUpdateDate(LocalDateTime.now()); dto = replyService.save(dto);
 * 
 * return "redirect:/board/read/" + dto.getBid();
 * 
 * }
 */

	@PreAuthorize("isAuthenticated()")
	@PutMapping("/update")
	public ResponseEntity<?> update2(@RequestBody ReplyDTO dto, Principal principal) {

		ReplyDTO dto_db = replyService.findById(dto.getId());
		dto_db.setContent(dto.getContent());

		if (dto == null || !principal.getName().equals(dto_db.getNickName())) {
			throw new DataNotFoundException("해당 자료가 없거나 수정 권한이 없습니다.");
		}
		Map<String, String> map = new HashMap<>();
		try {
			dto = replyService.update(dto_db);
			map.put("result", "수정성공");
			return ResponseEntity.ok().body(map);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("reslut", "수정 실패");
			return ResponseEntity.badRequest().body(map);
		}

	}

	@GetMapping("/all/{bid}")
	@ResponseBody
	public Map<String, List<ReplyDTO>> getReplisByBid(@PathVariable("bid") Integer bid) {

		List<ReplyDTO> list = replyService.findByBid(bid);

		Map<String, List<ReplyDTO>> map = new HashMap<>();
		map.put("arr", list);

		return map;
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update")
	public String update(ReplyDTO dto, Principal principal) {

		ReplyDTO dto_db = replyService.findById(dto.getId());
		dto_db.setContent(dto.getContent());

		if (dto == null || !principal.getName().equals(dto_db.getNickName())) {
			return "redirect:/";
		}
		dto = replyService.update(dto_db);

		return "redirect:/board/read/" + dto.getBid();
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/delete")
	public String delete(Integer id, Principal principal) {
		ReplyDTO dto = replyService.findById(id);

		if (dto == null || !principal.getName().equals(dto.getNickName())) {
			return "redirect:/";
		}
		replyService.delete(id);

		return "redirect:/board/read/" + dto.getBid();
	}

}
