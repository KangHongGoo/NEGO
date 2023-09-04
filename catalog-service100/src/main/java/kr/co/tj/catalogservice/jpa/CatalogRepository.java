package kr.co.tj.catalogservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.tj.catalogservice.dto.CatalogEntity;

public interface CatalogRepository extends JpaRepository<CatalogEntity, Long>{

	CatalogEntity findByProductid(String productid);

	

}
