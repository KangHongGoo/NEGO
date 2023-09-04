package kr.co.tj.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin // 다른 포트에서의 요청을 허용 할 수 있는 어노테이션
@RequestMapping("/api/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/all")
	public  ResponseEntity<?> findByAll() {
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<BoardDTO> list = boardService.findAll();
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
		
	}
	
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			boardService.delete(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", e.getMessage());
			return ResponseEntity.badRequest().body(map); 
		}
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody BoardDTO dto ){
		Map<String, Object> map = new HashMap<>();
		
		try {
			dto = boardService.update(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id){
		Map<String, Object> map = new HashMap<>();
		
		try {
			BoardDTO dto = boardService.findBoardId(id);
			dto = boardService.readCntUpdate(dto);
			
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
		
	}
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();
		
		if(dto == null) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}
		
		if(dto == null || dto.getTitle().equals("")) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}
		
		if(dto == null || dto.getContent().equals("")) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}
		
		try {
			dto = boardService.insert(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
		
	}

}
