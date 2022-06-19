package com.bazigar.bulandawaaz.uploads.posts;

public class PostSimplified {

    private Long postId;
    private String postUrl = "";
    private String hlsUrl = "";
    private String type = "";
    private Long userId;
    private String caption = "";
    private String location = "";
    private String thumbUrl = "";
    private String previewUrl = "";
    private String videoId = "";
    private String status = "";
    private Integer flag = 0;
    private Long createdAt;
    private Long likeCount = 0L;
    private Long commentCount = 0L;
    private Integer isLiked = 0;
    private String profileUrl = "";
    private String userName = "";

    public PostSimplified(Long postId, String postUrl, String hlsUrl, String type, Long userId, String caption, String location, String thumbUrl, String previewUrl, String videoId, String status, Integer flag, Long createdAt, Long likeCount, Long commentCount, Integer isLiked, String profileUrl, String userName) {
        this.postId = postId;
        this.postUrl = postUrl;
        this.hlsUrl = hlsUrl;
        this.type = type;
        this.userId = userId;
        this.caption = caption;
        this.location = location;
        this.thumbUrl = thumbUrl;
        this.previewUrl = previewUrl;
        this.videoId = videoId;
        this.status = status;
        this.flag = flag;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isLiked = isLiked;
        this.profileUrl = profileUrl;
        this.userName = userName;
    }

    public PostSimplified(Long postId, String postUrl, String hlsUrl, String type, Long userId, String caption, String location, String thumbUrl, String previewUrl, String videoId, String status, Integer flag, Long createdAt, Long likeCount, Long commentCount) {
        this.postId = postId;
        this.postUrl = postUrl;
        this.hlsUrl = hlsUrl;
        this.type = type;
        this.userId = userId;
        this.caption = caption;
        this.location = location;
        this.thumbUrl = thumbUrl;
        this.previewUrl = previewUrl;
        this.videoId = videoId;
        this.status = status;
        this.flag = flag;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public Integer getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Integer isLiked) {
        this.isLiked = isLiked;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}
