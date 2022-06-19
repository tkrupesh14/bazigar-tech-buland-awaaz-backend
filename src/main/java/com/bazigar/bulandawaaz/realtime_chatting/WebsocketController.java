package com.bazigar.bulandawaaz.realtime_chatting;

import com.bazigar.bulandawaaz.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public ChatMessage processMessage(@Payload ChatMessage chatMessage) {
        Long chatRoomId = chatRoomService.getChatRoomId(chatMessage.getSenderId(), chatMessage.getReceiverId());
        System.out.println("chatRoomId is "+chatRoomId);

        if (chatRoomId == null) {
            chatRoomService.save(new ChatRoom(
                    chatMessage.getSenderId(),
                    chatMessage.getReceiverId()
            ));
        }
        chatMessage.setChatId(chatRoomId);
        chatMessage.setSendingTime(System.currentTimeMillis());
        chatMessage.setStatus(MessageStatus.DELIVERED);
        ChatMessage saved = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiverId().toString(), "/queue/messages",
                saved
        );
        return saved;
    }
}
