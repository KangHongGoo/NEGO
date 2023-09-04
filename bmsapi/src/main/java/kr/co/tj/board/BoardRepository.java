package kr.co.tj.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {



}
