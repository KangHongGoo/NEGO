package kr.co.tj.board;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
	
	private long id;
	

	private String username;
	

	private String title;
	

	private String content;
	
	
	private Date createDate;
	
	
	private Date updateDate;
	

	private long readCnt;
	
	public static BoardDTO toDto(BoardEntity entity) {
		return BoardDTO.builder()
		.id(entity.getId())
		.username(entity.getUsername())
		.title(entity.getTitle())
		.content(entity.getContent())
		.createDate(entity.getCreateDate())
		.updateDate(entity.getUpdateDate())
		.readCnt(entity.getReadCnt())
		.build();
	}
	
	public BoardEntity toEntity() {
		return BoardEntity.builder()
		.id(id)
		.username(username)
		.title(title)
		.content(content)
		.createDate(createDate)
		.updateDate(updateDate)
		.readCnt(readCnt)
		.build();
	}

}
