package kr.co.tj.qna;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import kr.co.tj.board.file.FileDTO;
import kr.co.tj.board.file.FileService;





@RestController
@RequestMapping("/api/qna")
public class QnAController {
	
	@Autowired
	private QnAService qnaService;


	@Autowired
	private FileService fileService;
	
	
	
	// 게시물 불러오기
	@GetMapping("/id/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id){
		
		Map<String, Object> map = new HashMap<>();
		
		if (id == null) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}

		try {
			QnADTO dto = qnaService.findById(id);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "해당 게시물 가져오기 실패");
			return ResponseEntity.badRequest().body(map);
		}
		
		
	}
	
	
	// 게시물 목록 
	@GetMapping("")
	public ResponseEntity<?> findList() {
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<QnADTO> list=qnaService.findAll();
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "게시물 목록 가져오기 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}
	
	
	// 입력
	@PostMapping("")						
	public ResponseEntity<?> insert(@RequestBody QnADTO dto) {
										
		Map<String, Object> map = new HashMap<>();
			
		
		/* 아래와 같습니다.
		 * if (dto == null || dto.getUsername() == null || dto.getUsername().isEmpty()
		 * || dto.getTitle() == null || dto.getTitle().isEmpty() || dto.getContent() ==
		 * null || dto.getContent().isEmpty()) { map.put("result", "잘못된 정보입니다."); return
		 * ResponseEntity.badRequest().body(map); }
		 */


			if (dto == null) {
				map.put("result", "잘못된 정보입니다.");
				return ResponseEntity.badRequest().body(map);
			}

			if (dto.getUsername() == null || dto.getUsername().equals("")) {
				map.put("result", "잘못된 정보입니다.");
				return ResponseEntity.badRequest().body(map);
			}

			if (dto.getTitle() == null || dto.getTitle().equals("")) {
				map.put("result", "잘못된 정보입니다.");
				return ResponseEntity.badRequest().body(map);
			}

			if (dto.getContent() == null || dto.getContent().equals("")) {
				map.put("result", "잘못된 정보입니다.");
				return ResponseEntity.badRequest().body(map);
			}

			
			try {
				dto=qnaService.insert(dto);
				map.put("result",dto);
				return ResponseEntity.ok().body(map);
		
			} catch (Exception e) {
				e.printStackTrace();
				map.put("result","게시물 입력 실패");
				return ResponseEntity.badRequest().body(map);
			}
			
		}
	
	
	
	
		// 삭제
		@DeleteMapping("")
		public ResponseEntity<?> delete(@RequestBody QnADTO dto) {
		    Map<String, Object> map = new HashMap<>();
	
		    if (dto == null || dto.getId() == null) {
		        map.put("result", "잘못된 정보입니다.");
		        return ResponseEntity.badRequest().body(map);
		    }
	
			try {
				qnaService.delete(dto.getId());
				map.put("result", "삭제 성공");
				return ResponseEntity.ok().body(map);
				
			} catch (Exception e) {
				e.printStackTrace();
				map.put("result", "게시물 삭제 실패");
				return ResponseEntity.badRequest().body(map);
			}
		}
	
	
	
		
		// 수정
		@PutMapping("")   					
		public ResponseEntity<?> update(@RequestBody QnADTO dto) {
						
		    Map<String, Object> map = new HashMap<>();
		    
			/* 아래와 같습니다.
			 * if (dto == null || dto.getUsername() == null || dto.getUsername().isEmpty()
			 * || dto.getTitle() == null || dto.getTitle().isEmpty() || dto.getContent() ==
			 * null || dto.getContent().isEmpty()) { map.put("result", "잘못된 정보입니다."); return
			 * ResponseEntity.badRequest().body(map); }
			 */
		    
		    
			if (dto == null) {
				map.put("result", "잘못된 정보입니다.");
				return ResponseEntity.badRequest().body(map);
			}
	
			if (dto.getUsername() == null || dto.getUsername().equals("")) {
				map.put("result", "잘못된 정보입니다.");
				return ResponseEntity.badRequest().body(map);
			}
	
			if (dto.getTitle() == null || dto.getTitle().equals("")) {
				map.put("result", "잘못된 정보입니다.");
				return ResponseEntity.badRequest().body(map);
			}
	
			if (dto.getContent() == null || dto.getContent().equals("")) {
				map.put("result", "잘못된 정보입니다.");
				return ResponseEntity.badRequest().body(map);
			}
		    
		    
		    try {
		        dto = qnaService.update(dto);
		        map.put("result", dto);
		        return ResponseEntity.ok().body(map);
		    } catch (Exception e) {
		        e.printStackTrace();
		        map.put("result", "게시물 수정 실패");
		        return ResponseEntity.badRequest().body(map);
		    }
		}
	
		
		
		// 사진 업로드
		@PostMapping("/post")
		@ResponseBody
		public Map<String, Object> post(@RequestParam("file") MultipartFile files, QnADTO dto) {
			
			String filePath = "";
			
			
			try {

				// 기존 파일 이름을 가져옵니다.
				String origFilename = files.getOriginalFilename();

				// 현재 실행되고있는 경로 속 files폴더가 저장 경로가 됩니다.
				String savePath = System.getProperty("user.dir") + "\\files";

				// 파일이 저장되는 폴더가 없으면 폴더를 생성합니다.
				if (!new File(savePath).exists()) {
					try {
						new File(savePath).mkdir();
					} catch (Exception e) {
						e.getStackTrace();
					}
				}

				// savePath에 저장된 경로에 파일을 만듭니다.
				filePath = savePath + "\\" + origFilename;
							

				// 업로드한 파일을 서버의 파일 시스템에 저장.
				files.transferTo(new File(filePath));

				FileDTO dto_file = new FileDTO();
				dto_file.setOrigFilename(origFilename);
				dto_file.setFilePath(filePath);

				Long fileId = fileService.saveFile(dto_file);
				dto.setFileId(fileId);
				qnaService.insert(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 파일 경로를 반환하기 위한 코드
			Map<String, Object> map = new HashMap<>();
			String origFilename = files.getOriginalFilename();
			map.put("filePath", "http://localhost:9007/files/"+ origFilename);
			return map;
		}
		
		
		
	
		
		
		
		// 페이징
		@GetMapping("/page/{page}")
	      public ResponseEntity<?> getPage(@PathVariable("page") Integer page) {
	         Map<String, Object> map = new HashMap<>();
	         page = page - 1;
	          Pageable pageable = PageRequest.of(page, 10);
	          List<QnADTO> list= qnaService.getPage(pageable);
	          map.put("result", list);
	         return ResponseEntity.ok().body(map);
	      }
		
	

}
