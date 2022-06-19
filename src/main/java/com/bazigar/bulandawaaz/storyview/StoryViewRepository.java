package com.bazigar.bulandawaaz.storyview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryViewRepository  extends JpaRepository<Storyview, Long> {

    @Query("select s from Storyview s where s.storyId = ?1")
    List<Storyview> findByStoryId(Long storyId);

}
