package kr.co.tj.reply;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {
	
	private Integer id;
	private Integer bid;
	private String content;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private String nickName;

}
