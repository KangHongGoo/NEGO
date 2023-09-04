package kr.co.tj.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.tj.DataNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	
	@Autowired
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	public MemberDTO save(MemberForm memberForm) {
			
		Member member = new Member();
		member.setNickName(memberForm.getNickName());
		member.setName(memberForm.getName());
		member.setPassword(passwordEncoder.encode(memberForm.getPassword1()));
		member.setRole("usr");
		
		memberRepository.save(member);
		
		return new ModelMapper().map(member, MemberDTO.class);
	}
	
	
	public MemberDTO create(String nickName, String name, String password) {
		Member member = new Member();
		member.setNickName(nickName);
		member.setName(name);
		member.setPassword(passwordEncoder.encode(password));
		
		memberRepository.save(member);
		
		return new ModelMapper().map(member, MemberDTO.class);
		
	}
	
	public MemberDTO getMember(String nickName) {
		Optional<Member> optional =  memberRepository.findByNickName(nickName);
		
		if(optional.isPresent()) {
			Member mem = optional.get();
			
			return new ModelMapper().map(mem, MemberDTO.class);
		}
		return null;
		
		
	}


	public List<MemberDTO> findAll() {
		List<Member> list = memberRepository.findAll();
		
		List<MemberDTO> list2 = new ArrayList<>();
		
		
			for(int i = 0; i <list.size();i++) {
				Member member = list.get(i);
			MemberDTO dto = new ModelMapper().map(member, MemberDTO.class);
			list2.add(dto);
			}
			return list2;
		}

	@Transactional
	public MemberDTO save(MemberDTO dto) {
		
		Optional<Member> optional = memberRepository.findByNickName(dto.getNickName());
		
		if(optional.isEmpty()) {
			return null;
			
		}
		Member member = optional.get();
		
		boolean isMatched = passwordEncoder.matches(dto.getPassword(), member.getPassword());
		
		if(isMatched) {
			member.setName(dto.getName());
			
			member = memberRepository.save(member);
			
			return new ModelMapper().map(member, MemberDTO.class);
			
		}
		
		
		
		
		
		return null;
	}


	public void delete(MemberDTO dto) {
		Optional<Member> optional = memberRepository.findByNickName(dto.getNickName());
		
		if(optional.isEmpty()) {
			throw new DataNotFoundException("해당 회원은 없습니다.");
			
		
		}
		
		Member member = optional.get();
		
		boolean isMatched = passwordEncoder.matches(dto.getPassword(), member.getPassword());
		
		if(isMatched) {
			
			memberRepository.delete(member);
		}
	}
	


	}
	 	
		
	

	
	

