package kr.co.tj.mypage;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
// 모든 게시판 공통으로 사용할 수 있는 기본정보를 담은 dto 생성
public class MypageDTO {

	private Long id;
	private String title;
	private String content;
	private String username;
	private Date createDate;
	private Date updateDate;

}
