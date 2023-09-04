package kr.co.tj.catalogservice.dto;






import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogRequest {
	
		
	private String productname;

	private Long stock;

	private Long unitPrice;
	
	
	
	

}
