package kr.co.tj.board;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private FileSerivce fileService;
	
	
	// 삭제
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			boardService.delete(dto);
			map.put("result", "삭제 성공");
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", "삭제 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	// 수정
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody BoardDTO dto) {
		Map<String, Object> map =new HashMap<>();
		
		if(dto == null) {
			return ResponseEntity.badRequest().body("수정 실패");
		}
		
		if(dto.getTitle() == null) {
			return ResponseEntity.badRequest().body("제목을 입력해주세요");
		}
		
		if(dto.getContent() == null) {
			return ResponseEntity.badRequest().body("내용을 입력해주세요");
		}
		
		try {
			dto = boardService.update(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	// 리스트 불러오기
	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		Map<String, Object> map = new HashMap<>();
		
		
		try {
			List<BoardDTO> list = boardService.getAll();
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
		
	}
	
	// 글 자세히 보기
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			BoardDTO dto =  boardService.getById(id);
			
			// 조회수 올라가는거
			dto = boardService.readCntUpdate(dto);
			
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	
	//사진 업로드
	 @PostMapping("/post")
	    public String post(@RequestParam("file") MultipartFile files, BoardDTO dto) {
	        try {
	        	
	        	// 기존 파일 이름을 가져옵니다.
	            String origFilename = files.getOriginalFilename();
	            
	            // MD5를 이용해서 파일 이름일 변환합니다.
	            String filename = new MD5Generator(origFilename).toString();
	            
	            // 현재 실행되고있는 경로 속 files폴더가 저장 경로가 됩니다. 
	            String savePath = System.getProperty("user.dir") + "\\files";
	            
	            // 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. 
	            if (!new File(savePath).exists()) {
	                try{
	                    new File(savePath).mkdir();
	                }
	                catch(Exception e){
	                    e.getStackTrace();
	                }
	            }
	            
	            // savePath에 저장된 경로에 파일을 만듭니다.
	            String filePath = savePath + "\\" + filename;
	            
	            // 업로드한 파일을 서버의 파일 시스템에 저장.
	            files.transferTo(new File(filePath));
	            
	            
	            FileDTO dto_file = new FileDTO();
	            dto_file.setOrigFilename(origFilename);
	            dto_file.setFilename(filename);
	            dto_file.setFilePath(filePath);

	            Long fileId = fileService.saveFile(dto_file);
	            dto.setFileId(fileId);
	            boardService.insert(dto);
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	        
	        // return ResponseEntity.ok().body("그림 URL반환");
	        return "추후 수정";
	    }
	 
	 
	 // 글 작성하기
	  @PostMapping("")
	  public ResponseEntity<?> insert(@RequestBody BoardDTO dto) {
		  
		  	Map<String, Object> map = new HashMap<>();
	  
	  if(dto == null) {
		  return ResponseEntity.badRequest().body("제목 또는 내용을 입력해주세요"); }
	
	 try { 
		 dto = boardService.insert(dto);
		 map.put("result", dto); 
	 return ResponseEntity.ok().body(map);
	 } 
	 catch (Exception e) {
		 
	  e.printStackTrace(); 
	  map.put("result", e.getMessage()); return
	  ResponseEntity.badRequest().body(map);
	  }
	 }
	 
	 
	}
	
	
	
	
	
	
	 

