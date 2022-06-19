package com.bazigar.bulandawaaz.realtime_chatting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select s from ChatRoom s where s.chatId = ?1")
    Optional<ChatRoom> findChatRoomByChatId(String chatId);
}
