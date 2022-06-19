package com.bazigar.bulandawaaz.search_users;

public class SearchResult {

    private String userId;
    private String userName;
    private String fullName;
    private String profileUrl;
    private Boolean followingOrNot=false;

    public SearchResult() {
    }

    public SearchResult(String userId, String userName, String fullName, String profileUrl) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.profileUrl = profileUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Boolean getFollowingOrNot() {
        return followingOrNot;
    }

    public void setFollowingOrNot(Boolean followingOrNot) {
        this.followingOrNot = followingOrNot;
    }
}
