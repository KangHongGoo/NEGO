package kr.co.tj.board.file;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class FileDTO {
	private Long id;
	private String origFilename;
	private String filename;
	private String filePath;
	
	public FileEntity toEntity() {
		FileEntity build = FileEntity.builder()
				.id(id)
				.origFilename(origFilename)
				.filename(filename)
				.filePath(filePath)
				.build();
		return build;
		
	} 
	
	@Builder
	public FileDTO(Long id, String origFilename, String filename, String filePath) {
		
		this.id = id;
		this.origFilename = origFilename;
		this.filename = filename;
		this.filePath = filePath;
	}

}
