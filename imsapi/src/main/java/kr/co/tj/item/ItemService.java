package kr.co.tj.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	public ItemDTO findById(long id) {
		Optional<ItemEntity> optional = itemRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("해당 아이템은 없습니다.");
		}
		
		ItemEntity itemEntity = optional.get();
		
		ItemDTO itemDTO = new ModelMapper().map(itemEntity, ItemDTO.class);
		
		int discount = itemDTO.getDiscount();
		long price = itemDTO.getPrice();
		
		long salePrice = price * (100-discount) / 100;
		
		itemDTO.setSalePrice(salePrice);
		
		
		return itemDTO;
	}

	public List<ItemDTO> findAll() {
		List<ItemEntity> list_entity = itemRepository.findAll();
		
		List<ItemDTO> list_dto = new ArrayList<>();
		
		for(ItemEntity e : list_entity) {
			list_dto.add(new ModelMapper().map(e, ItemDTO.class));
		}
		
		// list_entity.forEach(e -> list_dto.add(new ModelMapper().map(e, ItemDTO.class)) );
		// 위의 for(ItemEntity e: list_e~ 코드와 똑같음.
		
		return list_dto;
	}

	public List<ItemDTO> findByStaff(String staff) {
		
		List<ItemEntity> list_entity = itemRepository.findByStaff(staff);
		List<ItemDTO> list_dto = new ArrayList<>();
		
		list_entity.forEach(e -> list_dto.add(   new ModelMapper().map(e, ItemDTO.class)  )   );
//		for(ItemEntity e : list_entity) {
//			list_dto.add(new ModelMapper().map(e, ItemDTO.class));
//		}
		
		return list_dto;
	}

	public ItemDTO save(ItemDTO itemDTO) {
		
		ItemEntity itemEntity = new ModelMapper().map(itemDTO, ItemEntity.class);
		
		Date date = new Date();
		
		itemEntity.setCreateDate(date);
		itemEntity.setUpdateDate(date);
				
		itemEntity = itemRepository.save(itemEntity);
		
		return new ModelMapper().map(itemEntity, ItemDTO.class);
	}

	@Transactional
	public ItemDTO update(ItemDTO itemDTO) {
		// itemDTO에는 createDate에 대한 정보가 없음.
		// 그래서 이 정보를 넣어주기 위해서 db에 있는 데이터를 가져와야 함.
		//본 클래스(ItemService클래스)에 있는 findById()메서드를 이용해서 db로부터 dto객체를 가져옴.
		ItemDTO dto_db = findById(itemDTO.getId());
		
		
		
		ItemEntity item_entity = new ModelMapper().map(itemDTO, ItemEntity.class);
		
		//item_entity에는 createDate에 대한 정보가 없음.
		// dto_db에 들어 있는 createDate 정보를 item_entity의 createDate에 값으로 설정함.
		item_entity.setCreateDate(dto_db.getCreateDate());
		
		Date date = new Date();
		item_entity.setUpdateDate(date);
	
		
		item_entity = itemRepository.save(item_entity);
		itemDTO = new ModelMapper().map(item_entity, ItemDTO.class);
		
		return itemDTO;
	}
	
	@Transactional
	public void delete(ItemDTO itemDTO) {
		ItemEntity itemEntity = new ModelMapper().map(itemDTO, ItemEntity.class);
		
		itemRepository.delete(itemEntity);
		
	}





}
