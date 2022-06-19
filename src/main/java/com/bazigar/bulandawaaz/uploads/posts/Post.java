package com.bazigar.bulandawaaz.uploads.posts;

import com.bazigar.bulandawaaz.User.User;

import javax.persistence.*;

@Entity
public class Post {

    private @Id @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    Long postId;
    private String postUrl = "";
    private String hlsUrl = "";
    private String type = "";
    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private User user;
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
    private Long viewCount=0L;
    private String city="";
    private String state="";
    private String category="";



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Post() {
    }

    public Post(String postUrl, String hlsUrl, String type,
                User user, String caption, String location,
                String thumbUrl, String previewUrl, String videoId,
                String status, Integer flag, Long createdAt,Long viewCount,
                String city,String state,String category)  {
        this.postUrl = postUrl;
        this.hlsUrl = hlsUrl;
        this.type = type;
        this.user = user;
        this.caption = caption;
        this.location = location;
        this.thumbUrl = thumbUrl;
        this.previewUrl = previewUrl;
        this.videoId = videoId;
        this.status = status;
        this.flag = flag;
        this.createdAt = createdAt;
        this.viewCount=viewCount;
        this.city=city;
        this.state=state;
        this.category=category;
    }

    public Post(String postUrl, User user) {
        this.postUrl = postUrl;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", postUrl='" + postUrl + '\'' +
                ", hlsUrl='" + hlsUrl + '\'' +
                ", type='" + type + '\'' +
                ", user=" + user +
                ", caption='" + caption + '\'' +
                ", location='" + location + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", videoId='" + videoId + '\'' +
                ", status='" + status + '\'' +
                ", flag=" + flag +'\'' +
                ", createdAt=" + createdAt +'\'' +
                ", category=" + category +'\'' +
                ", viewCount=" + viewCount +
                '}';
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }



}
