package com.bazigar.bulandawaaz.hashtags;

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
public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    @Query("select s from HashTag s where s.hashTag = ?1")
    public Optional<HashTag> findHashTagByHashtagName(String hashTag);

    @Transactional
    @Modifying
    @Query("update HashTag h set h.posts = ?1, h.postsCount = ?2 where h.id = ?3")
    public void updateHashTag(String posts, Long postsCount, Long tagId);

    @Query(value = "select s from HashTag s order by s.postsCount DESC")
    Page<HashTag> findAllHashTags(Pageable pageable);

    @Query(value = "select s from HashTag s where s.hashTag like ?1")
    List<HashTag> findHashTagByHashTag(String hashtag);

    @Query(nativeQuery = true, value = "select hash_tag.* from bulandawaaz.hash_tag  where hash_tag.hash_tag like ?1")
    Page<HashTag> findHashTagsByPage(String hashTag, Pageable pageable);

//    @Query
//    List<HashTag> findHashTagByHashTag(String hashTag);

//    @Query("ALTER TABLE HashTag h  ORDER BY h.postsCount desc;")
//    public void


}
