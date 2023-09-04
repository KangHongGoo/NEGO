package kr.co.tj.board;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.tj.DataNotFoundException;
import kr.co.tj.reply.ReplyDTO;
import kr.co.tj.reply.ReplyService;


@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	@Autowired
	private ReplyService replyService;
	 
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, Model model) {
		BoardDTO dto = boardService.getId(id);
		
		model.addAttribute("dto", dto);
		
		return "board/delete";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/delete")
	public String delete(Integer id,Principal principal,RedirectAttributes rttr) {
		
		BoardDTO dto = boardService.getId(id);
		
		
		
		if(dto != null) {
			
			boolean isTrue = dto.getNickName().equals(principal.getName());
			if(!isTrue) {
				rttr.addFlashAttribute("err", "권한이 없습니다");
				return "redirect:/member/login";
			}
 		
		boardService.delete(id);
		}else {
			throw new DataNotFoundException("해당 글은 없습니다.");
		}
		return "redirect:/";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/update/{id}")
	public String update(@PathVariable("id") Integer id, Model model,Principal principal, RedirectAttributes rttr) {
		BoardDTO dto = boardService.getId(id);
		if(dto.getNickName().equals(principal.getName())) {
		model.addAttribute("dto", dto);
		return "board/update";
	}else {
		rttr.addFlashAttribute("err", "권한이 없습니다.");
		
		return "redirect:/";
	}
		
		
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update")
	public String update(BoardDTO dto, Principal principal, RedirectAttributes rttr) {
		
		boolean isTrue = dto.getNickName().equals(principal.getName());
		if(!isTrue) {
			rttr.addFlashAttribute("err", "권한이 없습니다");
			return "redirect:/member/login";
		}
		
		boardService.update(dto);
		
		
		 
		 return "redirect:/board/read/" + dto.getId();
	}
	
	
	@GetMapping("/read/{id}")
	public String read(@PathVariable("id") Integer id, Model model) {
		
		BoardDTO dto = boardService.getId(id);
		
		dto.setContent(dto.getContent().replace("\n", "<br>"));
		
		model.addAttribute("dto", dto);
		
		/*
		 * List<ReplyDTO> reply = replyService.findByBid(id);
		 * 
		 * model.addAttribute("reply", reply);
		 */
		
		return "board/read";
	}
	
	
	
	
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/write")
	public String write() { 
		return "board/write";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/write")
	public String write(BoardForm boardForm,Principal principal) {
		BoardDTO dto = boardService.save(boardForm, principal.getName());
		return "redirect:/";
	}
	
	
	@GetMapping("/list")
	public String list(Model model, Integer pagenum) {
		if(pagenum == null) {
			pagenum = 1;
		}
		pagenum = pagenum -1;

		Page<BoardDTO> page = boardService.findAll(pagenum);
		PageTO<BoardDTO> pt = new PageTO<>(page);
		
		model.addAttribute("page", page);
		model.addAttribute("pt", pt);
		
		return "board/list";
	}

}
