package kr.co.tj.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
	
	private long id;
	
	private String nickName;
	
	private String name;
	
	private String password;
	
	private String role;
    
	

}
