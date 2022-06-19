package com.bazigar.bulandawaaz.comment_likes;

import org.springframework.lang.Nullable;

public class CommentLiker {

    private @Nullable
    Long userId;
    private @Nullable Long commentId;

    public CommentLiker() {
    }

    public CommentLiker(@Nullable Long userId, @Nullable Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    @Nullable
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(@Nullable Long commentId) {
        this.commentId = commentId;
    }
}
