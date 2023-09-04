package kr.co.tj.image;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImgFileService {

	@Autowired
	private ImgFileRepository fileRepository;

	public ImgFileDTO uploadImg(ImgFileDTO dto) {
		
		ImgFileEntity entity = ImgFileEntity.builder()
				.filedata(dto.getFiledata())
				.filename(dto.getFilename())
				.build();
		
		entity = fileRepository.save(entity);
		
		dto.setId(entity.getId());
		// TODO Auto-generated method stub
		return dto;
	}

	public ImgFileDTO getImgFile(Long id) {
		// TODO Auto-generated method stub
		
		Optional<ImgFileEntity> optional = fileRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new RuntimeException();
		}
		
		ImgFileEntity entity = optional.get();
		ImgFileDTO dto = new ImgFileDTO(entity.getId(), entity.getFilename(), entity.getFiledata());
		
		return dto;
	}
	
	
}
