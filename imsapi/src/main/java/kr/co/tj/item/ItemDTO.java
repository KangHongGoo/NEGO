package kr.co.tj.item;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {
	private long id;
	
	private String itemName;
	
	private long price;
	
	private int discount;
	
	private long salePrice;
	
	private int ea;
	
	private String itemDescribe;
	
	private String staff;
	
	private Date createDate;
	
	
	private Date updateDate;


	
	

}
