package kr.co.tj.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>{

	MemberEntity findByUsername(String username);

	MemberEntity findByUsernameAndPassword(String username, String password);

	Optional<MemberEntity> findById(String id);

}
