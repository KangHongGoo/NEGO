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
	
	
	
	
	
	public MemberEntity toMemberEntity2(MemberDTO dto) {
		MemberEntity entity = new MemberEntity(dto.getId(), dto.getUsername(), dto.getName(), dto.getCreateDate(), dto.getUpdateDate(), dto.getPassword(), dto.getRole());
		
		return entity;
	}
	
	
	public MemberEntity toMemberEntity(MemberDTO dto) {
		MemberEntity entity = MemberEntity.builder()
				.id(dto.getId())
				.username(dto.getUsername())
				.name(dto.getName())
				.createDate(dto.getCreateDate())
				.updateDate(dto.getUpdateDate())
				.password(dto.getPassword())
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
