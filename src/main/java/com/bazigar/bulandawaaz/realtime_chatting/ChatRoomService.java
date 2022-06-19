package com.bazigar.bulandawaaz.realtime_chatting;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    public Long save(ChatRoom chatRoom) {
        if (chatRoom == null) {
            return null;
        }
        Optional<User> sender = userRepository.findById(chatRoom.getSenderId());
        if (sender.isEmpty()) {
            return null;
        }
        Optional<User> receiver = userRepository.findById(chatRoom.getReceiverId());
        if (receiver.isEmpty()) {
            return null;
        }
        if (chatRoom.getSenderId() < chatRoom.getReceiverId()) {
            chatRoom.setChatId(chatRoom.getSenderId()+"__"+ chatRoom.getReceiverId());
        }
        else {
            chatRoom.setChatId(chatRoom.getReceiverId()+"__"+ chatRoom.getSenderId());
        }
        chatRoomRepository.save(chatRoom);
        return chatRoom.getId();
    }

    public Response delete(Long chatId) {
        if (chatId == null) {
            return new Response(
                    "No chat Room found with the given chatId",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatId);
        if (chatRoomOptional.isPresent()) {
            chatRoomRepository.delete(chatRoomOptional.get());
            return new Response(
                    "Chat room successfully deleted!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "Chat room not found with the given id",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
    }

    public Long getChatRoomId(Long senderId, Long receiverId) {
        if (senderId == null || receiverId == null) {
            return null;
        }
        String chatId = "";
        if (senderId < receiverId) {
            chatId = senderId+"__"+receiverId;
        }
        else {
            chatId = receiverId+"__"+senderId;
        }
        Optional<ChatRoom> chatRoom = chatRoomRepository.findChatRoomByChatId(chatId);
        if (chatRoom.isPresent()) {
            return chatRoom.get().getId();
        }
        else {
            return null;
        }
    }

}
