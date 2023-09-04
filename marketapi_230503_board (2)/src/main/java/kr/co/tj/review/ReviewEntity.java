package kr.co.tj.review;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="review")
public class ReviewEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;	
	@Column(nullable = false)
	private float rate;
	@Column(nullable = false)
	private int count;
	
	private String img;

	private Date createDate;
	private Date updateDate;
}
