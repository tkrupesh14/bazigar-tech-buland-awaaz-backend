package com.bazigar.bulandawaaz.realtime_chatting;

import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService {

    @Autowired
    public ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public ChatMessage save(ChatMessage chatMessage) {
        if (chatMessage == null) {
            return null;
        }
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    public Response delete(Long chatId) {
        if (chatId == null) {
            return new Response(
                    "chatId is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<ChatMessage> chatMessage = chatMessageRepository.findById(chatId);
        if (chatMessage.isPresent()) {
            chatMessageRepository.delete(chatMessage.get());
            return new Response(
                    "Chat successfully deleted!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "No chat found with the given id!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
    }

    public Response getAllMessagesForChatRoom(Long chatId) {
        if (chatId == null) {
            return new Response(
                    "chatId is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(chatId);
        if (chatRoom.isEmpty()) {
            return new Response(
                    "No chat room found with the given id",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByChatId(chatId);
        return new Response(
                chatMessages.size()+" messages found",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                chatMessages
        );
    }
}
