package kr.co.tj.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImgFileDTO {
	
	private Long id;
	private String filename;
	private byte[] filedata;
	

}
