package kr.co.tj.userservice.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	

	private String username;

	private String name;
	
	private Date createAt;
	
	private Date updateAt;
	
	private List<OrderResponse> orderList;
	
	private String token;
	

}
