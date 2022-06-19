package com.bazigar.bulandawaaz.following;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {

    @Query(value = "select s from Following s where s.userId = ?1")
    public List<Following> findUsersFollowedByUserId(Long userId);

    @Query(value = "select s from Following s where s.followingUserId = ?1")
    public List<Following> findUsersFollowingUserId(Long userId);

    @Query(value = "select s from Following s where s.userId = ?1")
    public Page<Following> getFollowedByUser(Long userId, Pageable pageable);

    @Query(value = "select s from Following s where s.followingUserId = ?1")
    public Page<Following> getFollowingByUser(Long userId,Pageable pageable);

}
