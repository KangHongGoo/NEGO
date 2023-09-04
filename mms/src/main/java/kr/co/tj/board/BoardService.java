package kr.co.tj.board;


import java.time.LocalDateTime;
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

import kr.co.tj.DataNotFoundException;



@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	public BoardDTO save(BoardForm boardForm, String nickName) {
		
		Board board;
		 {
			board = new Board();
			board.setContent(boardForm.getContent());
			board.setSubject(boardForm.getSubject());
			board.setCreateDate(LocalDateTime.now());
			board.setUpdateDate(LocalDateTime.now());
			board.setNickName(nickName);
			board = boardRepository.save(board);
			
		}
		return new ModelMapper().map(board, BoardDTO.class);
		
	
	}
	
	public BoardDTO save(BoardForm boardForm) {
		
		Board board = new Board();
		board.setContent(boardForm.getContent());
		board.setSubject(boardForm.getSubject());
		board.setCreateDate(LocalDateTime.now());
		board.setUpdateDate(LocalDateTime.now());
		
		board = boardRepository.save(board);
		
		return new ModelMapper().map(board, BoardDTO.class);
		
	}
	private BoardDTO entityToDto(Board board) {
		int id = board.getId();
		String subject = board.getSubject();
		String content = board.getContent();
		LocalDateTime createDate = board.getCreateDate();
		LocalDateTime updateDate = board.getUpdateDate();
		String nickName = board.getNickName();
		
		return new BoardDTO(id, subject, content, createDate, updateDate, nickName);
	}
	
	public Page<BoardDTO> findAll(int page) {
		
		List<Sort.Order> sortList = new ArrayList<>();
		sortList.add(Sort.Order.desc("id"));
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sortList));
	
		Page<Board> page_board = boardRepository.findAll(pageable);
		
		Page<BoardDTO> page_dto = page_board.map(board -> new BoardDTO(board.getId(), board.getSubject(), null, null, board.getUpdateDate(), board.getNickName()));
		
		
		
		
		return page_dto;
	
}
	

	public List<BoardDTO> findAll() {
		
		
		List<Board> list = boardRepository.findAll();
		
		List<BoardDTO> listDto = new ArrayList<>();
		
		for(int i = 0 ; i <list.size(); i ++) {
			Board board = list.get(i);

			
			BoardDTO dto = new ModelMapper().map(board, BoardDTO.class);
			
			listDto.add(dto);
		}
		
		return listDto;
	}
	public BoardDTO getId(Integer id) {
		Optional<Board> optional = boardRepository.findById(id);
		
		if(optional.isEmpty()) {
			return null;
		}
		
		Board board = optional.get();
		
		return new ModelMapper().map(board, BoardDTO.class);
		
		


	
	

	}
	
	@Transactional
	public void update(BoardDTO dto) {
		Board board = new ModelMapper().map(dto, Board.class);
		board.setUpdateDate(LocalDateTime.now());
		
		boardRepository.update(
				board.getSubject(),
				board.getContent(),
				board.getUpdateDate(),
				board.getId()
				);
		
	}
	@Transactional
	public void delete(Integer id) {
		boardRepository.deleteById(id);
		
	}
	
	
	
}
