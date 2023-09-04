package kr.co.tj.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{

	

	Page<BoardEntity> findByTitleContainingOrContentContaining(String keyword, String keyword2, Pageable pageable);
	
	List<BoardEntity> findAllByUsername(String username);

	


	BoardEntity findByUsername(String username);

	Optional<BoardEntity> findByCid(Long cid);

}
