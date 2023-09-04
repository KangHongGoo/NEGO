package kr.co.tj.member;



import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	private EntityManager entityManager;

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
		 
	
		
		
		
		return dto;
	}

	public MemberDTO findByUsername(String username) {
		MemberEntity entity = memberRepository.findByUsername(username);
		
		if(entity == null) {
			throw new RuntimeException("해당 정보는 존재하지 않습니다");
		}
		
		MemberDTO dto = new MemberDTO.MemberDTOBuilder()
				.id(entity.getId())
				.username(entity.getUsername())
				.name(entity.getName())
				.createDate(entity.getCreateDate())
				.updateDate(entity.getUpdateDate())
				.password(entity.getPassword())
				.build();
		
		return dto;
	}
	
	 @Transactional
     public MemberDTO update(MemberDTO dto) {
        
         MemberEntity entity = memberRepository.findByUsername(dto.getUsername());

         if(entity == null) {
            System.out.println(dto);
              throw new RuntimeException("해당 정보는 존재하지 않습니다");
           } 
         
          String rawPassword = entity.getPassword();
      
          boolean isMatched = new BCryptPasswordEncoder().matches(dto.getPassword(), rawPassword);
          
          if(isMatched == false) {
             System.out.println(dto);
             throw new RuntimeException("잘못된 정보입니다.");
          }else {
          
           Date date = new Date();
           entity.setName(dto.getName());
           entity.setUpdateDate(date);
           
           
           entity = memberRepository.save(entity);
           
           
           ModelMapper modelMapper = new ModelMapper();
           MemberDTO updatedDto = modelMapper.map(entity, MemberDTO.class);
           updatedDto.setId(null);
           updatedDto.setPassword(null);
           
           
           return updatedDto;

          }

          
           
           
     }

	public void delete(MemberDTO dto) {
		MemberEntity entity = memberRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
		
		
		if(entity == null) {
			throw new RuntimeException(" X ");
		}
		
		
		memberRepository.delete(entity);
		
	}

	public MemberDTO findByUsernameAndPassword(String username, String orgPassword) {
		MemberEntity entity = memberRepository.findByUsernameAndPassword(username, orgPassword);
		
		
		return new ModelMapper().map(entity, MemberDTO.class);
	}

	public MemberDTO login(MemberDTO dto) {
		MemberEntity member = getByCredentials(dto.getUsername());
		
		if(member == null) {
			throw new RuntimeException("패스워드 오류로 인해 로그인 거부");
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
