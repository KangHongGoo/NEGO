package kr.co.tj.message;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {
	
	private SimpMessageSendingOperations messagingTemplate;
	private ChatMessageRepository chatMessageRepository;
	
	@Autowired
	public ChatMessageService(SimpMessageSendingOperations messagingTemplate,ChatMessageRepository chatMessageRepository ) {
		// TODO Auto-generated constructor stub
		super();
		this.messagingTemplate = messagingTemplate;
		this.chatMessageRepository = chatMessageRepository;
	}
	
	@Transactional
	public void saveAndSendMessage(ChatMessageDTO dto) {
		// TODO Auto-generated method stub
		Date date = new Date();
		
		ChatMessageEntity entity = ChatMessageEntity.builder()
				.roomTitle(dto.getRoomTitle())
				.sendAt(date)
				.sender(dto.getSender())
				.receiver(dto.getReceiver())
				.message(dto.getMessage())
				.isRead(false)
				.build();
		
		entity = chatMessageRepository.save(entity);
		
		ChatMessageResponse response = ChatMessageResponse.builder()
				.id(entity.getId())
				.roomTitle(entity.getRoomTitle())
				.sendAt(date)
				.sender(entity.getSender())
				.receiver(entity.getReceiver())
				.message(entity.getMessage())
				.isRead(false)
				.build();
		
		messagingTemplate.convertAndSend("/sub/chatroom/" + response.getRoomTitle(), response);
	}

	public void readMessage(Long id) {
		
		
		Optional<ChatMessageEntity> optional = chatMessageRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new RuntimeException();
		}
		
		ChatMessageEntity chatMessageEntity = optional.get();
		chatMessageEntity.setRead(true);
		chatMessageEntity = chatMessageRepository.save(chatMessageEntity);
		
		ChatMessageReadStateResponse readResponse = ChatMessageReadStateResponse.builder()
				.id(chatMessageEntity.getId())
				.isRead(chatMessageEntity.isRead())
				.build();
		
		messagingTemplate.convertAndSend("/sub/chatroom/read/" + chatMessageEntity.getRoomTitle(), readResponse);
		
	}

	public List<ChatMessageDTO> getMessageList(String roomTitle) {
		// TODO Auto-generated method stub
		List<ChatMessageEntity> entityList = chatMessageRepository.findByRoomTitle(roomTitle);
		List<ChatMessageDTO> dtoList = new ArrayList<>();
		for(ChatMessageEntity x : entityList) {
			ChatMessageDTO dto = ChatMessageDTO.builder()
					.id(x.getId())
					.roomTitle(x.getRoomTitle())
					.sendAt(x.getSendAt())
					.sender(x.getSender())
					.receiver(x.getReceiver())
					.message(x.getMessage())
					.isRead(x.isRead())
					.build();
			dtoList.add(dto);
		}
		return dtoList;
		
		return null;
	}

}
