package kr.co.tj.board.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
	
	private long id;
	
	private String name;

	public static CategoryDTO toDto(CategoryEntity entity) {
		return CategoryDTO.builder()
		.id(entity.getId())
		.name(entity.getName())
		.build();
		
	}
}
