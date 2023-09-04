package kr.co.tj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {
	

	@GetMapping("/insert")
	public String insert() {
		return "board/insert";
	}
}
