package kr.co.tj.reply;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
	
	private Long id;
	
	private Long bid;
	
	private String username;

	private String content;
	
	private Date createDate;
	
	private Date updateDate;


}
