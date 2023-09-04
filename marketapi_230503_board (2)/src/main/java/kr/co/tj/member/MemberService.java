package kr.co.tj.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.tj.se.TokenProvider;


@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	//회원가입
	public MemberDTO save(MemberDTO dto) {
		
		MemberEntity entity = new ModelMapper().map(dto, MemberEntity.class);
		
		Date date = new Date();
		
		entity.setCreateDate(date);
		entity.setUpdateDate(date);
		entity.setPassword(passwordEncoder.encode(dto.getPassword())); //페스워드 암호화
		
		entity = memberRepository.save(entity);
		
		return new ModelMapper().map(entity, MemberDTO.class);
	}

	public MemberDTO FindByUsername(String username) {


		MemberEntity entity = memberRepository.findByUsername(username);
		
		MemberDTO dto = new ModelMapper().map(entity, MemberDTO.class);
		
		return dto;
	}

	public List<MemberDTO> FindAll() {
		List<MemberEntity> list = memberRepository.findAll();
		List<MemberDTO> list_dto = new ArrayList<>();
		for(MemberEntity x : list) {
			list_dto.add(MemberDTO.toDto(x));
		}
		return list_dto;
	}

	public MemberDTO update(MemberDTO dto) {
		MemberEntity entity = memberRepository.findByUsername(dto.getUsername());
		
		if(entity==null) {
			throw new RuntimeException("DB에 저장된 유저가 없습니다");
		}
		if(!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
			throw new RuntimeException("아이디또는 비밀번호가 틀렸습니다.22222");
		}
		
		entity.setPassword(passwordEncoder.encode(dto.getOrgPassword()));
		entity.setUpdateDate(new Date());
		entity.setNickname(dto.getNickname());
		
		entity = memberRepository.save(entity);
		
		return dto;
	}

	public MemberDTO login(MemberDTO dto) {
		MemberEntity entity = getByCredentials(dto.getUsername());
		
		if(entity == null) {
			throw new RuntimeException("아이디또는 비밀번호가 틀렸습니다.111");
		}
		
		if(!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
			throw new RuntimeException("아이디또는 비밀번호가 틀렸습니다.22222");
		}
		
		String token = tokenProvider.create(entity);
		
		dto = new ModelMapper().map(entity, MemberDTO.class);
		
		dto.setToken(token);
		
		dto.setId(null);
		dto.setPassword(null);
		
		return dto;
	}

	private MemberEntity getByCredentials(String username) {
		
		return memberRepository.findByUsername(username);
	}

	public MemberDTO findByToken(String username) {
		MemberEntity entity = memberRepository.findByUsername(username);
		String token = tokenProvider.create(entity);
		
		MemberDTO dto = new ModelMapper().map(entity, MemberDTO.class);
		
		dto.setToken(token);
		
		return dto;
	}


	
	
}
