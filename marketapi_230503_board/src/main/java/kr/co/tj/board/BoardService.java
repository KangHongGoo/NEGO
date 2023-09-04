package kr.co.tj.board;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.co.tj.board.category.CategoryService;



@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	

	

	
	 
	@Transactional
	public Page<BoardDTO> search(int pageNum, String keyword) {
		List<Sort.Order> sortList = new ArrayList<>();
		sortList.add(Sort.Order.desc("id"));
		
		Pageable pageable = PageRequest.of(pageNum, 10, Sort.by(sortList));
				
		Page<BoardEntity> page_board = boardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
		 
		 Page<BoardDTO> page_dto = page_board.map(
				 
				 boardEntity ->
				 new BoardDTO(
						 boardEntity.getId(),
						 boardEntity.getCid(),
						 boardEntity.getUsername(),
						 boardEntity.getTitle(),
						 boardEntity.getContent(),
						 boardEntity.getFileId(),
						 boardEntity.getCreateDate(),
						 boardEntity.getUpdateDate(),
						 boardEntity.getReadCnt()
						
						  )
				 );
		 return page_dto;
	}
	
	@Transactional
	public BoardDTO insert(BoardDTO dto) {
		
		BoardEntity entity = new ModelMapper().map(dto, BoardEntity.class);
		
		
		entity.setCreateDate(new Date());
		boardRepository.save(entity);
		
		
		return new ModelMapper().map(entity, BoardDTO.class);
	}
	@Transactional
	public BoardDTO findById(Long id) {
		
		Optional<BoardEntity> optional = boardRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("X");
		}
		BoardEntity entity = optional.get();
		
		
		return new ModelMapper().map(entity, BoardDTO.class);
	}
	@Transactional
	public List<BoardDTO> findAll() {
		
		List<BoardEntity> list_entity = boardRepository.findAll();
		List<BoardDTO> list_dto = new ArrayList<>();
		
		list_entity.forEach(e -> {
			list_dto.add(new ModelMapper().map(e, BoardDTO.class));
		});
		
		
		return list_dto;
	}
	@Transactional
	public BoardDTO update(BoardDTO dto) {
		
		Optional<BoardEntity> optional = boardRepository.findById(dto.getId());
		
		if(!optional.isPresent()) {
			throw new RuntimeException("정보를 꼭 입력해주세요");
		}
		BoardEntity entity = optional.get();
		
		entity.setContent(dto.getContent());
		entity.setTitle(dto.getTitle());
		entity.setCid(dto.getCid());
		entity.setUpdateDate(new Date());
		
		entity = boardRepository.save(entity);
		
		dto = new ModelMapper().map(entity, BoardDTO.class);		
		return dto;
	}
	@Transactional
	public void delete(BoardDTO dto) {
		Optional<BoardEntity> optional = boardRepository.findById(dto.getId());
		
		
			BoardEntity entity = optional.get();
			boardRepository.delete(entity);
		
		
		
		
		
	}
	@Transactional
	public BoardDTO readCntUpdate(BoardDTO dto) {
		Optional<BoardEntity> optional = boardRepository.findById(dto.getId());
		
		if(!optional.isPresent()) {
			throw new RuntimeException("해당 데이터는 없음");
		}
		
		BoardEntity orgEntity = optional.get();
		long readCnt = orgEntity.getReadCnt() +1 ;
		
		BoardEntity entity = new BoardEntity.BoardEntityBuilder()
				.title(orgEntity.getTitle())
				.id(orgEntity.getId())
				.content(orgEntity.getContent())
				.username(orgEntity.getUsername())
				.readCnt(readCnt)
				.cid(orgEntity.getCid())
				.createDate(orgEntity.getCreateDate())
				.updateDate(orgEntity.getUpdateDate())
				.build();
		
		entity = boardRepository.save(entity);
		
		
		return new ModelMapper().map(entity, BoardDTO.class);
	}
	@Transactional
	public Page<BoardDTO> findAll(int page) {
		List<Sort.Order> sortList = new ArrayList<>();
		sortList.add(Sort.Order.desc("id"));
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sortList));
		
		Page<BoardEntity> page_board = boardRepository.findAll(pageable);
		
		Page<BoardDTO> page_dto = page_board.map(
				boardEntity ->
				new BoardDTO(boardEntity.getId(),
						boardEntity.getCid(),
						boardEntity.getUsername(),
						boardEntity.getTitle(),
						boardEntity.getContent(),
						boardEntity.getFileId(),
						boardEntity.getCreateDate(),
						boardEntity.getUpdateDate(),
						boardEntity.getReadCnt()
						
						
						)
				
				
				
				);
				
		return page_dto;
	}
	@Transactional
	public void deleteBoard(Long id) {
		
		try {
			BoardEntity entity = boardRepository.findById(id).get();
			boardRepository.delete(entity);
		} catch (Exception e) {
			throw new RuntimeException("해당 게시물 삭제중 오류가 발생했습니다.");
		}
		
		
		
	}
	@Transactional
	public BoardDTO findByCid(Long cid) {
		
		Optional<BoardEntity> optional = boardRepository.findByCid(cid);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("x");
		}
		BoardEntity entity = optional.get();
		
		
		return new ModelMapper().map(entity, BoardDTO.class);
		
	} 

}
