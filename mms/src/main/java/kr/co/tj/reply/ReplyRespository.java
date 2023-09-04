package kr.co.tj.reply;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRespository extends JpaRepository<Reply, Integer>{
	List<Reply> findByBid(Integer bid, Sort sort);
	
	/* Optional<Reply> findById(Integer id); */
}
