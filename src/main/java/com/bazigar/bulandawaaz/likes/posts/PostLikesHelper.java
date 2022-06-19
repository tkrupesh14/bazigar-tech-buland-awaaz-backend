package com.bazigar.bulandawaaz.likes.posts;

public class PostLikesHelper {

    private Long postId;
    private Long userId;
    private Long likedAt;

    public PostLikesHelper(Long postId, Long userId, Long likedAt) {
        this.postId = postId;
        this.userId = userId;
        this.likedAt = likedAt;
    }

    public PostLikesHelper() {
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(Long likedAt) {
        this.likedAt = likedAt;
    }

    @Override
    public String toString() {
        return "PostLikesHelper{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", likedAt=" + likedAt +
                '}';
    }
}
