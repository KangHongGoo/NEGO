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
	
	private String restaurantName;
	private float rate;
	private String recommend;
	private String etc;

	private Date createDate;
	private Date updateDate;
}
