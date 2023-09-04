package kr.co.tj.boardservice.feign;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;

import kr.co.tj.boardservice.dto.BoardResponse;
import kr.co.tj.boardservice.dto.CategoryResponse;





@FeignClient(name = "category-service")
public interface CategoryFeign {
	
	@GetMapping("category-service/category/name")
	public CategoryResponse getNameByCid(Long cid) ;
	

}
