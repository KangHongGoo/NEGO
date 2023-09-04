package kr.co.tj.board;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardForm {

	@NotEmpty(message = "필수 항목입니다.")
	private String subject;
	
	@NotEmpty(message = "필수 항목입니다.")
	private String content;
}
