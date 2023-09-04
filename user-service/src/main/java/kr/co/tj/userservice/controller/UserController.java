package kr.co.tj.userservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.userservice.dto.UserDTO;
import kr.co.tj.userservice.dto.UserLoginRequest;
import kr.co.tj.userservice.dto.UserRequest;
import kr.co.tj.userservice.dto.UserResponse;
import kr.co.tj.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user-service")
@Slf4j
public class UserController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {
		
		Map<String, Object> map = new HashMap<>();
		
		if(userLoginRequest.getUsername() == null || userLoginRequest.getUsername().isEmpty()) {
			map.put("result", "ID를 바르게 입력해주세요");
			return ResponseEntity.ok().body(map);
		}
		
		if(userLoginRequest.getPassword() == null || userLoginRequest.getPassword().isEmpty()) {
			map.put("result", "PW를 바르게 입력해주세요");
			return ResponseEntity.ok().body(map);
		}
		UserDTO userDTO = UserDTO.toUserDTO(userLoginRequest);
		
		userDTO = userService.login(userDTO);
		
		if(userDTO == null) {
			map.put("result", "사용자명이나 비밀번호가 다릅니다");
			return ResponseEntity.ok().body(map);
		}
		UserResponse userResponse = userDTO.toUserResponse();
		
		map.put("result", userResponse);
		
		return ResponseEntity.ok().body(map);
		
	}
	
	@GetMapping("/user/{username}/orders") //user-service에서 username을 이용해서 order-service로부터 해당 user의 주문목록을 가져오기
	public ResponseEntity<?> getOrders(@PathVariable("username") String username) {
		UserDTO userDTO = userService.getOrders(username);
		
		UserResponse userResponse = userDTO.toUserResponse();
		
		return ResponseEntity.status(HttpStatus.OK).body(userResponse); //OK는 200번
	}
	
	@GetMapping("/test")
	public ResponseEntity<?> test() {
		
		System.out.println("::::::::::::::::::::");
		System.out.println("::::::::::::::::::::");
		System.out.println("::::::::::::::::::::");
		System.out.println("::::::::::::::::::::");
		
		
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse());
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
		UserDTO userDTO = UserDTO.toUserDTO(userRequest);
		
		userDTO = userService.createUser(userDTO);
		UserResponse userResponse = userDTO.toUserResponse();
		
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponse); //CREATED는 201번
	}

	@GetMapping("/health_check")
	public String status() {
		log.info("data.world: {}", env.getProperty("data.world"));
		log.info("data.test: {}", env.getProperty("data.test"));
		return "user service입니다"+env.getProperty("local.server.port") + ":" + env.getProperty("data.test");
	}
}
