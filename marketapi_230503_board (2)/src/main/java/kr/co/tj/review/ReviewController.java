package kr.co.tj.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("api/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/all") // 리뷰 전체 출력
	public ResponseEntity<?> findAll() {
		Map<String, Object> map = new HashMap<>();

		try {
			List<ReviewDTO> list = reviewService.findAll();
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "리뷰 목록을 가져올 수 없습니다.");
			return ResponseEntity.badRequest().body(map);
		}
	}

	@GetMapping("/id/{id}") // id에 해당하는 리뷰 츨력
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		Map<String, Object> map = new HashMap<>();
		if (id == null) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}
		try {
			ReviewDTO dto = reviewService.findById(id);
			
			/*
			 * // 조회수 구현 추가 dto = reviewService.countUpdate(dto);
			 */
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "리뷰를 가져올 수 없습니다.");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@GetMapping("/search/title") // title에서 검색
	public ResponseEntity<?> searchTitle(@RequestParam(name="keyword") String keyword){
		Map<String, Object> map=new HashMap<>();
		
		if(keyword==null) {
			map.put("result","검색어가 없습니다.");
			return ResponseEntity.badRequest().body(map);
		}
		
		try {
			List<ReviewDTO> list = reviewService.searchTitles(keyword);
			map.put("result", list);
			return ResponseEntity.ok().body(map);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "검색 결과를 찾을 수 없습니다.");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@GetMapping("/search/content") // content에서 검색
	public ResponseEntity<?> searchContent(@RequestParam(name="keyword") String keyword){
		Map<String, Object> map=new HashMap<>();
		
		if(keyword==null) {
			map.put("result","검색어가 없습니다.");
			return ResponseEntity.badRequest().body(map);
		}
		
		try {
			List<ReviewDTO> list = reviewService.searchContents(keyword);
			map.put("result", list);
			return ResponseEntity.ok().body(map);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "검색 결과를 찾을 수 없습니다.");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@GetMapping("/search/titleOrContent")
	public ResponseEntity<?> searchTitleOrContent(HttpServletRequest request) {
	    Map<String, Object> map = new HashMap<>();
	    String keyword = request.getParameter("keyword");

	    if (keyword == null || keyword.isEmpty()) {
	        map.put("result", "검색어가 없습니다.");
	        return ResponseEntity.badRequest().body(map);
	    }

	    try {
	        List<ReviewDTO> list = reviewService.searchTitlesOrContents(keyword);
	        map.put("result", list);
	        return ResponseEntity.ok().body(map);

	    } catch (Exception e) {
	        e.printStackTrace();
	        map.put("result", "검색 결과를 찾을 수 없습니다.");
	        return ResponseEntity.badRequest().body(map);
	    }
	}
	
	@PostMapping("") // 리뷰 입력
	public ResponseEntity<?> insert(@RequestBody ReviewDTO dto) {
		Map<String, Object> map = new HashMap<>();

		if (dto == null) {
			map.put("result", "리뷰가 작성되지 않았습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getUsername() == null || dto.getUsername().equals("")) {
			map.put("result", "유저명이 작성되지 않았습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getTitle() == null || dto.getTitle().equals("")) {
			map.put("result", "제목이 존재하지 않습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getContent() == null || dto.getTitle().equals("")) {
			map.put("result", "내용이 존재하지 않습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getRate() > 5.0) {
			map.put("result", "별점이 표현가능한 범위를 벗어났습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			dto = reviewService.insert(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@PutMapping("") // 리뷰 수정
	public ResponseEntity<?> update(@RequestBody ReviewDTO dto) {
		Map<String, Object> map = new HashMap<>();

		if (dto == null) {
			map.put("result", "리뷰가 작성되지 않았습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getUsername() == null || dto.getUsername().equals("")) {
			map.put("result", "유저명이 작성되지 않았습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getTitle() == null || dto.getTitle().equals("")) {
			map.put("result", "제목이 존재하지 않습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getContent() == null || dto.getTitle().equals("")) {
			map.put("result", "내용이 존재하지 않습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getRate() > 5.0 && dto.getRate()<0) {
			map.put("result", "별점이 표현가능한 범위를 벗어났습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			dto = reviewService.update(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "리뷰를 수정하는데 실패했습니다.");
			return ResponseEntity.badRequest().body(map);
		}
	}

	
	@DeleteMapping("") // 리뷰 삭제
	public ResponseEntity<?> delete(@RequestBody ReviewDTO dto) {
		Map<String, Object> map = new HashMap<>();

		if (dto == null) {
			map.put("result", "삭제할 정보가 없습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		if (dto.getId() == null) {
			map.put("result", "삭제할 정보가 없습니다.");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			reviewService.delete(dto.getId());
			map.put("result", "리뷰를 삭제했습니다.");
			return ResponseEntity.ok().body(map);
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "리뷰를 삭제하는데 실패했습니다.");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	@GetMapping("/page/{page}") // 페이징 구현
	public ResponseEntity<?> getPage(@PathVariable("page") Integer page) {
		Map<String, Object> map = new HashMap<>();
		page = page - 1;
	    Pageable pageable = PageRequest.of(page, 10);
	    List<ReviewDTO> list= reviewService.getPage(pageable);
	    map.put("result", list);
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping("/search/page/{page}") // 페이징 구현
	public ResponseEntity<?> getSearchPage(@PathVariable("page") Integer page, HttpServletRequest request) {
	    String keyword = request.getParameter("keyword");
	    Map<String, Object> map = new HashMap<>();
	    page = page - 1;
	    Pageable pageable = PageRequest.of(page, 10);
	    List<ReviewDTO> list= reviewService.getSearchPage(pageable, keyword);
	    map.put("result", list);
	    return ResponseEntity.ok().body(map);
	}	
}
