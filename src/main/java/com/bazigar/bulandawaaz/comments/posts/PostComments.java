package com.bazigar.bulandawaaz.comments.posts;

import com.bazigar.bulandawaaz.User.User;

import javax.persistence.*;

@Entity
public class PostComments {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long postId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String comment;
    private Long createdAt;
    private Boolean isVisible;
    private Long replyCount = 0L;
    private Long likeCount = 0L;
    private Boolean likedOrNot = false;

    public PostComments() {
    }

    public PostComments(Long postId, User user, String comment, Long createdAt, Boolean isVisible, Boolean likedOrNot) {
        this.postId = postId;
        this.user = user;
        this.comment = comment;
        this.createdAt = createdAt;
        this.isVisible = isVisible;
        this.likedOrNot = likedOrNot;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public Long getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Long replyCount) {
        this.replyCount = replyCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public String toString() {
        return "PostComments{" +
                "id=" + id +
                ", postId=" + postId +
                ", user=" + user +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", isVisible=" + isVisible +
                '}';
    }
}
