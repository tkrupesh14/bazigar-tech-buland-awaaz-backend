package com.bazigar.bulandawaaz.coins;

import com.bazigar.bulandawaaz.User.User;

import javax.persistence.*;

@Entity
public class CoinTransactionHistory {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "other_user_id", referencedColumnName = "id")
    private User otherUser;
    private Long postId;
    private String type;
    private Long coins;
    private Long createdAt;

    public CoinTransactionHistory(User user, User otherUser, Long postId, String type, Long coins, Long createdAt) {
        this.user = user;
        this.otherUser = otherUser;
        this.postId = postId;
        this.type = type;
        this.coins = coins;
        this.createdAt = createdAt;
    }

    public CoinTransactionHistory() {
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

    public User getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
