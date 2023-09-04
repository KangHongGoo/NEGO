package kr.co.tj.boardservice.dto;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;

}
