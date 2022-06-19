package com.bazigar.bulandawaaz.fcmpushnotifications.model;

public class PushNotificationRequest {


    private String title;
    private String message;
    private String topic;
    private String token;
    private String postUrl;
    private Long postId;
    private String userProfile;
    private String type;
    private Long userId;

    public PushNotificationRequest(String title, String message,
                                   String topic, String token, String postUrl,
                                   Long postId, String userProfile, String type) {
        this.title = title;
        this.message = message;
        this.topic = topic;
        this.token = token;
        this.postUrl = postUrl;
        this.postId = postId;
        this.userProfile = userProfile;
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PushNotificationRequest() {
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PushNotificationRequest(String title, String messageBody, String topicName) {
        this.title = title;
        this.message = messageBody;
        this.topic = topicName;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
