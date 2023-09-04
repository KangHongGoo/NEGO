package kr.co.tj.replyInfi;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

	private Long qnaid; // 게시물의 고유 id = bid
	
	private Long replyid; // 댓글의 고유 id
	
	private Long parentReplyid; // 대댓글의 고유 id = 부모 댓글의 고유 id 
	
	private List<ReplyDTO> childReplies; // 대댓글 목록
	
	private String username;

	private String content;
	
	private Date createDate;
	
	private Date updateDate;


}
