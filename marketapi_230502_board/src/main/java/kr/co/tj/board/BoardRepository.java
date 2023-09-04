package kr.co.tj.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{

	BoardEntity findByUsername(String username);

	Page<BoardEntity> findByTitleContainingOrContentContaining(String keyword, String keyword2, Pageable pageable);
	
	List<BoardEntity> findAllByUsername(String username);

	List<BoardEntity> findByCid(Long cid, Sort sort);

	List<BoardEntity> findByHashtagsName(String hashtagName);

}
