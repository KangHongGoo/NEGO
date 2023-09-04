package kr.co.tj.boardservice.dto;

import java.io.Serializable;
import java.util.Date;

import kr.co.tj.boardservice.persistence.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long cid;
	
	private String username;

	private String title;

	private String content;
	
	private Date createDate;
	
	private Date updateDate;
	
	private Long readCnt;
	
	private String cateName;
	
	public static BoardDTO toBoardDTO(BoardRequest boardRequest) {
		
		return BoardDTO.builder()
				.id(boardRequest.getId())
				.cid(boardRequest.getCid())
				.username(boardRequest.getUsername())
				.title(boardRequest.getTitle())
				.content(boardRequest.getContent())
				.build();
	}
	
	public BoardResponse toBoardResponse() {
		return BoardResponse.builder()
				.id(id)
				.cid(cid)
				.username(username)
				.title(title)
				.content(content)
				.createDate(createDate)
				.updateDate(updateDate)
				.readCnt(readCnt)
				.cateName(cateName)
				.build();
	}
	
	public BoardEntity toBoardEntity() {
		return BoardEntity.builder()
				.id(id)
				.cid(cid)
				.username(username)
				.title(title)
				.content(content)
				.createDate(createDate)
				.updateDate(updateDate)
				.readCnt(readCnt)
				.cateName(cateName)
				.build();
		}
	public  BoardDTO toBoardDTO2(BoardEntity entity) {
		this.id = entity.getId();
		this.cid = entity.getCid();
		this.username = entity.getUsername();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.createDate = entity.getCreateDate();
		this.updateDate = entity.getUpdateDate();
		this.readCnt = entity.getReadCnt();	
		this.cateName = entity.getCateName();
		return this;
	} 
	
	
public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
		
		return BoardDTO.builder()
				.id(boardEntity.getId())
				.username(boardEntity.getUsername())
				.cid(boardEntity.getCid())
				.username(boardEntity.getUsername())
				.title(boardEntity.getTitle())
				.content(boardEntity.getContent())
				.readCnt(boardEntity.getReadCnt())
				.createDate(boardEntity.getCreateDate())
				.updateDate(boardEntity.getUpdateDate())
				.cateName(boardEntity.getCateName())
				.build();
	}

public BoardResponse toBoardFindResponse(BoardEntity x) {
	
	return BoardResponse.builder()
			.id(x.getId())
			.cid(x.getCid())
			.username(x.getUsername())
			.title(x.getTitle())
			.content(x.getContent())
			.createDate(x.getCreateDate())
			.updateDate(x.getUpdateDate())
			.readCnt(x.getReadCnt())
			.cateName(x.getCateName())
			.build();
}

	
	
}
