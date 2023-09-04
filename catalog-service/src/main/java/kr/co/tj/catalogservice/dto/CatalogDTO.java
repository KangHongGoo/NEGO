package kr.co.tj.catalogservice.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	private String productId;
	

	private String productname;
	

	private Long stock;
	

	private Long unitPrice;
	
	
	private Date createAt;


	public static CatalogDTO toCatalogDTO(CatalogRequest catalogRequest) {
		
		return CatalogDTO.builder()
				.productname(catalogRequest.getProductname())
				.stock(catalogRequest.getStock())
				.unitPrice(catalogRequest.getUnitPrice())
				.build();
	}


	public CatalogResponse toCatalogResponse() {
	
		return CatalogResponse.builder()
				.productId(productId)
				.productname(productname)
				.stock(stock)
				.unitPrice(unitPrice)
				.createAt(createAt)
				.build();
	}
	
	public CatalogEntity toCatalogEntity() {
		
		return CatalogEntity.builder()
				.productId(productId)
				.productname(productname)
				.stock(stock)
				.unitPrice(unitPrice)
				.createAt(createAt)
				.build();
	}

	
	
	

}
