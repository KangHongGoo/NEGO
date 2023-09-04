package kr.co.tj.catalogservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	private Environment env;
	
	@Autowired
	private CatalogService catalogService;
	
	@PostMapping("/fileupload")
	public ResponseEntity<?> fileupload(MultipartHttpServletRequest mRequest) {
		String id = mRequest.getParameter("id");
		System.out.println(id);
		
		MultipartFile mFile = mRequest.getFile("file");
		System.out.println(mFile.getOriginalFilename());
		return ResponseEntity.ok("hello");
	}
	
	@DeleteMapping("/catalogs")
	public ResponseEntity<?> deleteCatalog(@RequestBody CatalogDTO catalogDTO) {
		catalogDTO = catalogService.findCatalogByProductid(catalogDTO.getProductid());
		
		if(catalogDTO == null) {
			return ResponseEntity.status(HttpStatus.OK).body(CatalogResponse.builder().status(0).build());
		}
		
		try {
			catalogService.deleteCatalog(catalogDTO);
			return ResponseEntity.status(HttpStatus.OK).body(CatalogResponse.builder().status(1).build());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body(CatalogResponse.builder().status(0).build());
		}
		
		
	}
	
	@GetMapping("/catalogs/{productid}")
	public ResponseEntity<?> findCatalogByProductid(@PathVariable String productid){
		CatalogDTO dto = catalogService.findCatalogByProductid(productid);
		
		CatalogResponse cResponse = dto.toCatalogResponse();
		
		return ResponseEntity.status(HttpStatus.OK).body(cResponse);
	}
	
	@PutMapping("/catalogs")
	public ResponseEntity<?> updateCatalog(@RequestBody CatalogDTO catalogDTO){
		
		CatalogDTO dbDTO = catalogService.findCatalogByProductid(catalogDTO.getProductid());
		
		if(dbDTO == null) {
			
			return ResponseEntity.status(HttpStatus.OK).body(
					CatalogResponse.builder().status(0).build()
			);
					
		}
		
		catalogDTO.setCreateAt(dbDTO.getCreateAt());
		catalogDTO.setId(dbDTO.getId());
		
		catalogService.updateCatalog(catalogDTO);
		
		CatalogResponse response = catalogDTO.toCatalogResponse();
		response.setStatus(1);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	

	
	
	@GetMapping("/health_check")
	public String status() {
		return "user service 입니다" + env.getProperty("local.server.port");
	}
	
	@PostMapping("/catalogs")
	public ResponseEntity<?> createCatalog(@RequestBody CatalogRequest catalogRequest){
		
		CatalogDTO catalogDTO = CatalogDTO.toCatalogDTO(catalogRequest);
		
		
		catalogDTO = catalogService.createCatalog(catalogDTO);
		
		CatalogResponse catalogResponse = catalogDTO.toCatalogResponse();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(catalogResponse);
	}
	
	
	@PutMapping("/catalogs/productid")
	public ResponseEntity<?> updateStockByProductId(@RequestBody OrderResponse orderResponse) {
		
		
		
		CatalogEntity orgCatalogEntity = catalogService.getCatalogByProductId(orderResponse.getProductId());
		
		if(orgCatalogEntity==null) {
			return ResponseEntity.status(HttpStatus.OK).body("0: 없는 상품입니다.");
		}
		
		if(orgCatalogEntity.getStock() < orderResponse.getQty()) {
			return ResponseEntity.status(HttpStatus.OK).body("0: 재고가 부족합니다.");
			
		}
		
		CatalogEntity entity = CatalogEntity.builder()
				.id(orgCatalogEntity.getId())
				.productid(orgCatalogEntity.getProductid())
				.productname(orgCatalogEntity.getProductname())
				.unitPrice(orgCatalogEntity.getUnitPrice())
				.createAt(orgCatalogEntity.getCreateAt())
				.stock(orgCatalogEntity.getStock()-orderResponse.getQty())
				.build();
		
		String result;
		try {
			result = catalogService.updateStockByProductId(entity);
			
			if (result.equalsIgnoreCase("ok")) {
				return ResponseEntity.status(HttpStatus.OK).body("1: 성공");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body("0: 재고 갱신 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body("0: 재고 갱신 실패");
		}
		
		
		
	}
	
	@GetMapping({"/catalogs/page/{page}", "/catalogs/page"})
	public ResponseEntity<?> listPage(@PathVariable(name = "page", required = false) Integer page){
		
		if(page == null) {
			page = 0;
		}
		
		List<CatalogDTO> dto_List = catalogService.listPage(page, 20);
		List<CatalogResponse> responseList = new ArrayList<>();
		
		for(CatalogDTO x : dto_List) {
			responseList.add(x.toCatalogResponse());
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}
	
	
	@PostMapping("/testdata")
	public ResponseEntity<?> testdata(@RequestBody CatalogRequest catalogRequest){
		
		CatalogDTO catalogDTO = CatalogDTO.toCatalogDTO(catalogRequest);
		
		String pn = catalogDTO.getProductname();
		for (int i = 0; i < 52; i++) {
						
			catalogDTO.setProductname(pn + i); 
			catalogService.createCatalog(catalogDTO);
		}
		
		try {
			Thread.sleep(30);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		CatalogResponse catalogResponse = catalogDTO.toCatalogResponse();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(catalogResponse);
	}
	
	
	

}
