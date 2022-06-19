package com.bazigar.bulandawaaz.realtime_chatting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("select c from ChatMessage c where c.chatId = ?1")
    public List<ChatMessage> findChatMessagesByChatId(Long chatId);

}
