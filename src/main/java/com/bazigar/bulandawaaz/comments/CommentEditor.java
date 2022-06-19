package com.bazigar.bulandawaaz.comments;

import org.springframework.lang.Nullable;

public class CommentEditor {

    private @Nullable
    Long commentId;
    private @Nullable Long userId;

    public CommentEditor() {
    }

    public CommentEditor(@Nullable Long commentId, @Nullable Long userId) {
        this.commentId = commentId;
        this.userId = userId;
    }

    @Nullable
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(@Nullable Long commentId) {
        this.commentId = commentId;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CommentEditor{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                '}';
    }
}
