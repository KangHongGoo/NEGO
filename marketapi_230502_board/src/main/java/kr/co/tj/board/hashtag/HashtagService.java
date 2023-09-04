package kr.co.tj.board.hashtag;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tj.board.BoardRepository;

@Service
public class HashtagService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private HashtagRepository  hashtagRepository;

	
	
}
