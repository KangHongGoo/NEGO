package kr.co.tj.userservice.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	

	private String id;

	private String username;

	private String password;

	private String name;
	
	private Date createAt;
	
	private Date updateAt;
	
	private String token;
	
	private List<OrderResponse> orderList;
	
	public static UserDTO toUserDTO(UserRequest ureq) {
		return UserDTO.builder()
				.username(ureq.getUsername())
				.password(ureq.getPassword())
				.name(ureq.getName())
				.build();
	}

	public UserResponse toUserResponse() {
		
		return UserResponse.builder()
				.username(username)
				.name(name)
				.createAt(createAt)
				.updateAt(updateAt)
				.orderList(orderList)
				.token(token)
				.build();
	}

	public UserEntity toUserEntity() {
		
		return UserEntity.builder()
				.username(username)
				.password(password)
				.name(name)
				.createAt(createAt)
				.updateAt(updateAt)
				.build();
	}

	public UserDTO toUserDTO(UserEntity userEntity) {
		 this.username = userEntity.getUsername();
		 this.name = userEntity.getName();
		 this.createAt = userEntity.getCreateAt();
		 this.updateAt = userEntity.getUpdateAt();
		 this.token = userEntity.getToken();
		
		return this;
	}

	public static UserDTO toUserDTO(UserLoginRequest userLoginRequest) {
		
		return UserDTO.builder()
				.username(userLoginRequest.getUsername())
				.password(userLoginRequest.getPassword())
				.build();
	}
	

}
