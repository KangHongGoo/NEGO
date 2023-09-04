package kr.co.tj.qna;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class QnADTO {
	
	private Long id;
	
	private String username;
	
	private String title;
	
	private Long fileId;
	
	private String content;
	
	private Date createDate;
	
	private Date updateDate;
	
	

}
