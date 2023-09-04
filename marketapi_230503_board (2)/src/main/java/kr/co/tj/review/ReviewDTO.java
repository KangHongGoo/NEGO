package kr.co.tj.review;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

	private Long id;
	private String username;
	private String title;
	private String content;
	private float rate;
	private int count;
	
	private String img;
	
	private Date createDate;
	private Date updateDate;
}
