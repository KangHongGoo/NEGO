package kr.co.tj.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("")
	public String list() {
		return "item/item_list";
	}

	@PostMapping({"","/"})
	public ResponseEntity<?> addItem(@RequestBody ItemDTO dto) {
		Map<String, String> map = new HashMap<>();
		
		try {
			
			itemService.addItem(dto);
			map.put("result", "등록성공");
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", "등록실패");
			return ResponseEntity.badRequest().body(map);
		}
		
	
	}
	@PutMapping({"","/"})
	public ResponseEntity<?> updateItem(@RequestBody ItemDTO dto) {
		ItemDTO dto_db = itemService.findById(dto.getId());
		dto_db.setInfoOfItem(dto.getInfoOfItem());
		
		Map<String, String> map = new HashMap<>();
		
		
		
		try {
			dto = itemService.update(dto_db);
			map.put("result", "성공");
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "실패");
			return ResponseEntity.badRequest().body(map);			
		}
		
	}
	
	@DeleteMapping({"","/"})
	public ResponseEntity<?> delete(@RequestBody ItemDTO dto){
		
		Map<String, String> map = new HashMap<>();
		
		try {
			itemService.delete(dto.getId());
			map.put("result", "성공");
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", "실패");
			return ResponseEntity.badRequest().body(map);
		}
	
	}
	
	
	
	@GetMapping("/list")
	@ResponseBody
	public Map<String, List<ItemDTO>> itemList(){
		List<ItemDTO> list = itemService.findAll();
		
		Map<String, List<ItemDTO>> map = new HashMap<>();
		map.put("arr", list);
		return map;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
