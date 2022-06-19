package com.bazigar.bulandawaaz.mentions;

import org.springframework.lang.Nullable;

public class MentionHelper {

    private @Nullable
    Long userId;
    private @Nullable Long mentionedUserId;
    private @Nullable Long postId;
    private @Nullable String type;

    public MentionHelper() {
    }

    public MentionHelper(@Nullable Long userId, @Nullable Long mentionedUserId, @Nullable Long postId, @Nullable String type) {
        this.userId = userId;
        this.mentionedUserId = mentionedUserId;
        this.postId = postId;
        this.type = type;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    @Nullable
    public Long getMentionedUserId() {
        return mentionedUserId;
    }

    public void setMentionedUserId(@Nullable Long mentionedUserId) {
        this.mentionedUserId = mentionedUserId;
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
}
