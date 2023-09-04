package kr.co.tj.staff;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<StaffEntity, Long>{

	StaffEntity findByUsername(String username);

	StaffEntity findByUsernameAndPassword(String username, String orgPassword);

}
