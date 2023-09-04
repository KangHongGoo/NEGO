package kr.co.tj.board;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Integer> {

	@Modifying(clearAutomatically = true)
	@Query(value = "update Board b set b.subject=:subject, b.content =:content, b.updateDate =:updateDate where b.id=:id", nativeQuery = true)
	void update(String subject, String content, LocalDateTime updateDate, Integer id);

	Page<Board> findAll(Pageable pageable);

}
