package kr.co.tj.catalogservice.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int status;
	
	private String orderId;
	
	private String userName;
	
	private String productId;
	
	private Long qty;
	
	private Long unitPrice;
	
	private Long totalPrice;
	
	private Date createAt;
	
	private Date updateAt;
	
	

}
