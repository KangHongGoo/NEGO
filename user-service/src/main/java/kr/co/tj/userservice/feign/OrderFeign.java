package kr.co.tj.userservice.feign;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.tj.userservice.dto.OrderResponse;

@FeignClient(name = "order-service")
public interface OrderFeign {
	
	@GetMapping("order-service/orders/user/{username}")
	public List<OrderResponse> getOrdersByUsername(@PathVariable String username);
}
