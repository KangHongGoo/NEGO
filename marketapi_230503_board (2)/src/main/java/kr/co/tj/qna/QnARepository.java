package kr.co.tj.qna;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnARepository extends JpaRepository<QnAEntity, Long>{
	
	// 은솔님 코드 추가
	List<QnAEntity> findMineByUsername(String username);

	Page<QnAEntity> findAll(Pageable pageable);

}
