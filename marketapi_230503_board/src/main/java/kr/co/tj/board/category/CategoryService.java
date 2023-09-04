package kr.co.tj.board.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryDTO insert(CategoryDTO dto) {
		
		CategoryEntity entity = new ModelMapper().map(dto, CategoryEntity.class);
		
		entity = categoryRepository.save(entity);
		
		return new ModelMapper().map(entity, CategoryDTO.class);
	}

	public CategoryDTO update(CategoryDTO dto) {
		
		Optional<CategoryEntity> optional = categoryRepository.findById(dto.getId());
		
		if(!optional.isPresent()) {
			throw new RuntimeException("X");
		}
		
		CategoryEntity entity = new CategoryEntity.CategoryEntityBuilder()
				.id(dto.getId())
				.name(dto.getName())
				.build();
		
		entity = categoryRepository.save(entity);
		dto.setId(entity.getId());
		
		return dto;
	}

	public void delete(CategoryDTO dto) {
		Optional<CategoryEntity> optional = categoryRepository.findById(dto.getId());
		
		if(optional.isPresent()) {
			CategoryEntity entity = optional.get();
			categoryRepository.delete(entity);
		}else {
			throw new RuntimeException("오류가 발생했습니다");
		}
		
		
		
	}

	public List<CategoryDTO> getAll() {
		List<CategoryEntity> list_entity = categoryRepository.findAll();
		List<CategoryDTO> list_dto = new ArrayList<>();
		
		for(CategoryEntity x : list_entity) {
			list_dto.add(CategoryDTO.toDto(x));
		}
		return list_dto;
	}

	public CategoryDTO getById(Long id) {
		Optional<CategoryEntity> optional = categoryRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("X");
		}
		
		CategoryEntity entity = optional.get();
		
		
		
		return new ModelMapper().map(entity, CategoryDTO.class);
	}

}
