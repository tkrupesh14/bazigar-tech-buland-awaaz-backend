package com.bazigar.bulandawaaz.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface SearchCategoryRepository extends JpaRepository<SearchCategory, Long> {

    @Query("select s from SearchCategory s where s.category = ?1")
    public Optional<SearchCategory> findCategoryByName(String category);

    @Transactional
    @Modifying
    @Query("update SearchCategory h set h.posts = ?1, h.postsCount = ?2 where h.id = ?3")
    public void updateCategory(String posts, Long postsCount, Long tagId);

    @Query(value = "select s from SearchCategory s order by s.postsCount DESC")
    List<SearchCategory> findAllCategory();

//    @Query(nativeQuery = true, value = "select hash_tag.* from bulandawaaz.hash_tag  where hash_tag.hash_tag regexp ?1")
//    Page<HashTag> findHashTagsByPage(String hashTag, Pageable pageable);
//
//    @Query
//    List<HashTag> findHashTagByHashTag(String hashTag);

//    @Query("ALTER TABLE HashTag h  ORDER BY h.postsCount desc;")
//    public void


}
