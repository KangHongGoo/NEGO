package kr.co.tj.board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	public BoardDTO insert(BoardDTO dto) {
		
		BoardEntity entity = new ModelMapper().map(dto, BoardEntity.class);
		
		
		entity.setCreateDate(new Date());
		boardRepository.save(entity);
		
		
		return new ModelMapper().map(entity, BoardDTO.class);
	}

	public BoardDTO getById(Long id) {
		
		Optional<BoardEntity> optional = boardRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("X");
		}
		BoardEntity entity = optional.get();
		
		
		return new ModelMapper().map(entity, BoardDTO.class);
	}

	public List<BoardDTO> getAll() {
		
		List<BoardEntity> list_entity = boardRepository.findAll();
		List<BoardDTO> list_dto = new ArrayList<>();
		
		list_entity.forEach(e -> {
			list_dto.add(new ModelMapper().map(e, BoardDTO.class));
		});
		
		
		return list_dto;
	}

	public BoardDTO update(BoardDTO dto) {
		
		BoardEntity entity = boardRepository.findByUsername(dto.getUsername());
		
	
		
		entity.setUpdateDate(new Date());
		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());
		
		boardRepository.save(entity);
		
		return new ModelMapper().map(entity, BoardDTO.class);
	}

	public void delete(BoardDTO dto) {
		BoardEntity entity = boardRepository.findByUsername(dto.getUsername());
		
		if(entity == null) {
			throw new RuntimeException("정보가 없습니다");
		}
		
		boardRepository.delete(entity);
		
	}
	
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
				.createDate(orgEntity.getCreateDate())
				.updateDate(orgEntity.getUpdateDate())
				.build();
		
		entity = boardRepository.save(entity);
		
		
		return new ModelMapper().map(entity, BoardDTO.class);
	}

}
