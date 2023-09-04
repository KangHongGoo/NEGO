package kr.co.tj.board;

import java.util.Date;
import java.util.List;

import kr.co.tj.board.hashtag.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
	
	private long id;
	
	private long cid;
	
	
	private String username;
	

	private String title;
	

	private String content;
	
	private Long fileId;
	
	
	private Date createDate;
	
	private Date updateDate;
	
	private long readCnt;
	
	  private List<Hashtag> hashtags;
	

	
	

}
