package kr.co.tj.mypage;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mypage")
public class MypageController {

	@Autowired
	private MypageService mypageService;

	// 내가 쓴 글 불러오기
	@GetMapping("")
	// 테스트 할때는 principal 빼고 했습니다.
	public ResponseEntity<?> findMyList(Principal principal, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		Map<String, Object> map = new HashMap<>();

		try {
			// 로그인해야 이용 가능한 서비스
			String username = principal.getName();

			// 각 게시판에 자신이 쓴 글 username으로 찾아서 불러오기
			List<MypageDTO> dto = mypageService.findAllMypageByUsernameWithPaging(username, page, size);

			map.put("result", dto);

			return ResponseEntity.ok(map);

			// 로그인 되지 않은 사용자일 경우
		} catch (NullPointerException e) {
			e.printStackTrace();
			map.put("error", "로그인이 필요한 서비스입니다.");
			return ResponseEntity.badRequest().body(map);
			// 다른 예외처리
		} catch (Exception e) {
			map.put("error", "오류발생. 다시 시도해 주세요.");
			return ResponseEntity.badRequest().body(map);
		}
	}

	// 내가 쓴 댓글 불러오기
	@GetMapping("/reply")
	public ResponseEntity<?> findMyReply(Principal principal,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		Map<String, Object> map = new HashMap<>();

		try {
			// 로그인해야 이용 가능한 서비스
			String username = principal.getName();

			// 사용자가 쓴 댓글 목록 불러오기
			List<MypageDTO> dto = mypageService.findAllMypageRepliesByUsernameWithPaging(username, page, size);

			map.put("result", dto);

			return ResponseEntity.ok(map);

			// 로그인 되지 않은 사용자일 경우
		} catch (NullPointerException e) {
			e.printStackTrace();
			map.put("error", "로그인이 필요한 서비스입니다.");
			return ResponseEntity.badRequest().body(map);
			// 다른 예외처리
		} catch (Exception e) {
			map.put("error", "오류발생. 다시 시도해 주세요.");
			return ResponseEntity.badRequest().body(map);
		}
	}

}
