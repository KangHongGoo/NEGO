package kr.co.tj.item;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "item")
public class ItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String itemName;
	
	@Column(nullable = false)
	private long price;
	
	private int discount;
	
	@Column(nullable = false)
	private int ea;
	
	@Column(nullable = false)
	private String itemDescribe;
	
	@Column(nullable = false)
	private String staff;
	
	private Date createDate;
	
	
	private Date updateDate;

}
