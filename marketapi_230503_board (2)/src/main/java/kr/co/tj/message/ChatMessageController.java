package kr.co.tj.message;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/chat-service")
public class ChatMessageController {
	
	private Environment env;
	private ChatMessageService chatMessageService;
	
	@Autowired
	public ChatMessageController(Environment env, ChatMessageService chatMessageService) {
		// TODO Auto-generated constructor stub
		super();
		this.env = env;
		this.chatMessageService = chatMessageService;
	}
	@GetMapping("/messagelist/{roomTitle}")
	public ResponseEntity<?> getMessageList(@PathVariable("roomTItle") String roomTitle) {
		List<ChatMessageDTO> dtoList = chatMessageService.getMessageList(roomTitle);
		List<ChatMessageResponse> responseList = new ArrayList<>();
		
		for(ChatMessageDTO x : dtoList) {
			ChatMessageResponse response = ChatMessageResponse.builder()
					.id(x.getId())
					.roomTitle(x.getRoomTitle())
					.sendAt(x.getSendAt())
					.receiver(x.getReceiver())
					.message(x.getMessage())
					.isRead(x.isRead())
					.build();
			responseList.add(response);
		}
		
		return ResponseEntity.ok().body(responseList);
	}
	
	@MessageMapping("/readmessage")
	public void readMessage(Long id) {
		chatMessageService.readMessage(id);
	}
	
	@MessageMapping("/sendmessage")
	public void message(@Header("Authorization") String bearerToken, ChatMessageRequest request) {
		String token = bearerToken.replace("Bearer", "");
		String secKey = env.getProperty("data.SECRET_KEY");
		String encodedSecKey = Base64.getEncoder().encodeToString(secKey.getBytes());
		String sender = Jwts.parser()
				.setSigningKey(encodedSecKey)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
		ChatMessageDTO dto = ChatMessageDTO.builder()
				.roomTitle(request.getRoomTitle())
				.sender(sender)
				.receiver(request.getReceiver())
				.message(request.getMessgae())
				.build();
		
		chatMessageService.saveAndSendMessage(dto);
	}

}
