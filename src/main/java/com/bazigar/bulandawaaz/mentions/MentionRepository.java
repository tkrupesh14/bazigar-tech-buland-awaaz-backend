package com.bazigar.bulandawaaz.mentions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentionRepository extends JpaRepository<Mention, Long> {

    @Query(value = "select s from Mention s where s.type = ?2 and s.postId = ?1")
    List<Mention> findMentionByPostIdAndType(Long postId, String type);

    @Query(value = "select s from Mention s where s.type = ?2 and s.mentionedUserId = ?1")
    Page<Mention> findMentionByMentionedUserIdAndType(Long postId, String type, Pageable pageable);

}
