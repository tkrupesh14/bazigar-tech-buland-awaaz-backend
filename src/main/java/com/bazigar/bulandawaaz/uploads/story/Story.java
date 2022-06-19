package com.bazigar.bulandawaaz.uploads.story;

import com.bazigar.bulandawaaz.User.User;

import javax.persistence.*;

@Entity
public class Story {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long storyId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String storyUrl = "";
    private String location = "";
    private String storyViewer = "";
    private String type = "";
    private String caption = "";
    private Integer isVisible = 0;
    private Integer highlighted = 0;
    private Long createdAt;
    private Integer flag = 0;

    public Story() {
    }

    public Story(User user, String storyUrl, String location,
                 String storyViewer, Integer isVisible, Integer highlighted,
                 Long createdAt, Integer flag, String caption, String type) {
        this.user = user;
        this.storyUrl = storyUrl;
        this.location = location;
        this.storyViewer = storyViewer;
        this.isVisible = isVisible;
        this.highlighted = highlighted;
        this.createdAt = createdAt;
        this.flag = flag;
        this.caption = caption;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Story{" +
                "storyId=" + storyId +
                ", user=" + user +
                ", storyUrl='" + storyUrl + '\'' +
                ", location='" + location + '\'' +
                ", storyViewer='" + storyViewer + '\'' +
                ", isVisible=" + isVisible +
                ", highlighted=" + highlighted +
                ", createdAt=" + createdAt +
                ", flag=" + flag +
                '}';
    }
}
