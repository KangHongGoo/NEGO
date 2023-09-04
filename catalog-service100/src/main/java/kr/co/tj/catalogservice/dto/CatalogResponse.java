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
public class CatalogResponse implements Serializable{
	
		
	private static final long serialVersionUID = 1L;
	
	private int status;

	private String productid;

	private String productname;

	private Long stock;

	private Long unitPrice;
	
	private Date createAt; 
	
	

}
