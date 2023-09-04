package kr.co.tj.orderservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.orderservice.dto.OrderDTO;
import kr.co.tj.orderservice.dto.OrderRequest;
import kr.co.tj.orderservice.dto.OrderResponse;
import kr.co.tj.orderservice.kafka.KafkaProducer;
import kr.co.tj.orderservice.kafka.OrderProducer;
import kr.co.tj.orderservice.service.OrderService;

@RestController
@RequestMapping("/order-service")
public class OrderController {

	private OrderService orderService;
	
	private KafkaProducer kafkaProducer;
	private OrderProducer orderProducer;
	
	@Autowired
	public OrderController(OrderService orderService, KafkaProducer kafkaProducer,OrderProducer orderProducer) {
		super();
		this.orderService = orderService;
		this.kafkaProducer = kafkaProducer;
		this.orderProducer = orderProducer;
	}

	@GetMapping("/orders/user/{username}")
	public ResponseEntity<?> getOrdersByUsername(@PathVariable String username) {

		List<OrderDTO> list = orderService.getOrdersByUsername(username);

		List<OrderResponse> responseList = new ArrayList<>();

		for (OrderDTO orderDTO : list) {
			OrderResponse orderResponse = orderDTO.toOrderResponse();
			responseList.add(orderResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}

	@PostMapping("/orders")
	public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest){
		
		OrderDTO orderDTO = OrderDTO.toOrderDTO(orderRequest);
		orderDTO = orderService.createOrder(orderDTO);
		
	//	orderDTO.setOrderId(UUID.randomUUID().toString());
	//	orderDTO.setTotalPrice(orderDTO.getUnitPrice() * orderDTO.getQty()  );
			
		//kafka code
		kafkaProducer.send("order-insert-topic", orderDTO);
	//	orderProducer.send("orders", orderDTO);
		
		
		OrderResponse orderResponse = orderDTO.toOrderResponse();
		return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
	}

	@GetMapping("/health_check")
	public String status() {
		return "user service입니다";
	}

}
