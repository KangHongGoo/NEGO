package kr.co.tj.orderservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.tj.orderservice.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {
	
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {

		this.kafkaTemplate = kafkaTemplate;
	};
	
	public OrderDTO send(String topic, OrderDTO orderDTO) {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = null;
		
		try {
			jsonData = mapper.writeValueAsString(orderDTO);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		kafkaTemplate.send(topic, jsonData);
		log.info("주문과 관련된 데이터를 카프카로 전송합니다.");
		
		return orderDTO;
		
	}
}
