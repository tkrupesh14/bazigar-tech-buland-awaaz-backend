package com.bazigar.bulandawaaz.hide_post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HidePostRepository extends JpaRepository<HidePost, Long> {


    @Query(value = "select s from HidePost s where s.userId = ?1")
    public List<HidePost> findHiddenPosts(Long userId);

}