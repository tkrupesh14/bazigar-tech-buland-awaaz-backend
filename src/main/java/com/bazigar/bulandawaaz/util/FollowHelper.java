package com.bazigar.bulandawaaz.util;

import org.springframework.lang.Nullable;

public class FollowHelper {

    private @Nullable Long userId;
    private @Nullable Long otherUserId;

    public FollowHelper() {
    }

    public FollowHelper(@Nullable Long userId, @Nullable Long otherUserId) {
        this.userId = userId;
        this.otherUserId = otherUserId;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    @Nullable
    public Long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(@Nullable Long otherUserId) {
        this.otherUserId = otherUserId;
    }

    @Override
    public String toString() {
        return "FollowHelper{" +
                "userId=" + userId +
                ", otherUserId=" + otherUserId +
                '}';
    }
}
