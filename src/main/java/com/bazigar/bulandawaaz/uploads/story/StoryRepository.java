package com.bazigar.bulandawaaz.uploads.story;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    @Query(value = "select s from Story s where s.user.id = ?1")
    Page<Story> findByUserId(Long userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update Story s set s.caption = ?2 where s.storyId = ?1")
    void updateStoryCaption(Long storyId, String caption);

}
