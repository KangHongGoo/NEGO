package kr.co.tj.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public List<MemberDTO> findAll() {
		
		List<MemberEntity> list_entity = memberRepository.findAll();
		List<MemberDTO> list_dto = new ArrayList<>();
		
		for(MemberEntity entity : list_entity) {
			list_dto.add(new ModelMapper().map(entity, MemberDTO.class));
		}
		
		return list_dto;
	}

	public MemberDTO findByUsername(String username) {
		MemberEntity entity = memberRepository.findByUsername(username);
		
		return new ModelMapper().map(entity, MemberDTO.class);
	}

	public MemberDTO update(MemberDTO memberDTO) {
		MemberEntity entity = new ModelMapper().map(memberDTO, MemberEntity.class);
		
		entity = memberRepository.save(entity);
		
		entity.setUpdateDate(new Date());
		return new ModelMapper().map(entity, MemberDTO.class);
	}

	public void delete(MemberDTO memberDTO) {
		MemberEntity entity = memberRepository.findByUsernameAndPassword(memberDTO.getUsername(), memberDTO.getPassword());
		
		memberRepository.delete(entity);
		
		
	}

	public MemberDTO findByUsernameAndPassword(String username, String orgPassword) {
		MemberEntity entity = memberRepository.findByUsernameAndPassword(username, orgPassword);
		
		return new ModelMapper().map(entity, MemberDTO.class);
		
	}

	public MemberDTO save(MemberDTO memberDTO) {
		
		MemberEntity entity = new ModelMapper().map(memberDTO, MemberEntity.class);
		
		Date date = new Date();
		entity.setCreateDate(date);
		entity.setUpdateDate(date);
		
		entity = memberRepository.save(entity);
		
		return new ModelMapper().map(entity, MemberDTO.class);
		 
		 
		
	}


	
}
