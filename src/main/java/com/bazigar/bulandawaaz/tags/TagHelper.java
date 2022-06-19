package com.bazigar.bulandawaaz.tags;

import org.springframework.lang.Nullable;

public class TagHelper {

    private @Nullable
    Long userId;
    private @Nullable Long taggedUserId;
    private @Nullable Long postId;
    private @Nullable String type;
    private @Nullable String postUrl;

    public TagHelper(@Nullable Long userId, @Nullable Long taggedUserId, @Nullable Long postId, @Nullable String type, @Nullable String postUrl) {
        this.userId = userId;
        this.taggedUserId = taggedUserId;
        this.postId = postId;
        this.type = type;
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
    public Long getTaggedUserId() {
        return taggedUserId;
    }

    public void setTaggedUserId(@Nullable Long taggedUserId) {
        this.taggedUserId = taggedUserId;
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
    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(@Nullable String postUrl) {
        this.postUrl = postUrl;
    }

    @Override
    public String toString() {
        return "TagHelper{" +
                "userId=" + userId +
                ", taggedUserId=" + taggedUserId +
                ", postId=" + postId +
                ", type='" + type + '\'' +
                ", postUrl='" + postUrl + '\'' +
                '}';
    }
}
