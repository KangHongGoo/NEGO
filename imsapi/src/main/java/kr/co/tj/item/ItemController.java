package kr.co.tj.item;

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

@RestController//@Controller + @ResponseBody
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody ItemDTO itemDTO) {
		if(itemDTO == null) {
			return ResponseEntity.badRequest().body("삭제할 데이터가 없습니다.");
		}
		Map<String, Object> map = new HashMap<>();
		
		try {
			itemService.delete(itemDTO);
			map.put("result", "삭제 성공");
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", "삭제 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody ItemDTO itemDTO){
		if(itemDTO == null) {
			return ResponseEntity.badRequest().body("입력된 데이터가 없습니다.");
		}
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			ItemDTO dto = itemService.update(itemDTO);
			map.put("result", dto);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("err", "수정 실패");
			return ResponseEntity.badRequest().body(map);
		}
		
	}

	
	@PostMapping("")
	public ResponseEntity<?> save(@RequestBody ItemDTO itemDTO){
		if(itemDTO == null) {
			return ResponseEntity.badRequest().body("입력된 데이터가 없습니다.");
		}
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			ItemDTO dto = itemService.save(itemDTO);
			map.put("result", dto);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", "입력 실패했습니다.");
			return ResponseEntity.badRequest().body(map);
		}
		
	}

	
	@GetMapping("/staff/{staff}")
	public ResponseEntity<?> findByStaff(@PathVariable("staff") String staff){
		Map<String, Object> map = new HashMap<>();
		
		if(staff==null || staff.equals("")) {
			return ResponseEntity.badRequest().body("staff 정보가 누락되었음.");
		}
		
		try {
			List<ItemDTO> list = itemService.findByStaff(staff);
			map.put("result", list);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	@GetMapping("/all")
	public ResponseEntity<?> findAll(){
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<ItemDTO> list = itemService.findAll();
			map.put("result", list);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("err", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") long id){
		Map<String, Object> map = new HashMap<>();
		
		try {
			ItemDTO dto = itemService.findById(id);
			map.put("result", dto);
			
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("err", e.getMessage()); 

			return ResponseEntity.badRequest().body(map);
		}
		
	}

}
