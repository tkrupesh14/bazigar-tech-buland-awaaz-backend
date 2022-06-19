package com.bazigar.bulandawaaz.uploads.story;

public class StorySimplified {

    private Long id;
    private Long userId;
    private String storyUrl = "";
    private String location = "";
    private String storyViewer = "";
    private Integer isVisible = 0;
    private Integer highlighted = 0;
    private Long createdAt;
    private Integer flag = 0;
    private String profileUrl = "";
    private String userName = "";
    private String type = "";
//done?
    public StorySimplified(Long id, Long userId, String storyUrl, String location, String storyViewer, Integer isVisible, Integer highlighted, Long createdAt, Integer flag, String profileUrl, String userName, String type) {
        this.id = id;
        this.userId = userId;
        this.storyUrl = storyUrl;
        this.location = location;
        this.storyViewer = storyViewer;
        this.isVisible = isVisible;
        this.highlighted = highlighted;
        this.createdAt = createdAt;
        this.flag = flag;
        this.profileUrl = profileUrl;
        this.userName = userName;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    public void setStoryUrl(String storyUrl) {
        this.storyUrl = storyUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStoryViewer() {
        return storyViewer;
    }

    public void setStoryViewer(String storyViewer) {
        this.storyViewer = storyViewer;
    }

    public Integer getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(Integer highlighted) {
        this.highlighted = highlighted;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
