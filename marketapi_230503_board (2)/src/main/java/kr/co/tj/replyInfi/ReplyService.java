package kr.co.tj.replyInfi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.co.tj.mypage.MypageDTO;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;
	
	
	
	
	// 은솔님 코드 추가
	 public List<MypageDTO> findAllMypageRepliesByUsername(String username) {
	      List<ReplyEntity> entity = replyRepository.findMineByUsername(username);
	      return entity.stream().map(replyEntity -> new ModelMapper().map(replyEntity, MypageDTO.class))
	            .collect(Collectors.toList());
	   }



	
	@Transactional     // 부모 댓글 id
	public ReplyDTO findByReplyid(Long replyid) {
		Optional<ReplyEntity> optional = replyRepository.findByReplyid(replyid);
		if (!optional.isPresent()) {
			throw new RuntimeException("댓글 가져오기 실패");
		}

		ReplyEntity entity = optional.get();
		return new ModelMapper().map(entity, ReplyDTO.class);
	}

	
	
	@Transactional // 게시물 id
	public List<ReplyDTO> findByQnaid(Long qnaid) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
		List<ReplyEntity> list = replyRepository.findByQnaid(qnaid, sort);

		/*
		 * List<ReplyDTO> list2 = new ArrayList<>();
		 * 
		 * for (int i = 0; i < list.size(); i++) { ReplyEntity entity = list.get(i);
		 * ReplyDTO dto = new ModelMapper().map(entity, ReplyDTO.class); list2.add(dto);
		 * }   - 아래 람다식과 같음
		 */
		
		List<ReplyDTO> list2 = list.stream()
                .map(entity -> new ModelMapper().map(entity, ReplyDTO.class))
                .collect(Collectors.toList());
		return list2;
	}

	

	@Transactional
	public ReplyDTO save(ReplyDTO dto) {

	    ReplyEntity entity = new ReplyEntity.ReplyEntityBuilder()  // 빌더패턴 사용하여 인스턴스 생성
	            .qnaid(dto.getQnaid())
	            .content(dto.getContent())
	            .username(dto.getUsername())
	            .createDate(new Date())
	            .updateDate(new Date())
	            .build();

	    if (dto.getParentReplyid() != null) { // 대댓글인 경우
	        ReplyEntity parentEntity = 
	        		replyRepository.findById(dto.getParentReplyid())
	                .orElseThrow(() -> new IllegalArgumentException("부모 댓글 정보가 없습니다."));
	        entity.setParentReplyid(parentEntity); // 부모 댓글 정보 설정
	    }

	    entity = replyRepository.save(entity);

	    dto.setQnaid(entity.getQnaid());
	    dto.setContent(entity.getContent());
	    dto.setReplyid(entity.getReplyid());

	    return dto;
	}

	@Transactional
	public ReplyDTO update(ReplyDTO dto) {
	    Optional<ReplyEntity> optional = replyRepository.findByReplyid(dto.getReplyid());

	    if (!optional.isPresent()) {
	        throw new RuntimeException("해당하는 댓글이 없습니다.");
	    }

	    ReplyEntity entity = optional.get();

	    if (dto.getContent() != null) {
	        entity.setContent(dto.getContent());
	    }

	    if (dto.getUsername() != null) {
	        entity.setUsername(dto.getUsername());
	    }

	    entity.setUpdateDate(new Date());

	    if (dto.getParentReplyid() != null) {
	        Optional<ReplyEntity> parentOptional = replyRepository.findByReplyid(dto.getParentReplyid());

	        if (!parentOptional.isPresent()) {
	            throw new RuntimeException("상위 댓글 정보가 잘못되었습니다.");
	        }

	        entity.setParentReplyid(parentOptional.get());
	    } else {
	        entity.setParentReplyid(null);
	    }

	    entity = replyRepository.save(entity);

	    return new ModelMapper().map(entity, ReplyDTO.class);
	}

	
	@Transactional
	public void delete(Long replyid) {
	    Optional<ReplyEntity> optional = replyRepository.findById(replyid);
	    if (optional.isPresent()) {
	        ReplyEntity entity = optional.get();
	        Long parentReplyid = entity.getParentReplyid() != null ? entity.getParentReplyid().getReplyid() : null;

	        replyRepository.deleteById(replyid);

	        if (parentReplyid != null) {
	            List<ReplyEntity> children = replyRepository.findByParentReplyid(parentReplyid);
	            if (children.isEmpty()) {
	                // 대댓글이 없는 경우 부모 댓글도 삭제
	                replyRepository.deleteById(parentReplyid);
	            }
	        }
	    } else {
	        throw new RuntimeException("해당하는 댓글이 없습니다.");
	    }
	}




	// 특정 댓글(replyid)에 대한 모든 대댓글들을 가져옴
	@Transactional
	public List<ReplyEntity> getRepliesByParentReplyId(Long replyid) {
	    List<ReplyEntity> entities = replyRepository.findByParentReplyid(replyid);
	    List<ReplyEntity> list = new ArrayList<>();
	    
	    for (ReplyEntity entity : entities) {
	        List<ReplyEntity> childReplies = getRepliesByParentReplyId(entity.getReplyid());
	        entity.setReplies(childReplies);
	        list.add(entity);
	    }
	    return list;
	}



	/*
	 * @Transactional // 댓글 가져오기 public List<ReplyEntity> findAllParent() {
	 * 
	 * return replyRepository.findAllByParentReplyidIsNull(); }
	 * 
	 * 
	 * 
	 * @Transactional // 대댓글 목록 가져오기 public List<ReplyEntity> findAllChildren(Long
	 * parentReplyid) { // TODO Auto-generated method stub List<ReplyEntity> entity
	 * = replyRepository.findByParentReplyid(parentReplyid); for (ReplyEntity e :
	 * entity) { e.setReplies(findAllChildren(e.getReplyid())); } return entity; }
	 */



	@Transactional
	public List<ReplyDTO> getAllReplies() {
	    List<ReplyEntity> entities = replyRepository.findAll();
	    return entities.stream()
	        .map(entity -> {
	            ReplyDTO dto = convertToDto(entity);
	            return dto;
	        })
	        .collect(Collectors.toList());
	}

	private ReplyDTO convertToDto(ReplyEntity entity) {
	    return ReplyDTO.builder()
	        .replyid(entity.getReplyid())
	        .qnaid(entity.getQnaid())
	        .parentReplyid(entity.getParentReplyid() != null ? entity.getParentReplyid().getReplyid() : null)
	        .username(entity.getUsername())
	        .content(entity.getContent())
	        .createDate(entity.getCreateDate())
	        .updateDate(entity.getUpdateDate())
	        .build();
	}







	/*
	 * @Transactional // 대댓글 까지 모두 가져오기 public List<ReplyEntity> findAll() {
	 * List<ReplyEntity> entity = replyRepository.findAll(); return entity; }
	 */






	

	


}