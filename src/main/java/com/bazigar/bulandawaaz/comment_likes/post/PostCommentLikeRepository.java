package com.bazigar.bulandawaaz.comment_likes.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostCommentLikeRepository extends
        JpaRepository<PostCommentLike, Long> {

    @Query(value = "select l from PostCommentLike l where l.user.id = ?1")
    List<PostCommentLike> findCommentsLikedByUserId(Long userId);

    @Query(value = "select l from PostCommentLike l where l.user.id = ?1 and l.postComments.id = ?2 ")
    Optional<PostCommentLike> isLikedByUser(Long userId, Long postCommentId);

}
