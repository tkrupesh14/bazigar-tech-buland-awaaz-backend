package com.bazigar.bulandawaaz.archiveAndSave;

import org.springframework.lang.Nullable;

public class ArchiveHelper {

    private @Nullable
    Long userId;
    private @Nullable Integer type;
    private @Nullable Long postId;
    private @Nullable String postUrl;

    public ArchiveHelper() {
    }

    public ArchiveHelper(@Nullable Long userId, @Nullable Integer type, @Nullable Long postId, @Nullable String postUrl) {
        this.userId = userId;
        this.type = type;
        this.postId = postId;
        this.postUrl = postUrl;
    }

    @Nullable
    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(@Nullable String postUrl) {
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
    public Integer getType() {
        return type;
    }

    public void setType(@Nullable Integer type) {
        this.type = type;
    }

    @Nullable
    public Long getPostId() {
        return postId;
    }

    public void setPostId(@Nullable Long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "ArchiveHelper{" +
                "userId=" + userId +
                ", type=" + type +
                ", postId=" + postId +
                '}';
    }
}
