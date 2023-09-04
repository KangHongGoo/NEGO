package kr.co.tj.board;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

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
import kr.co.tj.board.file.FileSerivce;

@RestController
@RequestMapping("/api/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private FileSerivce fileService;
	
	
	
	//카테고리아이디 추가
	@GetMapping("/{cid}")
	public ResponseEntity<?> findByCid(@PathVariable("cid") Long cid) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<BoardDTO> list = boardService.findByCid(cid);
			map.put("result", list);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}
	// 다중 삭제 추가
	@PostMapping("/delete")
	public String delete(@RequestBody List<Long> boardIds) {
		
		for(int i=0; i<boardIds.size(); i++) {
			Long id = Long.valueOf(boardIds.get(i));
			boardService.deleteBoard(id);
		}
		return "result";
	}
	// 검색 기능 추가
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam("pageNum") int pageNum, @RequestParam("keyword") String keyword) {
		Map<String, Object> map = new HashMap<>();

		try {
			Page<BoardDTO> page = boardService.search(pageNum, keyword);
			map.put("result", page);

			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}

	}

	// 페이징
	@GetMapping("/list")
	public ResponseEntity<?> list(int pageNum) {
		Map<String, Object> map = new HashMap<>();

		try {
			Page<BoardDTO> page = boardService.findAll(pageNum);
			map.put("result", page);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("result", "목록을 가져오는데 실패했습니다.");
			return ResponseEntity.badRequest().body(map);
		}
	}

	// 삭제
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();

		try {
			boardService.delete(dto);
			map.put("result", "삭제 성공");
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("result", "삭제 실패");
			return ResponseEntity.badRequest().body(map);
		}
	}

	// 수정
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();

		if (dto == null) {
			return ResponseEntity.badRequest().body("수정 실패");
		}

		if (dto.getTitle() == null) {
			return ResponseEntity.badRequest().body("제목을 입력해주세요");
		}

		if (dto.getContent() == null) {
			return ResponseEntity.badRequest().body("내용을 입력해주세요");
		}

		try {
			dto = boardService.update(dto);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			
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
			BoardDTO dto = boardService.getById(id);

			// 조회수를 위한 코드
			dto = boardService.readCntUpdate(dto);

			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}

	// 사진 업로드
	@PostMapping("/post")
	@ResponseBody
	public Map<String, Object> post(@RequestParam("file") MultipartFile files, BoardDTO dto) {
		
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
			boardService.insert(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 파일 경로를 반환하기 위한 코드
		Map<String, Object> map = new HashMap<>();
		String origFilename = files.getOriginalFilename();
		map.put("filePath", "http://localhost:9007/files/"+ origFilename);
		return map;
	}

	// 글 작성하기
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody BoardDTO dto) {

		Map<String, Object> map = new HashMap<>();

		if (dto == null) {
			return ResponseEntity.badRequest().body("제목 또는 내용을 입력해주세요");
		}

		try {
			
			dto = boardService.insert(dto);
			
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {

			e.printStackTrace();
			map.put("result", e.getMessage());
			return ResponseEntity.badRequest().body(map);
		}
	}

}
