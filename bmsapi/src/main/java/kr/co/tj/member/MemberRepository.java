package kr.co.tj.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String>{

	MemberEntity findByUsername(String username);

	MemberEntity findByUsernameAndPassword(String username, String password);

	

}
