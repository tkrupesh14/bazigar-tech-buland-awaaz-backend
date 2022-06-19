package com.bazigar.bulandawaaz.comments;

public class CommentsPretty {

    private Long commentId;
    private Long postId;
    private Long userId;
    private String comment;
    private Long replyCount = 0L;
    private Long likeCount = 0L;
    private Boolean likedOrNot = false;

    public CommentsPretty() {
    }

    public CommentsPretty(Long commentId, Long postId, Long userId, String comment, Long replyCount, Long likeCount, Boolean likedOrNot) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
        this.replyCount = replyCount;
        this.likeCount = likeCount;
        this.likedOrNot = likedOrNot;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Boolean getLikedOrNot() {
        return likedOrNot;
    }

    public void setLikedOrNot(Boolean likedOrNot) {
        this.likedOrNot = likedOrNot;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}
