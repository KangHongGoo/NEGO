package kr.co.tj.catalogservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.co.tj.catalogservice.dto.CatalogDTO;
import kr.co.tj.catalogservice.dto.CatalogEntity;
import kr.co.tj.catalogservice.jpa.CatalogRepository;

@Service
public class CatalogService {
	
	@Autowired
	private CatalogRepository catalogRepository;

	@Transactional
	public CatalogDTO createCatalog(CatalogDTO catalogDTO) {
		
		String productId = UUID.randomUUID().toString();
		catalogDTO.setProductid(productId);
		
		Date date = new Date();
		catalogDTO.setCreateAt(date);
		
		
		
		CatalogEntity catalogEntity = catalogDTO.toCatalogEntity();
		
		catalogEntity = catalogRepository.save(catalogEntity);
		
		
		catalogDTO = CatalogDTO.toCatalogDTO(catalogEntity);
	
		
		
		
		return catalogDTO;
	}
	
	public CatalogEntity getCatalogByProductId(String productid) {
		return catalogRepository.findByProductid(productid);
	}

	
	
	public String updateStockByProductId(CatalogEntity catalogEntity) {
		
		 try {
			catalogRepository.save(catalogEntity);
			 
			 return "ok";
		} catch (Exception e) {
			
			e.printStackTrace();
			return "fail";
		}
		
	}

	public List<CatalogDTO> listPage(Integer page, int maxNum) {
		Sort sort = Sort.by("createAt").descending();
		Pageable pageable = PageRequest.of(page, maxNum, sort);
		
		Page<CatalogEntity> page_catalog = catalogRepository.findAll(pageable);
		List<CatalogEntity> list_entity = page_catalog.getContent();
		
		List<CatalogDTO> list = new ArrayList<>();
		
		list_entity.forEach(entity -> {
			CatalogDTO dto = CatalogDTO.toCatalogDTO(entity);
			list.add(dto);
		});
		
		return list;
	}

	public CatalogDTO findCatalogByProductid(String productid) {
		
		CatalogEntity entity = catalogRepository.findByProductid(productid);
		
		CatalogDTO dto = CatalogDTO.toCatalogDTO(entity);
		return dto;
	}

	
	@Transactional
	public void updateCatalog(CatalogDTO catalogDTO) {
		CatalogEntity entity = catalogDTO.toCatalogEntity();
		catalogRepository.save(entity);
		
	}

	public void deleteCatalog(CatalogDTO catalogDTO) {
		// TODO Auto-generated method stub
		CatalogEntity entity = catalogDTO.toCatalogEntity();
		catalogRepository.delete(entity);
		
	}

}
