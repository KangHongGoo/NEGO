package kr.co.tj.reply;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {


	List<ReplyEntity> findByBid(Long bid, Sort sort);

}
