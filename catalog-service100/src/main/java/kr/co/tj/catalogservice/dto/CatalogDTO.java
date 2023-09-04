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
	
	private Long id;

	private String productid;

	private String productname;

	private Long stock;

	private Long unitPrice;
	
	private Date createAt;
	
	public static CatalogDTO toCatalogDTO(CatalogRequest creq) {
		
		return CatalogDTO.builder()
				
				.productname(creq.getProductname())
				.stock(creq.getStock())
				.unitPrice(creq.getUnitPrice())
				.build();
	}
	


	public CatalogResponse toCatalogResponse() {
		
		
		
		
		return CatalogResponse.builder()
				.productid(productid)
				.productname(productname)
				.stock(stock)
				.unitPrice(unitPrice)
				.createAt(createAt)
				.build();
	}


	public CatalogEntity toCatalogEntity() {
		return CatalogEntity.builder()
				.id(id)
				.productid(productid)
				.productname(productname)
				.stock(stock)
				.unitPrice(unitPrice)
				.createAt(createAt)
				.build();
	}


//	public CatalogDTO toCatalogDTO(CatalogEntity catalogEntity) {
//		this.productId = catalogEntity.getProductid();
//				
//		return this;
//	}
	
	public static CatalogDTO toCatalogDTO(CatalogEntity catalogEntity) {
		return CatalogDTO.builder()
				.id(catalogEntity.getId())
				.createAt(catalogEntity.getCreateAt())
				.productid(catalogEntity.getProductid())
				.productname(catalogEntity.getProductname())
				.stock(catalogEntity.getStock())
				.unitPrice(catalogEntity.getUnitPrice())
				.build();
	}


}
