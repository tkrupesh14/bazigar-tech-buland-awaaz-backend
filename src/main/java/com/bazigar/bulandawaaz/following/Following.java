package com.bazigar.bulandawaaz.following;

import javax.persistence.*;

@Entity
public class Following {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long userId;
    private Long followingUserId;
    private Long createdAt;

    public Following() {
    }

    public Following(Long userId, Long followingUserId) {
        this.userId = userId;
        this.followingUserId = followingUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFollowingUserId() {
        return followingUserId;
    }

    public void setFollowingUserId(Long followingUserId) {
        this.followingUserId = followingUserId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Following{" +
                "id=" + id +
                ", userId=" + userId +
                ", followingUserId=" + followingUserId +
                '}';
    }
}
