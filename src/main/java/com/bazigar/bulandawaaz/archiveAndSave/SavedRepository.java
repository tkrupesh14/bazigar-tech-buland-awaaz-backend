package com.bazigar.bulandawaaz.archiveAndSave;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedRepository extends JpaRepository<Savedposts, Long> {

    @Query(value = "select a from Savedposts a where a.user.id = ?1")
    public List<Savedposts> findByUserId(Long userId);

    @Query(value = "select s from Savedposts s where s.post_id = ?1")
    public Optional<Savedposts> findByPostId(Long postId);

    @Query(value = "select s from Savedposts s where s.type = ?2 and s.user.id = ?1")
    Page<Savedposts> findSavedByIdAndType(Long userId, int i, Pageable page);
}
