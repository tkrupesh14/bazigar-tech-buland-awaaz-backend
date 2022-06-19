package com.bazigar.bulandawaaz.uploads.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select s from Post s where s.user.id = ?1")
    Page<Post> findByUserId(Long userId, Pageable pageable);

    @Query(value = "select s from Post s where s.category = ?1")
    List<Post> findByCategory(String category);

    @Query(value = "select s from Post s where s.user.id = ?1")
    List<Post> findPostByUserId(Long userId);

    @Query(value = "select s from Post s where s.city = ?1 or s.state = ?2")
    Page<Post> findByLocation(String city,String state, Pageable pageable);





    @Query(value = "select s from Post s  ") // todo:check later
    Page<Post> findByHashtag(String hashtag, Pageable pageable);

    @Query(value = "select s from Post s where s.postId= ?1 ")
    Page<Post> findByPostId(Long postId, Pageable pageable);

    @Query(value = "select s from Post s where s.postId= ?1 ")
    Optional<Post> findPostById(Long postId);


    @Transactional
    @Modifying
    @Query(value = "update Post p set p.caption = ?2 where p.postId = ?1")
    void updatePostCaption(Long postId, String caption);

    @Transactional
    @Modifying
    @Query(value = "update Post p set p.viewCount = (p.viewCount+?2) where p.postId = ?1")
    void updateViewCount(Long postId,Long increase);

    @Query(value = "select s from Post s where s.city = ?1 or s.state = ?2 order by s.viewCount DESC")
    Page<Post> getTrendingPosts(String city,String state, Pageable pageable);

}
