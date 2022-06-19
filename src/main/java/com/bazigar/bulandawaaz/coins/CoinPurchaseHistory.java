package com.bazigar.bulandawaaz.coins;

import com.bazigar.bulandawaaz.User.User;

import javax.persistence.*;

@Entity
public class CoinPurchaseHistory {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Long coins;
    private String amountSpent;
    private Long purchasedOn;

    public CoinPurchaseHistory(User user, Long coins, String amountSpent, Long purchasedOn) {
        this.user = user;
        this.coins = coins;
        this.amountSpent = amountSpent;
        this.purchasedOn = purchasedOn;
    }

    public Long getPurchasedOn() {
        return purchasedOn;
    }

    public void setPurchasedOn(Long purchasedOn) {
        this.purchasedOn = purchasedOn;
    }

    public CoinPurchaseHistory() {
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

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }

    public String getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(String amountSpent) {
        this.amountSpent = amountSpent;
    }
}
