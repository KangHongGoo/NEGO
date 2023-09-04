package kr.co.tj.board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	

	public BoardDTO insert(BoardDTO dto) {
		
		Date date = new Date();
		
		BoardEntity entity = new BoardEntity.BoardEntityBuilder()
				.title(dto.getTitle())
				.content(dto.getContent())
				.username(dto.getUsername())
				.createDate(date)
				.updateDate(date)
				.build();
		

		entity = boardRepository.save(entity);
		
		dto.setContent(entity.getContent());
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setUsername(entity.getUsername());
		
		return dto;
	}

	public BoardDTO findBoardId(Long id) {
	    Optional<BoardEntity> optional = boardRepository.findById(id);
	    
	    if(!optional.isPresent()) {
	    	return null;
	    }
	    BoardEntity entity = optional.get();
	    BoardDTO dto = new BoardDTO.BoardDTOBuilder()
	    		.id(entity.getId())
	    		.title(entity.getTitle())
	    		.content(entity.getContent())
	    		.username(entity.getUsername())
	    		.createDate(entity.getCreateDate())
	    		.updateDate(entity.getUpdateDate())
	    		.readCnt(entity.getReadCnt())
	    		.build();
	    		
	   
		return dto;
	}
	

	public void delete(BoardDTO dto) {
		Optional<BoardEntity> optional = boardRepository.findById(dto.getId());
		
		if(optional.isPresent()) {
			BoardEntity entity = optional.get();
			boardRepository.delete(entity);
		}else {
			throw new RuntimeException("X");
		}
			           
		
		
		
	}

	public List<BoardDTO> findAll() {
		List<BoardEntity> list_entity = boardRepository.findAll();
		
		List<BoardDTO> list = new ArrayList<>();
		
		for(BoardEntity x : list_entity) {
			list.add(BoardDTO.toDto(x));
		}
		return list;
	}
	
	@Transactional
	public BoardDTO update(BoardDTO dto) {
		Optional<BoardEntity> optional = boardRepository.findById(dto.getId());
		
		if(!optional.isPresent()) {
			throw new RuntimeException("해당 데이터는 없음");
		}
		
		Date date2 = optional.get().getCreateDate();
		Date date = new Date();
		
		BoardEntity orgEntity = optional.get();
		long readCnt = orgEntity.getReadCnt();
		
		BoardEntity entity = new BoardEntity.BoardEntityBuilder()
				.title(dto.getTitle())
				.id(dto.getId())
				.content(dto.getContent())
				.username(dto.getUsername())
				.updateDate(date)
				.readCnt(readCnt)
				.createDate(orgEntity.getCreateDate())
				.build();
		entity.setCreateDate(date2);

		entity = boardRepository.save(entity);
		
		dto.setContent(entity.getContent());
		dto.setTitle(entity.getTitle());
		dto.setUsername(entity.getUsername());
		
		
		return dto;
	}
	
	@Transactional
	public BoardDTO readCntUpdate(BoardDTO dto) {
		Optional<BoardEntity> optional = boardRepository.findById(dto.getId());
		
		if(!optional.isPresent()) {
			throw new RuntimeException("해당 데이터는 없음");
		}
		BoardEntity orgEntity = optional.get();
		long readCnt = orgEntity.getReadCnt() + 1;
		
		BoardEntity entity = new BoardEntity.BoardEntityBuilder()
				.title(orgEntity.getTitle())
				.id(orgEntity.getId())
				.content(orgEntity.getContent())
				.username(orgEntity.getUsername())
				.readCnt(readCnt)
				.createDate(orgEntity.getCreateDate())
				.updateDate(orgEntity.getUpdateDate())
				.build();
		
		entity = boardRepository.save(entity);
			
		
		dto = BoardDTO.toDto(entity);
		
		return dto;
	}
	/*
	 * public BoardDTO count(BoardDTO dto) {
	 * 
	 * dto.getClass(); dto.setReadCnt(dto.getReadCnt() + 1); readCntUpdate(dto);
	 * 
	 * return dto; }
	 */
	


}
