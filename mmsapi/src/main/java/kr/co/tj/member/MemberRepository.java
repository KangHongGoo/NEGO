package kr.co.tj.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long>{

	MemberEntity findByUsername(String username);

	MemberEntity findByUsernameAndPassword(String username, String password);



	

}
