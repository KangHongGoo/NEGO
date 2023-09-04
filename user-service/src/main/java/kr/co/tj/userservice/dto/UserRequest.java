package kr.co.tj.userservice.dto;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserRequest{

	private String username;

	private String password;
	private String password2;
	private String orgPassword;

	private String name;
	private String token;
}
