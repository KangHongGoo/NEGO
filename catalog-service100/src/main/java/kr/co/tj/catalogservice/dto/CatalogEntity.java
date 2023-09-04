package kr.co.tj.catalogservice.dto;

import java.io.Serializable;
import java.util.Date;

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

@Data
@Entity
@Table(name = "catalogs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogEntity implements Serializable{
	
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String productid;
	
	@Column(nullable = false)
	private String productname;
	
	@Column(nullable = false)
	private Long stock;
	
	@Column(nullable = false)
	private Long unitPrice;
	private Date createAt;
	
	
	
}
