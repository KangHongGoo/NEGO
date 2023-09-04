package kr.co.tj.board;

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
@RequestMapping("/api/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();

		if (dto == null) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getId() == null) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			boardService.delete(dto.getId());
			map.put("result", "삭제 성공");
			return ResponseEntity.ok().body(map);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "삭제 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteBoard(@RequestParam("id") String idList) {
		Map<String, Object> map = new HashMap<>();
		// 쉼표로 구분된 문자열을 배열로 변환
		String[] ids = idList.split(",");

		try {
			// 배열의 각 요소를 숫자로 변환하여 삭제 작업 수행
			for (String idStr : ids) {
				long id = Long.parseLong(idStr);
				boardService.delete(id);
			}

			map.put("result", "삭제 성공");
			return ResponseEntity.ok().body(map);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "삭제 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}

	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();

		if (dto == null) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getUsername() == null) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getId() == null || dto.getId() == 0L) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getTitle() == null || dto.getTitle().equals("")) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getContent() == null || dto.getContent().equals("")) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			dto = boardService.update(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "수정 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		Map<String, Object> map = new HashMap<>();

		try {
			List<BoardDTO> list = boardService.findAll();
			map.put("result", list);
			return ResponseEntity.ok().body(map);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "실패");
			return ResponseEntity.badRequest().body(map);
		}
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		Map<String, Object> map = new HashMap<>();

		if (id == null) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			// 조회수 증가&저장
			boardService.increaseReadCnt(id);

			BoardDTO dto = boardService.findById(id);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "실패");
			return ResponseEntity.badRequest().body(map);
		}
	}

	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();

		if (dto == null) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getUsername() == null) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getTitle() == null || dto.getTitle().equals("")) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getContent() == null || dto.getContent().equals("")) {
			map.put("result", "에러");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			dto = boardService.insert(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "실패");
			return ResponseEntity.badRequest().body(map);
		}

	}
}
