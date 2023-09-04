package kr.co.tj.board;

import java.util.Date;



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
	
	public static BoardDTO toDto(BoardEntity entity) {
		return BoardDTO.builder()
		.id(entity.getId())
		.cid(entity.getCid())
		.username(entity.getUsername())
		.title(entity.getTitle())
		.content(entity.getContent())
		.fileId(entity.getFileId())
		.createDate(entity.getCreateDate())
		.updateDate(entity.getUpdateDate())
		.readCnt(entity.getReadCnt())
		.build();
	}
	  
	  public BoardDTO toBoardDTO(BoardEntity entity) {
			this.id =entity.getId();
			this.username = entity.getUsername();
			this.title = entity.getTitle();
			this.content = entity.getContent();
			this.fileId = entity.getFileId();
			this.createDate = entity.getCreateDate();
			this.updateDate = entity.getUpdateDate();
			this.readCnt = entity.getReadCnt();
			
			return this;
		
	

	
	

}
}
