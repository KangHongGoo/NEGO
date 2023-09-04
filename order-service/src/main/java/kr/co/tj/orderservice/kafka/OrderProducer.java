package kr.co.tj.orderservice.kafka;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.tj.orderservice.dto.Field;
import kr.co.tj.orderservice.dto.KafkaOrderDTO;
import kr.co.tj.orderservice.dto.OrderDTO;
import kr.co.tj.orderservice.dto.Payload;
import kr.co.tj.orderservice.dto.Schema;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderProducer{
	
	private KafkaTemplate<String, String> kafkaTemplate;
	
	List<Field> fields = Arrays.asList(
			new Field("string", false, "username"),
			new Field("string", false, "productId"),
			new Field("int32", false, "qty"),
			new Field("int32", false, "unitPrice"),
			new Field("string", false, "orderId"),
			new Field("int32", false, "totalPrice")
			);
	
	Schema schema = Schema.builder().type("struct").fields(fields).optional(false).name("orders").build();
	
	
	@Autowired
	public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
		
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public OrderDTO send(String topic, OrderDTO orderDTO) {
		
		Payload payload = Payload.builder()
				
				.username(orderDTO.getUsername())
				.productId(orderDTO.getProductId())
				.qty(orderDTO.getQty())
				.unitPrice(orderDTO.getUnitPrice())
				.totalPrice(orderDTO.getTotalPrice())
				.orderId(orderDTO.getOrderId())
				.build();
		
		
		KafkaOrderDTO kafkaOrderDTO = new KafkaOrderDTO(schema, payload);
		
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = null;
		
		try {
			jsonData = mapper.writeValueAsString(kafkaOrderDTO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		kafkaTemplate.send(topic, jsonData);
		log.info("주문과 관련된 데이터를 카프카로 전송합니다."+kafkaOrderDTO);
		
		return orderDTO;
	}
}