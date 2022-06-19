package com.bazigar.bulandawaaz.util;

public class UserDataToReturn {
    private Long userId;

    public UserDataToReturn(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserDataToReturn{" +
                "userId=" + userId +
                '}';
    }
}
