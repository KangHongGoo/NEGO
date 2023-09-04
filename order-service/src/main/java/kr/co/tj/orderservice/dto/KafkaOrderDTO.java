package kr.co.tj.orderservice.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KafkaOrderDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Schema schema;
	private Payload payload;
}
