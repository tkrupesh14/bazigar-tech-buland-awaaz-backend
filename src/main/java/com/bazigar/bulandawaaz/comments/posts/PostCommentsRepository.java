package com.bazigar.bulandawaaz.comments.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentsRepository extends JpaRepository<PostComments, Long> {

    @Query(value = "select p from PostComments p where p.postId = ?1")
    List<PostComments> findByPostId(Long postId);
}
