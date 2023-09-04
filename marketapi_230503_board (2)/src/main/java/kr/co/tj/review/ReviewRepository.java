package kr.co.tj.review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
	
	List<ReviewEntity> findByTitleContaining(String keyword);
	List<ReviewEntity> findByContentContaining(String keyword);
	List<ReviewEntity> findByTitleContainingOrContentContaining(String keyword, String keyword2);

	List<ReviewEntity> findMineByUsername(String username);
		// 은솔님 코드
	
	Page<ReviewEntity> findAll(Pageable pageable);
	
	@Query("SELECT r FROM ReviewEntity r WHERE r.title LIKE %:keyword% OR r.content LIKE %:keyword%")
	Page<ReviewEntity> findSearchAll(Pageable pageable, @Param("keyword") String keyword);
}