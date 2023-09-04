package kr.co.tj.board.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long>{

	Hashtag findByName(String name);

}
