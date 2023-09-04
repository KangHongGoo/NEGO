package kr.co.tj.board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	public BoardDTO insert(BoardDTO dto) {
		BoardEntity entity = dto.toEntity();
		//BoardEntity entity = new ModelMapper().map(dto, BoardEntity.class);
		
		Date now = new Date();
		entity.setCreateDate(now);
		entity.setUpdateDate(now);
		
		entity = boardRepository.save(entity);
		
		return BoardDTO.toDto(entity);
		//return new ModelMapper().map(entity, BoardDTO.class);
	}

	public BoardDTO findById(Long id) {
		Optional<BoardEntity> optional = boardRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("게시글 가져오기 실패");
		}
		
		BoardEntity entity = optional.get();
		
		return BoardDTO.toDto(entity);
		//return new ModelMapper().map(entity, BoardDTO.class);
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
			throw new RuntimeException("해당 데이터는 없습니다.");
		}
		
		BoardEntity entity = optional.get();

		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());
		entity.setUpdateDate(new Date());

		entity = boardRepository.save(entity);

	    return BoardDTO.toDto(entity);
	}

	@Transactional
	public void delete(Long id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public void increaseReadCnt(Long id) {
		if (id == null) {
            throw new RuntimeException("게시글 ID가 유효하지 않습니다.");
        }

        BoardEntity entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        entity.setReadCnt(entity.getReadCnt() + 1);
        boardRepository.save(entity);
	}

}
