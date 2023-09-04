package kr.co.tj.member;



import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDTO {
	
	private String id;
	
	
	private String username;
	
	
	private String name;
	
	private Date createDate;
	
	private Date updateDate;
	
	
	private String password;
	private String password2;
	private String orgPassword;
	
	private String token;
	private int role;
	
	
	public MemberEntity toMemberEntity(MemberDTO dto) {
		MemberEntity entity = MemberEntity.builder()
				.id(dto.getId())
				.username(dto.getUsername())
				.name(dto.getName())
				.password(dto.getPassword())
				.createDate(dto.getCreateDate())
				.updateDate(dto.getUpdateDate())
				.build();
				
				return entity;
		
	}
	
	public MemberDTO toMemberDTO(MemberEntity entity) {
		this.id = entity.getId();
		this.username = entity.getUsername();
		this.name = entity.getName();
		this.createDate = entity.getCreateDate();
		this.updateDate = entity.getUpdateDate();
		
		
		return this;
		
	}

}
