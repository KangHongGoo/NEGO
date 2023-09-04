package kr.co.tj.qna;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.tj.board.BoardRepository;
import kr.co.tj.mypage.MypageDTO;



@Service
public class QnAService {
	
	@Autowired
	private QnARepository qnaRepository;

	
	
	
	// 은솔님 코드 추가
	public List<MypageDTO> findAllMypageByUsername(String username) {
	      List<QnAEntity> qnaList = qnaRepository.findMineByUsername(username);
	      List<MypageDTO> mypageList = new ArrayList<>();

	      for (QnAEntity qnaEntity : qnaList) {
	    	  MypageDTO mypageDTO = new MypageDTO();

	         mypageDTO.setId(qnaEntity.getId());
	         mypageDTO.setTitle(qnaEntity.getTitle());
	         mypageDTO.setContent(qnaEntity.getContent());
	         mypageDTO.setUsername(qnaEntity.getUsername());
	         mypageDTO.setCreateDate(qnaEntity.getCreateDate());
	         mypageDTO.setUpdateDate(qnaEntity.getUpdateDate());
	      }
	      return mypageList;
	   }


	

	

	
	@Transactional
	public QnADTO findById(Long id) {
		Optional<QnAEntity> optional = qnaRepository.findById(id);
		
		 if (!optional.isPresent()) {
	         throw new RuntimeException("리뷰 가져오기 실패");
	      }
		 
		 QnAEntity entity = optional.get();
		 return new ModelMapper().map(entity, QnADTO.class);
	}


	@Transactional
	public List<QnADTO> findAll() {
		
		List<QnAEntity> list_entity = qnaRepository.findAll();
		List<QnADTO> list = new ArrayList<>();

		list_entity.forEach(e -> {
			list.add(new ModelMapper().map(e, QnADTO.class));
		});
		return list;
	}


	@Transactional
	public QnADTO insert(QnADTO dto) {
	    QnAEntity entity = new ModelMapper().map(dto, QnAEntity.class);

	    Date now = new Date();
	    entity.setCreateDate(now);
	    entity.setUpdateDate(now);

	    // 이미지 아이디 설정 로직 추가
	    entity.setFileId(dto.getFileId());

	    // 삽입된 게시물을 데이터베이스에 저장합니다.
	    entity = qnaRepository.save(entity);

	    // 변경 사항을 데이터베이스에 즉시 적용합니다.
	    qnaRepository.flush();

	    // 삽입된 게시물의 ID를 얻습니다.
	    Long insertedId = entity.getId();

	    // 삽입된 게시물의 ID를 dto 객체에 설정합니다.
	    dto.setId(insertedId);

	    // 수정된 dto 객체를 반환합니다.
	    return dto;
	}





	@Transactional
	public void delete(Long id) {
		qnaRepository.deleteById(id);
		
	}


	@Transactional
	public QnADTO update(QnADTO dto) {
		
		Optional<QnAEntity> optional = qnaRepository.findById(dto.getId());
		
		 if (!optional.isPresent()) {
	         throw new RuntimeException("해당 데이터는 없다.");
	      }
		 QnAEntity entity = optional.get();
		 
		 entity.setTitle(dto.getTitle());
		 entity.setContent(dto.getContent());
		 entity.setFileId(dto.getFileId());
		 entity.setUpdateDate(new Date());
		 
		 entity = qnaRepository.save(entity);
		 return new ModelMapper().map(entity, QnADTO.class);
	}


	@Transactional
	public List<QnADTO> getPage(Pageable pageable) {
	      Page<QnAEntity> entity=qnaRepository.findAll(pageable);
	      List<QnADTO> dtoList=new ArrayList<>();
	      
	      entity.forEach(x -> {
	            dtoList.add(new ModelMapper().map(x, QnADTO.class));
	        });
	      return dtoList;
	   }






	public QnADTO getQnaData(Long id) {
		// TODO Auto-generated method stub
		return null;
	}






	
	
	
	

}
