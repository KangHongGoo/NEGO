package kr.co.tj.board;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FileSerivce {
	
	@Autowired
	private FileRepository fileRepository;
	
	
	

@Transactional
 public Long saveFile(FileDTO fileDTO) {
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
