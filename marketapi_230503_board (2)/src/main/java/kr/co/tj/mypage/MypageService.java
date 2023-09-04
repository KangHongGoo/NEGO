package kr.co.tj.mypage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tj.board.BoardService;
import kr.co.tj.qna.QnAService;
import kr.co.tj.replyInfi.ReplyService;
import kr.co.tj.review.ReviewService;

@Service
public class MypageService {

	@Autowired
	private BoardService boardService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private QnAService qnAService;
	
	@Autowired
	private ReplyService replyService;

	
	// username으로 찾은 글 목록 페이징 처리
	public List<MypageDTO> findAllMypageByUsernameWithPaging(String username, int page, int size) {
		// 각 게시판의 서비스로부터 username으로 찾은 글 목록을 MypageDTO 형태로 가져옴
		List<MypageDTO> boardDtoList = boardService.findAllMypageByUsername(username);
		List<MypageDTO> reviewDtoList = reviewService.findAllMypageByUsername(username);
		List<MypageDTO> qnaDtoList = qnAService.findAllMypageByUsername(username);

		// 모든 글 목록을 하나의 리스트로 합침
		List<MypageDTO> list = Stream.of(boardDtoList, reviewDtoList, qnaDtoList)
				// 각 게시판의 목록을 하나의 스트림으로 변환
				// Collection::stream - 각 게시판의 목록을 스트림으로 변환하는 함수
				.flatMap(Collection::stream)
				// 변환된 스트림을 하나의 리스트로 모음
				.collect(Collectors.toList());

		// 시간순 정렬
		// Comparator를 사용하여 MypageDTO의 updateDate를 기준으로 정렬하고, 내림차순으로 정렬함 (최신 글이 상위에 위치)
		list.sort(Comparator.comparing(MypageDTO::getUpdateDate).reversed());

		// (시작)페이징 처리
		int start = size * (page - 1);
		// (끝)페이지가 리스트의 크기를 넘지 않도록 최소값 설정
		int end = Math.min(size * page, list.size());

		// 리스트에서 시작 인덱스 ~ 끝 인덱스 반환
		return list.subList(start, end);
	}
	
	
	// username으로 찾은 댓글 목록 페이징 처리
	public List<MypageDTO> findAllMypageRepliesByUsernameWithPaging(String username, int page, int size) {
	    List<MypageDTO> replyDtoList = replyService.findAllMypageRepliesByUsername(username);

	    // 시간순 정렬
	    replyDtoList.sort(Comparator.comparing(MypageDTO::getCreateDate).reversed());

	    // 페이징 처리
	    int start = size * (page - 1);
	    int end = Math.min(size * page, replyDtoList.size());

	    return replyDtoList.subList(start, end);
	}


}
