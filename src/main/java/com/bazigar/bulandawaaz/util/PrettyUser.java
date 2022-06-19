package com.bazigar.bulandawaaz.util;

public class PrettyUser {

    private Long id;
    private String userName;
    private String fullName;
    private String profileUrl;
    private Boolean followingOrNot;

    public Boolean getFollowingOrNot() {
        return followingOrNot;
    }

    public void setFollowingOrNot(Boolean followingOrNot) {
        this.followingOrNot = followingOrNot;
    }

    public PrettyUser(Long id, String userName, String fullName, String profileUrl,Boolean followingOrNot) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.profileUrl = profileUrl;
        this.followingOrNot=followingOrNot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
