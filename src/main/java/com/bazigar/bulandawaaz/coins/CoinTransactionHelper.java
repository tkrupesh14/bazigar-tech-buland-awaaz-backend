package com.bazigar.bulandawaaz.coins;

public class CoinTransactionHelper {
    private Long userId;
    private Long otherUserId;
    private Long postId;
    private String type;
    private Long coins;

    public CoinTransactionHelper(Long userId, Long otherUserId, Long postId, String type, Long coins) {
        this.userId = userId;
        this.otherUserId = otherUserId;
        this.postId = postId;
        this.type = type;
        this.coins = coins;
    }

    public CoinTransactionHelper() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(Long otherUserId) {
        this.otherUserId = otherUserId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }
}
