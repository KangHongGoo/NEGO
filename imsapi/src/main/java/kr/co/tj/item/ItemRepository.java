package kr.co.tj.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<ItemEntity, Long>{

	List<ItemEntity> findByStaff(String staff);


}
