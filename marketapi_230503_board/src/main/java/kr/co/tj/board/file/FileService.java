package kr.co.tj.board.file;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FileService {
	
	@Autowired
	private FileRepository fileRepository;
	
	
	

@Transactional
 public Long saveFile(FileDTO fileDTO) {
	 if (fileDTO.getFilename() == null || fileDTO.getFilename().isEmpty()) {
	        throw new IllegalArgumentException("Filename cannot be null or empty");
	    }
	
	return fileRepository.save(fileDTO.toEntity()).getId();
	
	
}

@Transactional
 public FileDTO getFile(Long id) {
	
	
	FileEntity entity = fileRepository.findById(id).get();
	
	FileDTO fileDTO = FileDTO.builder()
			.id(id)
			.origFilename(entity.getOrigFilename())
			.filename(entity.getFilename())
			.filePath(entity.getFilePath())
			.build();
	return fileDTO;
}

}
