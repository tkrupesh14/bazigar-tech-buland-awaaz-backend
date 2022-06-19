package com.bazigar.bulandawaaz.notifications;

import com.bazigar.bulandawaaz.User.User;

import javax.persistence.*;

@Entity
public class Notification {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String type; // available values are post, mptv, reels and story
    private Long postId;
    private String message;
    private Long createdAt;
    private String postUrl;

    public Notification() {
    }

    public Notification(User user, String type, Long postId, Long createdAt, String  message,
                        String postUrl) {
        this.user = user;
        this.type = type;
        this.postId = postId;
        this.createdAt = createdAt;
        this.message = message;
        this.postUrl=postUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
