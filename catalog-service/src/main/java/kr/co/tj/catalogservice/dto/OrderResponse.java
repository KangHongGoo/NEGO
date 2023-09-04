package kr.co.tj.catalogservice.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String productId;
	
	private String orderId;
	
	private Long qty;
	
	private Long unitPrice;

	private Long totalPrice;
	
	private Date createAt;
	
	private Date updateAt;

}
