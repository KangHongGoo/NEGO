package kr.co.tj.boardservice.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import kr.co.tj.boardservice.dto.BoardDTO;
import kr.co.tj.boardservice.dto.BoardRequest;
import kr.co.tj.boardservice.dto.BoardResponse;
import kr.co.tj.boardservice.dto.CategoryResponse;
import kr.co.tj.boardservice.feign.CategoryFeign;
import kr.co.tj.boardservice.service.BoardService;
import kr.co.tj.boardservice.service.BoardServiceImpl;

@RestController
@RequestMapping("/board-service")
public class BoardController {

	
	private BoardService boardService;
	private CategoryFeign categoryFeign;
	
	@Autowired
	public BoardController(BoardService boardService, CategoryFeign categoryFeign) {
		super();
		this.boardService = boardService;
		this.categoryFeign = categoryFeign;
	}


	
	
	
	// 다중 삭제 추가
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
	
	
	// 검색기능
	@GetMapping("/board/search")
	public ResponseEntity<?> search(@RequestParam("pageNum") int pageNum, @RequestParam("keyword") String keyword) {
		
		
		
		try {
			Page<BoardDTO> page = boardService.search(pageNum, keyword);
			
			return ResponseEntity.status(HttpStatus.OK).body(page);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body(" 오류");
		}
	}
	// 페이징
	@GetMapping("/page/{page}")
	public ResponseEntity<?> getpage(@PathVariable("page") Integer page) {
		
		
		page = page -1 ;
		Pageable pageable = PageRequest.of(page, 10);
		List<BoardResponse> list = boardService.getPage(pageable);
			
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
	
	// 수정
	 @PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody BoardResponse boardResponse) {
		 
		 if(boardResponse == null) {
			 return ResponseEntity.status(HttpStatus.OK).body("값을 입력해주세요");
		 }
		 
		BoardDTO boardDTO = boardService.update(boardResponse);
		
		boardResponse = boardDTO.toBoardResponse();
		
		// 마찬가지로 수정 후에 수정한 카테고리 이름 들어가게끔 하는 코드
		CategoryResponse name = categoryFeign.getNameByCid(boardDTO.getCid());
		
		
		boardDTO.setCateName(name.getName());
		
		 return ResponseEntity.status(HttpStatus.CREATED).body(boardResponse);
		
	} 
	// 삭제
	@DeleteMapping("")
	public ResponseEntity<?> delete(@RequestBody BoardRequest request) {
		
		if(request.getId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("다시 확인해주세요");
		}
		
		try {
			boardService.delete(request.getId());
			
			return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패");
		}
		
		
	}
	// 전체목록 가져오기
	@GetMapping("/board/all")
	public ResponseEntity<?> findAll() {
		
		List<BoardDTO> list = boardService.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	
	
	// 자세히보기
	@GetMapping("/board/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		
		if(id == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 정보는 없습니다");
		}
		BoardDTO dto = boardService.findById(id);
		
		// 조회수 올라가는 코드
		dto = boardService.readCntUpdate(dto);
		
		// 카테고리 이름 추출
		CategoryResponse name = categoryFeign.getNameByCid(dto.getCid());
		
		dto.setCateName(name.getName());
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	} 
	
	// 게시글 작성
	 @PostMapping("/boards") 
	public ResponseEntity<?> createBoard(@RequestBody BoardRequest boardRequest) {
		
		 if(boardRequest.getTitle() == null || boardRequest.getTitle().equals("")) {
			 return ResponseEntity.status(HttpStatus.OK).body("제목을 입력해주세요"); 
		 }
		 
		 if(boardRequest.getContent() == null || boardRequest.getContent().equals("")) {
			 return ResponseEntity.status(HttpStatus.OK).body("내용을 입력해주세요"); 
		 }
	  
	 BoardDTO boardDTO = BoardDTO.toBoardDTO(boardRequest);
	  
	 boardDTO = boardService.createBoard(boardDTO);
	  
	 BoardResponse boardResponse = boardDTO.toBoardResponse();
	  
	 return ResponseEntity.status(HttpStatus.CREATED).body(boardResponse); 
	 
	 }
	 
	 
	 //  반복문
	 @PostMapping("/testinsert")
	 public void writeArticles() {
		  for (int i = 1; i < 200; i++) {
			  BoardRequest boardRequest = new BoardRequest();
			  boardRequest.setUsername("m001");
		    boardRequest.setTitle("제목 " + i);   // 글의 제목
		    boardRequest.setContent("내용 " + i);  // 글의 내용
		    
		    BoardDTO boardDTO = BoardDTO.toBoardDTO(boardRequest);
		    
		    boardDTO = boardService.createBoard(boardDTO);
		    
		   
		  }
		}
	 
	
	
}
