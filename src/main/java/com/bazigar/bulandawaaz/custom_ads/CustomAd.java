package com.bazigar.bulandawaaz.custom_ads;

import com.bazigar.bulandawaaz.User.User;

import javax.persistence.*;

@Entity
public class CustomAd {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long adId;
    private String adUrl = "";
    private Double adCost;
    private Double remainingCost;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String adDescription = "";
    private String adType = "post";
    private String mediaType = "image";
    private Long adViews = 0L;
    private Long createdAt;

    public CustomAd() {
    }

    public CustomAd(String adUrl, Double adCost, User user, String adDescription, String adType, String mediaType, Long createdAt) {
        this.adUrl = adUrl;
        this.adCost = adCost;
        this.user = user;
        this.adDescription = adDescription;
        this.adType = adType;
        this.mediaType = mediaType;
        this.createdAt = createdAt;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public Double getAdCost() {
        return adCost;
    }

    public void setAdCost(Double adCost) {
        this.adCost = adCost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAdDescription() {
        return adDescription;
    }

    public void setAdDescription(String adDescription) {
        this.adDescription = adDescription;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAdViews() {
        return adViews;
    }

    public void setAdViews(Long adViews) {
        this.adViews = adViews;
    }

    public Double getRemainingCost() {
        return remainingCost;
    }

    public void setRemainingCost(Double remainingCost) {
        this.remainingCost = remainingCost;
    }
}
