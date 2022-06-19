package com.bazigar.bulandawaaz.User;

public class PersonalDetails {
    private Long userId;
    private String username = "";
    private String email = "";
    private String phoneNo = "";
    private String profileUrl = "";
    private String countryCode = "";

    public PersonalDetails(Long userId, String username, String email, String phoneNo, String profileUrl) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.profileUrl = profileUrl;
        this.countryCode = countryCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
