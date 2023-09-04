package kr.co.tj.replyInfi;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

	Optional<ReplyEntity> findByReplyid(Long replyid);

	//List<ReplyEntity> findByParentReplyid(Long replyid);

	List<ReplyEntity> findByQnaid(Long qnaid, Sort sort);
	
	// 은솔님 코드 추가
	List<ReplyEntity> findMineByUsername(String username);

	
	
	List<ReplyEntity> findByParentReplyid(ReplyEntity parentReply);

	List<ReplyEntity> findByParentReplyid(Long parentReplyid);

	List<ReplyEntity> findAllByParentReplyidIsNotNull();

	List<ReplyEntity> findAllByParentReplyidIsNull();


	

}
