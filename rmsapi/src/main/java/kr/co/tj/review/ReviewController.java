package kr.co.tj.review;

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
@RequestMapping("/api/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody ReviewDTO dto) {
		Map<String, Object> map = new HashMap<>();

		if(dto==null || 
				dto.getUsername()==null|| dto.getUsername().equals("")||
				dto.getRestaurantName()==null|| dto.getRestaurantName().equals("")||
				dto.getRate()==0f||
				dto.getRecommend()==null|| dto.getRecommend().equals("")){
			map.put("result","잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			dto = reviewService.update(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "리뷰 수정 실패");
			return ResponseEntity.badRequest().body(map);
		}
		
	}

	
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody ReviewDTO dto) {
		Map<String, Object> map = new HashMap<>();

		if (dto == null) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getId() == null) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			reviewService.delete(dto.getId());
			map.put("result", "삭제 성공");
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "리뷰 삭제 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}

	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody ReviewDTO dto){
		Map<String, Object> map=new HashMap<>();
		
		if(dto==null || 
				dto.getUsername()==null|| dto.getUsername().equals("")||
				dto.getRestaurantName()==null|| dto.getRestaurantName().equals("")||
				dto.getRate()==0f||
				dto.getRecommend()==null|| dto.getRecommend().equals("")){
			map.put("result","잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}
		
		try {
			
			dto=reviewService.insert(dto);
			map.put("result",dto);
			return ResponseEntity.ok().body(map);
	
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result","리뷰 입력 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> findList(){
		Map<String, Object> map = new HashMap<>();
		try {
			List<ReviewDTO> list=reviewService.findAll();
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "리뷰 목록 가져오기 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}

	@GetMapping("/pagenum/{pagenum}")
	public ResponseEntity<?> findList(@PathVariable("pagenum") Integer pagenum){
		Map<String, Object> map = new HashMap<>();
		try {
			List<ReviewDTO> list=reviewService.findPage(pagenum);
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "리뷰 목록 가져오기 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@GetMapping("id/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id){
		Map<String, Object> map = new HashMap<>();

		if (id == null) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			ReviewDTO dto = reviewService.findById(id);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "리뷰 가져오기 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
}
