package kr.co.tj.staff;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
	
	@Autowired
	private StaffRepository staffRepository;
	
	public List<StaffDTO> findAll() {
		List<StaffEntity> list_entity = staffRepository.findAll();
		
		List<StaffDTO> list_dto = new ArrayList<>();
		
		list_entity.forEach(e->{
			list_dto.add(new ModelMapper().map(e, StaffDTO.class));
		});
		
		return list_dto;
	}
	
	

	public StaffDTO findByUsername(String username) {
		
		StaffEntity entity = staffRepository.findByUsername(username);
		
		if(entity == null) {
			throw new RuntimeException("해당 이름은 존재하지 않습니다.");
		}
		
		return new ModelMapper().map(entity, StaffDTO.class);
	}



	public StaffDTO save(StaffDTO staffDTO) {
		
		StaffEntity staffEntity = new ModelMapper().map(staffDTO, StaffEntity.class);
		
		Date date = new Date();
		staffEntity.setCreateDate(date);
		staffEntity.setUpdateDate(date);
		
		staffEntity = staffRepository.save(staffEntity);
		
		
		return new ModelMapper().map(staffEntity, StaffDTO.class);
	}



	public StaffDTO findByUsernameAndPassword(String username, String orgPassword) {
		StaffEntity entity= staffRepository.findByUsernameAndPassword(username, orgPassword);
		
		return new ModelMapper().map(entity, StaffDTO.class);
	}



	@Transactional
	public StaffDTO update(StaffDTO staffDTO) {
		StaffEntity entity = new ModelMapper().map(staffDTO, StaffEntity.class);
		
		entity.setUpdateDate(new Date());
		
		entity = staffRepository.save(entity);
		
		return new ModelMapper().map(entity, StaffDTO.class);	
	}


	@Transactional
	public void delete(String username, String password) {
		StaffEntity entity =  staffRepository.findByUsernameAndPassword(username, password);
		
		if(entity == null) {
			throw new RuntimeException("해당 스 탭은 없 습니다.");
		}
		
		staffRepository.delete(entity);
		
	}
	
	public void delete(StaffDTO staffDTO) {
		StaffEntity entity = staffRepository.findByUsernameAndPassword(staffDTO.getUsername(), staffDTO.getPassword());
		
		if(entity == null) {
			throw new RuntimeException(" X ");
		}
		
		staffRepository.delete(entity);
	}


	
}
