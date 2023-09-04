package kr.co.tj.catalogservice.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.catalogservice.dto.CatalogDTO;
import kr.co.tj.catalogservice.dto.CatalogEntity;
import kr.co.tj.catalogservice.dto.CatalogRequest;
import kr.co.tj.catalogservice.dto.CatalogResponse;
import kr.co.tj.catalogservice.dto.OrderResponse;
import kr.co.tj.catalogservice.service.CatalogService;



@RestController
@RequestMapping("/catalog-service")
public class CatalogController {
	
	@Autowired
	private CatalogService catalogService;
	
	@GetMapping("/catalogs/{productid}")
	public ResponseEntity<?> getOrdersByUsername(@PathVariable String productid) {

		CatalogEntity entity = catalogService.getCatalogByProductId(productid);

		return ResponseEntity.status(HttpStatus.OK).body(entity);
	}
	
	@PutMapping("/catalogs/productid")
	public ResponseEntity<?> updateStockByProductId(@RequestBody OrderResponse orderResponse) {
		
		CatalogEntity catalogEntity = catalogService.getCatalogByProductId(orderResponse.getProductId());
		
		if(catalogEntity == null ) {
			return ResponseEntity.status(HttpStatus.OK).body("0: 없는 상품입니다.");
		}
		
		if(catalogEntity.getStock() < orderResponse.getQty()) {
			return ResponseEntity.status(HttpStatus.OK).body("0: 재고가 부족합니다");
		}
		
		
		
		catalogEntity.setStock(catalogEntity.getStock() - orderResponse.getQty());
		
		String result;
		
		try {
			result = catalogService.updateStockByProductId(catalogEntity);
			
			if (result.equalsIgnoreCase("ok")) {
				return ResponseEntity.status(HttpStatus.OK).body("1: 성공");
			}else {
				return ResponseEntity.status(HttpStatus.OK).body("0: 재고 갱신 실패");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.OK).body("0: 재고 갱신 실패");
		}
		
		
		
		
	}
	
	@PostMapping("/catalogs")
	public ResponseEntity<?> createProduct(@RequestBody CatalogRequest catalogRequest) {
		
		CatalogDTO catalogDTO = CatalogDTO.toCatalogDTO(catalogRequest);
		
		catalogDTO = catalogService.createCatalog(catalogDTO);
		CatalogResponse catalogResponse = catalogDTO.toCatalogResponse();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(catalogResponse);
	}
	
	@GetMapping("/health_check")
	public String status() {
		return "user service입니다";
	}

}
