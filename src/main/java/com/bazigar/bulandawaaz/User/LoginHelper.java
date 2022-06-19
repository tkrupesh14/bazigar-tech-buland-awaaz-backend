package com.bazigar.bulandawaaz.User;

import org.springframework.lang.Nullable;

public class LoginHelper {

    private @Nullable String nameEmailOrPhone;
    private @Nullable String password;
    private String profileUrl = "";
    private String bio = "";
    private String dob = "";
    private String gender = "unknown";
    private String firebaseToken = "";
    private Boolean flag = false;
    private Integer access = 0;
    private Boolean verified = false;
    private String latitude = "";
    private String longitude = "";
    private String ip = "";
    private String country = "";
    private String deviceName = "";
    private String address = "";
    private String city = "";
    private String signUpType = "";
    private String googleSignInToken = "";
    private String facebookSignInToken = "";

    public LoginHelper() {
    }

    public LoginHelper(@Nullable String nameEmailOrPhone, @Nullable String password, String profileUrl, String bio, String website, String dob, Integer accountType, String gender, String firebaseToken, Boolean flag, Integer access, String snapchatUrl, Boolean verified, String latitude, String longitude, String ip, String country, String deviceName, String address, String city) {
        this.nameEmailOrPhone = nameEmailOrPhone;
        this.password = password;
        this.profileUrl = profileUrl;
        this.bio = bio;
        this.dob = dob;
        this.gender = gender;
        this.firebaseToken = firebaseToken;
        this.flag = flag;
        this.access = access;
        this.verified = verified;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ip = ip;
        this.country = country;
        this.deviceName = deviceName;
        this.address = address;
        this.city = city;
    }

    @Nullable
    public String getNameEmailOrPhone() {
        return nameEmailOrPhone;
    }

    public void setNameEmailOrPhone(@Nullable String nameEmailOrPhone) {
        this.nameEmailOrPhone = nameEmailOrPhone;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSignUpType() {
        return signUpType;
    }

    public void setSignUpType(String signUpType) {
        this.signUpType = signUpType;
    }

    public String getGoogleSignInToken() {
        return googleSignInToken;
    }

    public void setGoogleSignInToken(String googleSignInToken) {
        this.googleSignInToken = googleSignInToken;
    }

    public String getFacebookSignInToken() {
        return facebookSignInToken;
    }

    public void setFacebookSignInToken(String facebookSignInToken) {
        this.facebookSignInToken = facebookSignInToken;
    }
}
