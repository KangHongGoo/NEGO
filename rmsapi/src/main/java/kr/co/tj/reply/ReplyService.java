package kr.co.tj.reply;


import java.util.ArrayList;
import java.util.Date;
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
	private ReplyRepository replyRepository;

	public ReplyDTO save(ReplyDTO dto) {
		
		ReplyEntity entity = new ReplyEntity.ReplyEntityBuilder()
				.bid(dto.getBid())
				.content(dto.getContent())
				.username(dto.getUsername())
				.createDate(new Date())
				.updateDate(new Date())
				.build();
		
		entity = replyRepository.save(entity);
		
		dto.setBid(entity.getBid());
		dto.setContent(entity.getContent());
		dto.setId(entity.getId());
		
		return dto;
	}

	@Transactional
	public ReplyDTO update(ReplyDTO dto) {
	    Optional<ReplyEntity> optional = replyRepository.findById(dto.getId());
	    
	    if (!optional.isPresent()) {
	        throw new RuntimeException("해당하는 댓글이 없습니다.");
	    }
	    
	    ReplyEntity reply = optional.get();
	    
	    reply.setContent(dto.getContent());
	    reply.setUpdateDate(new Date());
	    
	    
	    reply = replyRepository.save(reply);
	    		
	    
	    
	    return new ModelMapper().map(reply, ReplyDTO.class);
	}

	@Transactional
	public void delete(Long id) {
		replyRepository.deleteById(id);
	}

	
	@Transactional
	public List<ReplyDTO> findAll() {
		
		List<ReplyEntity> reply_entity = replyRepository.findAll();
		List<ReplyDTO> list = new ArrayList<>();

		reply_entity.forEach(e -> {
			list.add(new ModelMapper().map(e, ReplyDTO.class));
		});
		return list;
	}

	@Transactional
	public ReplyDTO findById(Long id) {
		Optional<ReplyEntity> optional=replyRepository.findById(id);
		if (!optional.isPresent()) {
			throw new RuntimeException("리뷰 가져오기 실패");
		}

		ReplyEntity entity = optional.get();
		return new ModelMapper().map(entity,ReplyDTO.class);
	}

	//bid 추가
	public List<ReplyDTO> findByBid(Long bid) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
		List<ReplyEntity> list = replyRepository.findByBid(bid, sort);
		
		List<ReplyDTO> list2 = new ArrayList<>();
		
		for(int i = 0 ; i < list.size(); i++) {
			ReplyEntity entity = list.get(i);
			ReplyDTO dto = new ModelMapper().map(entity, ReplyDTO.class);
			list2.add(dto);
		}
		return list2;
	}


}
