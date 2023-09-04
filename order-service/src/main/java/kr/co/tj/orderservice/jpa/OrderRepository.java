package kr.co.tj.orderservice.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.tj.orderservice.dto.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{

	List<OrderEntity> findByUsername(String username);

}
