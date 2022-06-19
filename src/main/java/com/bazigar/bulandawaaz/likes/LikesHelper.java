package com.bazigar.bulandawaaz.likes;

import org.springframework.lang.Nullable;

public class LikesHelper {

    private @Nullable
    Long userId;
    private @Nullable Long postId;

    public LikesHelper() {
    }

    public LikesHelper(@Nullable Long userId, @Nullable Long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    @Nullable
    public Long getPostId() {
        return postId;
    }

    public void setPostId(@Nullable Long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "LikesHelper{" +
                "userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
