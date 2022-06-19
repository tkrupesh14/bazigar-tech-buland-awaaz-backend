package com.bazigar.bulandawaaz.custom_ads;

import org.springframework.web.multipart.MultipartFile;

public class CustomAdHelper {
    private MultipartFile ad;
    private Double adCost;
    private Long userId;
    private String adDescription = "";
    private String adType = "post";
    private String mediaType = "image";

    public CustomAdHelper(MultipartFile ad, Double adCost, Long userId, String adDescription, String adType, String mediaType) {
        this.ad = ad;
        this.adCost = adCost;
        this.userId = userId;
        this.adDescription = adDescription;
        this.adType = adType;
        this.mediaType = mediaType;
    }

    public MultipartFile getAd() {
        return ad;
    }

    public void setAd(MultipartFile ad) {
        this.ad = ad;
    }

    public Double getAdCost() {
        return adCost;
    }

    public void setAdCost(Double adCost) {
        this.adCost = adCost;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
