package kr.co.tj.member;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
	
	private long id;
	
	
	private String username;
	
	
	private String name;
	
	
	private String password;
	private String password2;
	
	private String orgPassword;
	
	private Date createDate;
	
	private Date updateDate;

}
