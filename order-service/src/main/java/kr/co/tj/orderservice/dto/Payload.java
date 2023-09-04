package kr.co.tj.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payload {
	
	private String username;
	private String productId;
	private Long qty;
	private Long unitPrice;
	private Long totalPrice;
	private String orderId;
}