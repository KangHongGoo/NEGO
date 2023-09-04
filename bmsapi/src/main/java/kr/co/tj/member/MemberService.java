package kr.co.tj.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import kr.co.tj.sec.TokenProvider;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	

	public MemberDTO insert(MemberDTO dto) {
	
		MemberEntity entity = dto.toMemberEntity(dto);
		
		Date date = new Date();
		entity.setCreateDate(date);
		entity.setUpdateDate(date);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));

		entity = memberRepository.save(entity);
		
		dto = dto.toMemberDTO(entity);
		dto.setId(null);
		dto.setPassword(null);
		
	//	return new ModelMapper().map(entity, MemberDTO.class);
		return dto;
	}

	public MemberDTO findByUsername(String username) {
		MemberEntity entity = memberRepository.findByUsername(username);

		MemberDTO dto = new ModelMapper().map(entity, MemberDTO.class);
		dto.setId(null);
		dto.setPassword(null);
		
		return dto;
	}

	@Transactional
	public MemberDTO update(MemberDTO dto) {
		MemberEntity entity = memberRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());

		if (entity == null) {
			throw new RuntimeException("잘못된 정보입니다.");
		}

		entity.setName(dto.getName());
		entity.setUpdateDate(new Date());

		entity = memberRepository.save(entity);

		//dto = new ModelMapper().map(entity, MemberDTO.class);

		dto.toMemberDTO(entity);
		dto.setId(null);
		dto.setPassword(null);
		return dto;
	}

	public List<MemberDTO> findAll() {

		List<MemberEntity> list_entity = memberRepository.findAll();

		List<MemberDTO> list_dto = new ArrayList<>();

		list_entity.forEach(e -> {
			list_dto.add(new ModelMapper().map(e, MemberDTO.class));
		});

		return list_dto;
	}

	public void delete(MemberDTO dto) {
		MemberEntity entity = memberRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
		
		if(entity == null) {
			throw new RuntimeException(" X ");
		}
		
		
		memberRepository.delete(entity);
	}

	public MemberDTO login(MemberDTO dto) {
		MemberEntity member = getByCredentials(dto.getUsername());
		
		if(member == null ) {
			throw new RuntimeException("회원 정보 오류 로그인 거부");
		}
		
		
		if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
			throw new RuntimeException("패스워드 오류 로그인 거부");
		}
		
		String token = tokenProvider.create(member);
		
		dto = new ModelMapper().map(member, MemberDTO.class);
		
		dto.setToken(token);
		dto.setId(null);
		dto.setPassword(null);
		
		
		return dto;
	}
	
	
	public MemberEntity getByCredentials(String username) {
		return memberRepository.findByUsername(username);
	}
	
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


