package kr.co.tj.orderservice.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Schema {
	private String type;
	private List<Field> fields;
	private boolean optional;
	private String name;
}
