package kr.co.tj.board;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PageTO<T> {
	private final Page<T> page;
	private int begin;
	private int end;
	
		public PageTO(Page<T> page) {
		this.page = page;
		calBeginAndEnd();
	}
	
	
	public void calBeginAndEnd() { 
		begin = (page.getNumber()/10)*10;
		end = begin + 9;
		
	if(end > page.getTotalPages()-1) {
		end = page.getTotalPages()-1;
	}
	}





}
