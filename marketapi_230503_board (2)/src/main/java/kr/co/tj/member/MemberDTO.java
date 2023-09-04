package kr.co.tj.member;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

	private String id;
	
	private String username;
	
	private String nickname;
	
	private String password;
	
	//변경 비밀번호1, 회원가입시 비밀번호 확인
	private String orgPassword;
	
	//변경 비밀번호2
	private String orgPassword2;
	
	private Date createDate;
	
	private Date updateDate;

	private Integer role;
	
	private String token;

	public static MemberDTO toDto(MemberEntity entity) {
		return MemberDTO.builder()
				.id(entity.getId())
				.username(entity.getUsername())
				.nickname(entity.getNickname())
				.password(entity.getPassword())
				.createDate(entity.getCreateDate())
				.updateDate(entity.getUpdateDate())
				.role(entity.getRole())
				.build();
	}
	
}
