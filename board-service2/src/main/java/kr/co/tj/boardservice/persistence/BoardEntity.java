package kr.co.tj.boardservice.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "boards")
public class BoardEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long cid;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;
	
	private Date createDate;
	
	private Date updateDate;
	
	@ColumnDefault("0")
	private Long readCnt;
	
	private String cateName;

}
