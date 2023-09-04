package kr.co.tj.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
	
	@Id // 기본키 (=주키)
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ID값을 자동으로 1씩 늘려서 입력
	
	private Long id;
	
	@Column(unique = true) // 중복 입력 거부
	private String nickName;
	
	private String name;
	
	private String password;
	

	private String role;
    
	

}
