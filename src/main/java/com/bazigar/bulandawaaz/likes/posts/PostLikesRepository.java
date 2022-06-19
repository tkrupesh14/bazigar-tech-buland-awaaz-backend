package com.bazigar.bulandawaaz.likes.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

    @Query(value = "select m from PostLikes m where m.user.id = ?1")
    public List<PostLikes> findByUserId(Long userId);

    @Query(value = "select m from PostLikes m where m.postId = ?1")
    public List<PostLikes> findByPostId(Long postId);

    @Query(value = "select m from PostLikes m where m.postId = ?1 and m.user.id = ?2")
    public Optional<PostLikes> isPostLikedByUser(Long postId, Long userId);

}
