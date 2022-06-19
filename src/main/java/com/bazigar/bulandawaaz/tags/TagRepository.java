package com.bazigar.bulandawaaz.tags;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "select s from Tag s where s.postId = ?1")
    List<Tag> findTagByPostId(Long postId);

    @Query(value = "select s from Tag s where s.taggedUserId = ?1")
    Page<Tag> findTagByUserId(Long userId, Pageable pageable);

}
