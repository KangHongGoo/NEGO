package kr.co.tj.board;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.co.tj.board.hashtag.Hashtag;
import kr.co.tj.board.hashtag.HashtagRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private HashtagRepository hashtagRepository;
	
	public List<BoardEntity> getBoardByHashtag(String hashtagName) {
		return boardRepository.findByHashtagsName(hashtagName);
	}
	
	public BoardEntity createBoard(BoardEntity board) {
		
		board = boardRepository.save(board);
		
		List<Hashtag> hashtags = new ArrayList<>();
		for(Hashtag hashtag : board.getHashtags()) {
			Hashtag foundHashtag = hashtagRepository.findByName(hashtag.getName());
			
			if(foundHashtag == null) {
				foundHashtag = hashtagRepository.save(hashtag);
			}
			hashtags.add(foundHashtag);
		}
		board.setHashtags(hashtags);
		board = boardRepository.save(board);
		
		return board;
	}
	
	public List<BoardDTO> findAllByUsername(String username) {

	      List<BoardEntity> entity = boardRepository.findAllByUsername(username);
	      List<BoardDTO> dto = new ArrayList<>();

	      entity.forEach(e -> {
	         dto.add(new ModelMapper().map(e, BoardDTO.class));
	      });
	      return dto;
	   }
	 
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
						 boardEntity.getReadCnt(),
						 boardEntity.getHashtags()
						  )
				 );
		 return page_dto;
	}
	

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

	public Page<BoardDTO> findAll(int page) {
		List<Sort.Order> sortList = new ArrayList<>();
		sortList.add(Sort.Order.desc("createDate"));
		
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
						boardEntity.getReadCnt(),
						boardEntity.getHashtags()
						
						
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

	public List<BoardDTO> findByCid(Long cid) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		List<BoardEntity> list_entity = boardRepository.findByCid(cid, sort);
		
		List<BoardDTO> list_dto = new ArrayList<>();
		
		for(int i = 0; i <list_entity.size(); i++) {
			BoardEntity entity = list_entity.get(i);
			BoardDTO dto = new ModelMapper().map(entity, BoardDTO.class);
			list_dto.add(dto);
		}
		return list_dto;
	}

}
