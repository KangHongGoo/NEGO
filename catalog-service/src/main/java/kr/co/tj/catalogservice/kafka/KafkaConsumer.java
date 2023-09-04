package kr.co.tj.catalogservice.kafka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.tj.catalogservice.dto.CatalogEntity;
import kr.co.tj.catalogservice.jpa.CatalogRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {
	
	private CatalogRepository catalogRepository;

	
	@Autowired
	public KafkaConsumer(CatalogRepository catalogRepository) {
		super();
		this.catalogRepository = catalogRepository;
	}
	
	@KafkaListener(topics = {"order-insert-topic"}, groupId = "consumerGroup1")
	public void updateStock(String kafkaMsg) {
		log.info("kafkaMsg: ====>" + kafkaMsg);
		
		Map<Object, Object> map = new HashMap<>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			map = mapper.readValue(kafkaMsg, new TypeReference<Map<Object, Object>>() {});
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CatalogEntity entity = catalogRepository.findByProductId((String)map.get("productId"));
		
		if(entity != null) {
			long oldStock = entity.getStock();
			long qty = Long.valueOf(String.valueOf(map.get("qty")));
			
			entity.setStock(oldStock-qty);
			
			catalogRepository.save(entity);
		}
	}
	

}
