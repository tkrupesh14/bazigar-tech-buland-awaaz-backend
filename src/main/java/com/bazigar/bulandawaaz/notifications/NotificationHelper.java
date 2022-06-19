package com.bazigar.bulandawaaz.notifications;

import org.springframework.lang.Nullable;

public class NotificationHelper {

    private @Nullable
    Long userId;
    private @Nullable Long postId;
    private @Nullable String type;
    private @Nullable String message;
    private String postUrl;

    public NotificationHelper() {
    }

    public NotificationHelper(@Nullable Long userId, @Nullable Long postId, @Nullable String type, @Nullable String message,
                              String postUrl) {
        this.userId = userId;
        this.postId = postId;
        this.type = type;
        this.message = message;
        this.postUrl=postUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    @Nullable
    public Long getPostId() {
        return postId;
    }

    public void setPostId(@Nullable Long postId) {
        this.postId = postId;
    }

    @Nullable
    public String getType() {
        return type;
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }
}
