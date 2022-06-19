package com.bazigar.bulandawaaz.comment_replies;

import org.springframework.lang.Nullable;

public class ReplyHelper {

    private @Nullable
    Long commentId;
    private @Nullable Long userId;
    private @Nullable String reply;

    public ReplyHelper() {
    }

    public ReplyHelper(@Nullable Long commentId, @Nullable Long userId, @Nullable String reply) {
        this.commentId = commentId;
        this.userId = userId;
        this.reply = reply;
    }

    public ReplyHelper(@Nullable Long commentId, @Nullable Long userId) {
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

    @Nullable
    public String getReply() {
        return reply;
    }

    public void setReply(@Nullable String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "ReplyHelper{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", reply='" + reply + '\'' +
                '}';
    }
}
