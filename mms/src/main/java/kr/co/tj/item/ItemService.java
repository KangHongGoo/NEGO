package kr.co.tj.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;

	
	public ItemDTO addItem(ItemDTO dto) {
		Item item = new Item.ItemBuilder()
				.codeOfItem(dto.getCodeOfItem())
				.nameOfItem(dto.getNameOfItem())
				.infoOfItem(dto.getInfoOfItem())
				.priceOfItem(null)
				.build();
		
		item = itemRepository.save(item);
		
		return new ModelMapper().map(item, ItemDTO.class);
		
		
	}
	


	public ItemDTO findById(Integer id) {
		Optional<Item> optional = itemRepository.findById(id);
		
		if(!optional.isPresent()) {
		return null;
	}
	 return new ModelMapper().map(optional.get(), ItemDTO.class);	
	}


	public ItemDTO update(ItemDTO dto_db) {
		Item item = new ModelMapper().map(dto_db, Item.class);
		
		item =itemRepository.save(item);
		dto_db = new ModelMapper().map(item, ItemDTO.class);
		
		return dto_db;
		
	
	}


	public void delete(Integer id) {
		itemRepository.deleteById(id);
		
	}
	public List<ItemDTO> findAll() {
		List<Item> list = itemRepository.findAll();
		List<ItemDTO> list2 = new ArrayList<>();
		
		for(Item x : list) {
			list2.add(new ModelMapper().map(x, ItemDTO.class));
		}
		return list2;
	}

}
	