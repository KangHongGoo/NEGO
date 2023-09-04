package kr.co.tj.reply;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



@Service
public class ReplyService {
	
	@Autowired
	private ReplyRespository replyRespository;
	
	

	public ReplyDTO save(ReplyDTO dto) {
		
		
		Reply reply = new ModelMapper().map(dto, Reply.class);
		
		reply = replyRespository.save(reply);
		
		
		return new ModelMapper().map(reply, ReplyDTO.class);
	}

	public List<ReplyDTO> findByBid(Integer bid) {
		Sort sort = Sort.by(Sort.Direction.DESC, "updateDate");
		List<Reply> list = replyRespository.findByBid(bid, sort);
		
		List<ReplyDTO> list2 = new ArrayList<>();
		
		for(int i =0; i < list.size();i++) {
			Reply reply = list.get(i);
			ReplyDTO dto = new ModelMapper().map(reply, ReplyDTO.class);
			list2.add(dto);
		}
		return list2;
	}

	public ReplyDTO findById(Integer id) {
		Optional<Reply> optional = replyRespository.findById(id);
		
		if(!optional.isPresent()) {
			return null;
		}
		
		
		return new ModelMapper().map(optional.get(), ReplyDTO.class);
	}
	@Transactional
	public void delete(Integer id) {
		replyRespository.deleteById(id);
		
	}

	@Transactional
	public ReplyDTO update(ReplyDTO dto_db) {
		Reply reply = new ModelMapper().map(dto_db, Reply.class);
		reply.setUpdateDate(LocalDateTime.now());
		
		reply = replyRespository.save(reply);
		
		dto_db = new ModelMapper().map(reply, ReplyDTO.class);
		
		return dto_db;
	}


		
	

	

	
	
	
	
}