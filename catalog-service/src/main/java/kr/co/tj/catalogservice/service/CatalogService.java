package kr.co.tj.catalogservice.service;

import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tj.catalogservice.dto.CatalogDTO;
import kr.co.tj.catalogservice.dto.CatalogEntity;
import kr.co.tj.catalogservice.jpa.CatalogRepository;

@Service
public class CatalogService {
	
	@Autowired
	private CatalogRepository catalogRepository;

	public CatalogDTO createCatalog(CatalogDTO catalogDTO) {
		
		String productId = UUID.randomUUID().toString();
		
		catalogDTO.setProductId(productId);
		
		catalogDTO.setCreateAt(new Date());
		
		CatalogEntity catalogEntity = catalogDTO.toCatalogEntity();
		
		catalogEntity = catalogRepository.save(catalogEntity);
		
		return catalogDTO;
	}

	public CatalogEntity getCatalogByProductId(String productId) {
		
		return catalogRepository.findByProductId(productId);
	}

	@Transactional
	public String updateStockByProductId(CatalogEntity catalogEntity) {
		
		try {
			catalogRepository.save(catalogEntity);
			
			return "ok";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	
	}

}
