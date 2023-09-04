package kr.co.tj.board.category;

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
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/{id}")
	   public ResponseEntity<?> FindById(@PathVariable("id") Long id){
	      Map<String, Object> map = new HashMap<>();
	      
	      try {
	         CategoryDTO dto = categoryService.FindByName(id);
	         map.put("result", dto);
	         return ResponseEntity.ok().body(map);
	      } catch (Exception e) {
	         e.printStackTrace();
	         map.put("result", e.getMessage());
	         return ResponseEntity.badRequest().body(map);
	      }
	   }


	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			CategoryDTO dto = categoryService.getById(id);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	// 말머리 모아보기
	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		
		Map<String, Object> map =new  HashMap<>();
		
		try {
			List<CategoryDTO> list = categoryService.getAll();
			System.out.println(list);
			
			
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}
	// 말머리 지우기
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody CategoryDTO dto) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			categoryService.delete(dto);
			map.put("result", "삭제 완료");
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(map);
		}
	}
	// 말머리 수정
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody CategoryDTO dto) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			dto = categoryService.update(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}
	// 말머리 작성
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody CategoryDTO dto) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			dto = categoryService.insert(dto);
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
