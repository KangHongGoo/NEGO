package kr.co.tj.member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
	
	@Size(min=3, max=25)
	@NotEmpty(message = "사용자 ID는 필수항목입니다.")
	private String nickName;
	
	@NotEmpty(message = "사용자명은 필수항목입니다.")
	private String name;
	
	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	private String password1;
	
	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	private String password2;
	
	
}
