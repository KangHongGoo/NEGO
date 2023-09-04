package kr.co.tj.userservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import kr.co.tj.userservice.dto.OrderResponse;
import kr.co.tj.userservice.dto.UserDTO;
import kr.co.tj.userservice.dto.UserEntity;
import kr.co.tj.userservice.feign.OrderFeign;
import kr.co.tj.userservice.jpa.UserRespository;

@Service
public class UserService {
	
	/*
	 @Value("${data.test1}") private String test1;
	  
	 @Value("${data.url}") private String url;
	 */


	private UserRespository userRepository;

	private OrderFeign orderFeign;

	private RestTemplate restTemplate;
	
	private Environment env;
	
	private TokenProvider tokenProvider;
	
	
	
	@Autowired
	public UserService(Environment env, UserRespository userRespository, OrderFeign orderFeign, 
			RestTemplate restTemplate,TokenProvider tokenProvider) {
		super();
		this.env = env;
		this.userRepository = userRespository;
		this.orderFeign = orderFeign;
		this.restTemplate = restTemplate;
		this.tokenProvider = tokenProvider;
	}    
	 
	@Autowired
	// 기존에 쓰던 PasswordEncoder에서 BcryptPasswordEncoder로 바뀜
	private BCryptPasswordEncoder passwordEncoder;

	public UserDTO getOrders(String username) {
		

		UserEntity userEntity = userRepository.findByUsername(username);

		if (userEntity == null) {
			throw new RuntimeException("존재하지않는 사용자입니다ㅏㅏ.");
		}

		UserDTO userDTO = new UserDTO();
		userDTO = userDTO.toUserDTO(userEntity);
		/*
		// 1. 서비스간의 통신 : fegin 이용해서 통신한 코드
		List<OrderResponse> orderList = orderFeign.getOrdersByUsername(username);  */
		
		// 2. 서비스간의 통신 : RestTemplate 이용해서 통신
		// String orderUrl = "http://localhost:8000/order-service/orders/user/"+username;
		// String orderUrl = String.format("http://localhost:8000/order-service/orders/user/%s", username);
		String orderUrl = String.format(env.getProperty("data.url"), username);
		
		 ResponseEntity<List<OrderResponse>> result = restTemplate.exchange(orderUrl,
				 HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderResponse>>() {	
		});
		
		
		List<OrderResponse> orderList = result.getBody();
		
		userDTO.setOrderList(orderList);					 					  

		return userDTO;
	}

	public UserDTO createUser(UserDTO userDTO) {
		userDTO = getDate(userDTO);

		UserEntity userEntity = userDTO.toUserEntity();
		
		// 암호화                       
		
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

		userEntity = userRepository.save(userEntity);

		return userDTO.toUserDTO(userEntity);
	}

	private UserDTO getDate(UserDTO userDTO) {
		Date date = new Date();

		if (userDTO.getCreateAt() == null) {
			userDTO.setCreateAt(date);
		}

		userDTO.setUpdateAt(date);
		return userDTO;
	}

	public UserDTO login(UserDTO userDTO) {
		UserEntity userEntity = userRepository.findByUsername(userDTO.getUsername());
		if(userEntity == null) {
			return null;
		}
		
		if(!passwordEncoder.matches(userDTO.getPassword(), userEntity.getPassword())) {
			
			return null;
		}
		
		String token = tokenProvider.create(userEntity);
		
		userDTO = userDTO.toUserDTO(userEntity);
		userDTO.setToken(token);
		userDTO.setPassword("");
		
		return userDTO;
	}

}
