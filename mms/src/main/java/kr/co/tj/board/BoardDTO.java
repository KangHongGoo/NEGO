package kr.co.tj.board;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

	private int id;
	
	private String subject;
	
	private String content;
	
	private LocalDateTime createDate;
	
	private LocalDateTime updateDate;
	private String nickName;
}
