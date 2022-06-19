package com.bazigar.bulandawaaz.comments;

import org.springframework.lang.Nullable;

public class CommentHelper {

    private @Nullable
    Long userId;
    private @Nullable Long postId;
    private @Nullable String comment;

    public CommentHelper() {
    }

    public CommentHelper(@Nullable Long userId, @Nullable Long postId, @Nullable String comment) {
        this.userId = userId;
        this.postId = postId;
        this.comment = comment;
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

    @Nullable
    public String getComment() {
        return comment;
    }

    public void setComment(@Nullable String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentHelper{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
