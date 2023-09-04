package kr.co.tj.orderservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tj.orderservice.dto.OrderDTO;
import kr.co.tj.orderservice.dto.OrderEntity;
import kr.co.tj.orderservice.dto.OrderResponse;
import kr.co.tj.orderservice.feign.CatalogFeign;
import kr.co.tj.orderservice.jpa.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CatalogFeign catalogFeign;

	public OrderDTO createOrder(OrderDTO orderDTO) {
		
		orderDTO = getDate(orderDTO);
		
		orderDTO.setOrderId(UUID.randomUUID().toString());
		
		orderDTO.setTotalPrice(orderDTO.getUnitPrice() * orderDTO.getQty());
		
		OrderEntity orderEntity = orderDTO.toOrderEntity();
		
		orderEntity = orderRepository.save(orderEntity);
		
		
		/*
		OrderResponse orderResponse = orderDTO.toOrderResponse();
		
		String feignResult = catalogFeign.updateStockByProductId(orderResponse);
		
		if(feignResult.startsWith("0")) {
			orderRepository.delete(orderEntity);
			
			return null;
		} 
		*/
		
		return orderDTO;
	}
	
	private OrderDTO getDate(OrderDTO orderDTO) {
		Date now = new Date();
		
		if(orderDTO.getCreateAt() == null) {
			orderDTO.setCreateAt(now);
		}
		orderDTO.setUpdateAt(now);
		
		return orderDTO;
	}

	public List<OrderDTO> getOrdersByUsername(String username) {
		
		List<OrderEntity> dbList = orderRepository.findByUsername(username);
		List<OrderDTO> list = new ArrayList<>();
		
		for(OrderEntity x : dbList ) {
			OrderDTO orderDTO = OrderDTO.toOrderDTO(x);
			list.add(orderDTO);
		}
		
		
		return list;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
