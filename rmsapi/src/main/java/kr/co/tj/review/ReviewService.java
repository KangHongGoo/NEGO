package kr.co.tj.review;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		ReviewEntity entity=new ModelMapper().map(dto, ReviewEntity.class);
	
		Date now = new Date();
		entity.setCreateDate(now);
		entity.setUpdateDate(now);

		entity = reviewRepository.save(entity);

		dto=new ModelMapper().map(entity, ReviewDTO.class);
		return dto;
	}
	
	@Transactional
	public List<ReviewDTO> findPage(Integer pagenum) {
		List<ReviewEntity> list_entity = reviewRepository.findAll();
		
		int start = 10 * (pagenum - 1);
	    int end = 10*(pagenum-1)+10;
	    
		List<ReviewEntity> list_entity_page=list_entity.subList(start, end);
		
	    List<ReviewDTO> list = new ArrayList<>();

	    if(pagenum<=list_entity.size()/10) {
		list_entity_page.forEach(e -> {
			list.add(new ModelMapper().map(e, ReviewDTO.class));
			});
		} else {
			List<ReviewEntity> list_entity_remaining = list_entity.subList(list_entity.size()/5*5, list_entity.size());
			list_entity_remaining.forEach(e -> {
		        list.add(new ModelMapper().map(e, ReviewDTO.class));
		    });
		}
		
		return list;
	}
	
	@Transactional
	public List<ReviewDTO> findAll() {
		List<ReviewEntity> list_entity = reviewRepository.findAll();
		List<ReviewDTO> list = new ArrayList<>();

		list_entity.forEach(e -> {
			list.add(new ModelMapper().map(e, ReviewDTO.class));
		});
		return list;
	}

	@Transactional
	public ReviewDTO findById(Long id) {
		Optional<ReviewEntity> optional=reviewRepository.findById(id);
		if (!optional.isPresent()) {
			throw new RuntimeException("리뷰 가져오기 실패");
		}

		ReviewEntity entity = optional.get();
		return new ModelMapper().map(entity,ReviewDTO.class);
	}
	
	@Transactional
	public void delete(Long id) {
		reviewRepository.deleteById(id);
	}

	@Transactional
	public ReviewDTO update(ReviewDTO dto) {
		Optional<ReviewEntity> optional = reviewRepository.findById(dto.getId());

		if (!optional.isPresent()) {
			throw new RuntimeException("해당 데이터는 없다.");
		}
		ReviewEntity entity = optional.get();

		entity.setRestaurantName(dto.getRestaurantName());
		entity.setRate(dto.getRate());
		entity.setRecommend(dto.getRecommend());
		entity.setEtc(dto.getEtc());
		entity.setUpdateDate(new Date());

		entity = reviewRepository.save(entity);
		return new ModelMapper().map(entity,ReviewDTO.class);
	}
}
