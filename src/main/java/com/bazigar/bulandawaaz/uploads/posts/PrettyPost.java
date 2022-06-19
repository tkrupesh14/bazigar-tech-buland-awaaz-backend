package com.bazigar.bulandawaaz.uploads.posts;

public class PrettyPost {

    private Long postId;
    private String postUrl = "";
    private String hlsUrl = "";
    private String thumbUrl = "";
    private String userName = "";
    private String userProfilePic = "";
    private Long likeCount = 0L;
    private Long commentsCount = 0L;
    private Boolean savedOrNot = false;
    private String mentions = "";
    private String hashTags = "";
    private String caption = "";
    private Long createdAt;
    private Boolean likedOrNot = false;
    private String type = "";
    private String location = "";
    private Long userId;
    private Boolean followingOrNot=false;
    private Long viewCount=0L;
    private String city="";
    private String state="";
    private String fullName="";
    private Boolean verified=false;
    private Boolean isReporter=false;
    private Boolean isPopular=false;
    private String category="";

    public Boolean getPopular() {
        return isPopular;
    }

    public void setPopular(Boolean popular) {
        isPopular = popular;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getReporter() {
        return isReporter;
    }

    public void setReporter(Boolean reporter) {
        isReporter = reporter;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
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

    public Boolean getFollowingOrNot() {
        return followingOrNot;
    }

    public void setFollowingOrNot(Boolean followingOrNot) {
        this.followingOrNot = followingOrNot;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public PrettyPost(String type){
        this.type=type;
    }

    public PrettyPost(Long postId, String postUrl, String hlsUrl, String thumbUrl, String userName,
                      String userProfilePic, Long likeCount, Long commentsCount, Boolean savedOrNot, String mentions,
                      String hashTags, String caption, Long createdAt, Boolean likedOrNot, String type, String location,
                      Long userId,Boolean followingOrNot,Long viewCount,String city,String state,String fullName,
                      Boolean isReporter,Boolean verified,String category,Boolean isPopular) {
        this.postId = postId;
        this.postUrl = postUrl;
        this.hlsUrl = hlsUrl;
        this.thumbUrl = thumbUrl;
        this.userName = userName;
        this.userProfilePic = userProfilePic;
        this.likeCount = likeCount;
        this.commentsCount = commentsCount;
        this.savedOrNot = savedOrNot;
        this.mentions = mentions;
        this.hashTags = hashTags;
        this.caption = caption;
        this.createdAt = createdAt;
        this.likedOrNot = likedOrNot;
        this.type = type;
        this.location = location;
        this.userId=userId;
        this.followingOrNot=followingOrNot;
        this.viewCount=viewCount;
        this.city=city;
        this.state=state;
        this.fullName=fullName;
        this.isReporter=isReporter;
        this.verified=verified;
        this.category=category;
        this.isPopular=isPopular;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Boolean getSavedOrNot() {
        return savedOrNot;
    }

    public void setSavedOrNot(Boolean savedOrNot) {
        this.savedOrNot = savedOrNot;
    }

    public String getMentions() {
        return mentions;
    }

    public void setMentions(String mentions) {
        this.mentions = mentions;
    }

    public String getHashTags() {
        return hashTags;
    }

    public void setHashTags(String hashTags) {
        this.hashTags = hashTags;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
