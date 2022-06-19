package com.bazigar.bulandawaaz.likes.posts;

import com.bazigar.bulandawaaz.User.User;

import javax.persistence.*;

@Entity
public class PostLikes {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long postId;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Long likedAt;

    public PostLikes() {
    }

    public PostLikes(Long postId, User user, Long likedAt) {
        this.postId = postId;
        this.user = user;
        this.likedAt = likedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(Long likedAt) {
        this.likedAt = likedAt;
    }

    @Override
    public String toString() {
        return "PostLikes{" +
                "id=" + id +
                ", postId=" + postId +
                ", user=" + user +
                ", likedAt=" + likedAt +
                '}';
    }
}
