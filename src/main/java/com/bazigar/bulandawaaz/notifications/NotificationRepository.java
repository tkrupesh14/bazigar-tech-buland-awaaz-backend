package com.bazigar.bulandawaaz.notifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {


    @Query(value = "select s from Notification s where s.user.id = ?1")
    List<Notification> findByUserId(Long userId);

    @Query(value = "select s from Notification s where s.user.id = ?1")
    Page<Notification> findByUserId(Long userId, Pageable pageable);
}
