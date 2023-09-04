package kr.co.tj.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity

// 생성자의 접근 제한자를 'protected'로 지정
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Table(name = "file")
public class FileEntity {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String origFilename;
	
	@Column(nullable = false)
	private String filename;
	
	@Column(nullable = false)
	private String filePath;
	
	@Builder
	public FileEntity(Long id, String origFilename, String filename, String filePath) {
		this.id = id;
		this.origFilename = origFilename;
		this.filename = filename;
		this.filePath = filePath;
	}

}
