package kr.co.tj.board.hashtag;

import java.util.List;

import kr.co.tj.board.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HashtagDTO {
	
	private long id;
	
	private String name;
	
	private List<BoardEntity> boards;

}
