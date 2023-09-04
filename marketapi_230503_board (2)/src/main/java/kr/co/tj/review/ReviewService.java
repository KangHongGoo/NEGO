package kr.co.tj.review;

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

import kr.co.tj.mypage.MypageDTO;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	
	
	public List<MypageDTO> findAllMypageByUsername(String username) {
	      List<ReviewEntity> reviewList = reviewRepository.findMineByUsername(username);
	      List<MypageDTO> mypageList = new ArrayList<>();
	
	      for (ReviewEntity reviewEntity : reviewList) {
	    	  MypageDTO mypageDTO = new MypageDTO();
	
    	  mypageDTO.setId(reviewEntity.getId());
	    	  mypageDTO.setTitle(reviewEntity.getTitle());
	    	  mypageDTO.setContent(reviewEntity.getContent());
	    	  mypageDTO.setCreateDate(reviewEntity.getCreateDate());
	    	  mypageDTO.setUpdateDate(reviewEntity.getUpdateDate());
		  }
		  return mypageList;
   }
	// 은솔님 코드


	@Transactional
	public List<ReviewDTO> findAll() {
		List<ReviewEntity> entity = reviewRepository.findAll();
		List<ReviewDTO> dto = new ArrayList<>();

		entity.forEach(x -> {
			dto.add(new ModelMapper().map(x, ReviewDTO.class));
		});
		return dto;
	}

	@Transactional
	public ReviewDTO findById(Long id) {
		Optional<ReviewEntity> optional = reviewRepository.findById(id);
		if (!optional.isPresent()) {
			throw new RuntimeException("리뷰 가져오기 실패");
		}
		
		ReviewEntity entity = optional.get();
		entity.setCount(entity.getCount()+1);
		return new ModelMapper().map(entity, ReviewDTO.class);
	}
	
	@Transactional
	public List<ReviewDTO> searchTitles(String keyword) {
		List<ReviewEntity> entity=reviewRepository.findByTitleContaining(keyword);
		List<ReviewDTO> dto = new ArrayList<>();

		entity.forEach(x -> {
			dto.add(new ModelMapper().map(x, ReviewDTO.class));
		});
		return dto;
	}
	
	@Transactional
	public List<ReviewDTO> searchContents(String keyword) {
		List<ReviewEntity> entity=reviewRepository.findByContentContaining(keyword);
		List<ReviewDTO> dto = new ArrayList<>();

		entity.forEach(x -> {
			dto.add(new ModelMapper().map(x, ReviewDTO.class));
		});
		return dto;
	}
	
	@Transactional
	public List<ReviewDTO> searchTitlesOrContents(String keyword) {
		List<ReviewEntity> entity=reviewRepository.findByTitleContainingOrContentContaining(keyword, keyword);
		List<ReviewDTO> dto = new ArrayList<>();

		entity.forEach(x -> {
			dto.add(new ModelMapper().map(x, ReviewDTO.class));
		});
		return dto;
	}
	
	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		ReviewEntity entity = new ModelMapper().map(dto, ReviewEntity.class);

		entity.setCount(0);
		
		Date now = new Date();
		entity.setCreateDate(now);
		entity.setUpdateDate(now);

		entity = reviewRepository.save(entity);

		dto = new ModelMapper().map(entity, ReviewDTO.class);

		return dto;
	}

	@Transactional
	public ReviewDTO update(ReviewDTO dto) {
		Optional<ReviewEntity> optional = reviewRepository.findById(dto.getId());

	      if (!optional.isPresent()) {
	         throw new RuntimeException("수정할 데이터가 존재하지 않습니다.");
	      }
	      ReviewEntity entity = optional.get();
	      
	      if (dto.getTitle() != null) {
		        entity.setTitle(dto.getTitle());
		    }

	      if (dto.getContent() != null) {
	        entity.setContent(dto.getContent());
	      }
	      
	      if(dto.getRate() > 5.0 && dto.getRate()<0) {
	    	  entity.setRate(dto.getRate());
	      }
	      entity.setUpdateDate(new Date());
	      
	      entity = reviewRepository.save(entity);
	      return new ModelMapper().map(entity, ReviewDTO.class);
	}

	@Transactional
	public void delete(Long id) {
		reviewRepository.deleteById(id);
	}

	public List<ReviewDTO> getPage(Pageable pageable) {
		Page<ReviewEntity> entity=reviewRepository.findAll(pageable);
		List<ReviewDTO> dto=new ArrayList<>();
		
		entity.forEach(x -> {
            dto.add(new ModelMapper().map(x, ReviewDTO.class));
        });
		return dto;
	}

	public List<ReviewDTO> getSearchPage(Pageable pageable, String keyword) {
		Page<ReviewEntity> entity=reviewRepository.findSearchAll(pageable, keyword);
		List<ReviewDTO> dto=new ArrayList<>();
		
		entity.forEach(x -> {
            dto.add(new ModelMapper().map(x, ReviewDTO.class));
        });
		return dto;
	}


	/*
	 * // 조회수 자동 업데이트 추가 public ReviewDTO countUpdate(ReviewDTO dto) {
	 * 
	 * Optional<ReviewEntity> optional = reviewRepository.findById(dto.getId());
	 * 
	 * 
	 * if(!optional.isPresent()) { throw new RuntimeException("해당 데이터는 없음"); }
	 * 
	 * ReviewEntity orgEntity = optional.get(); int count = orgEntity.getCount()+1;
	 * 
	 * ReviewEntity entity = new ReviewEntity.ReviewEntityBuilder()
	 * .id(orgEntity.getId()) .username(orgEntity.getUsername())
	 * .title(orgEntity.getTitle()) .content(orgEntity.getContent()) .count(count)
	 * .rate(orgEntity.getRate()) .createDate(orgEntity.getCreateDate())
	 * .updateDate(orgEntity.getUpdateDate()) .build();
	 * 
	 * entity = reviewRepository.save(entity);
	 * 
	 * return new ModelMapper().map(entity, ReviewDTO.class); }
	 */

	
	/*
	 * public List<ReviewDTO> findAllByUsername(String username) {
	 * List<ReviewEntity> entity = reviewRepository.findAllByUsername(username);
	 * List<ReviewDTO> dto = new ArrayList<>();
	 * 
	 * entity.forEach(x -> { dto.add(new ModelMapper().map(x, ReviewDTO.class)); });
	 * return dto; }
	 */
}
