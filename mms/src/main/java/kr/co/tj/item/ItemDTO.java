package kr.co.tj.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ItemDTO {
	
	
	private Integer id; 
	
	private String codeOfItem;
	
	private String nameOfItem;
	
	private String infoOfItem;
	
	private Integer priceOfItem;


}
