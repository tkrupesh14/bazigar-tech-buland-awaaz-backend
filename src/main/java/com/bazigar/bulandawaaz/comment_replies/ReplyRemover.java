package com.bazigar.bulandawaaz.comment_replies;

public class ReplyRemover {

    private Long replyId;
    private Long userId;

    public ReplyRemover() {
    }

    public ReplyRemover(Long replyId, Long userId) {
        this.replyId = replyId;
        this.userId = userId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
