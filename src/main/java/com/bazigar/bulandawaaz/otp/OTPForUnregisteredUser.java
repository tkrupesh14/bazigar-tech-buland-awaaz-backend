package com.bazigar.bulandawaaz.otp;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OTPForUnregisteredUser {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String email = "";
    private String phoneNo = "";
    private String oneTimePassword = "";
    private String token = "";
    private String userName;
    private String fullName;
    private String password;
    private String profileUrl = "";
    private String bio = "";
    private String website = "";
    private String dob = "";

    /*
     * 1 = public account
     * 2 = private account
     * 3 = creator account
     * */
    private Integer accountType = 1;
    private Long createdAt;
    private Long updatedAt;
    private String gender = "unknown";
    private String firebaseToken = "";
    private Boolean flag = false;
    private Integer access = 0;
    private String snapchatUrl = "";
    private Boolean verified = false;
    private String latitude = "";
    private String longitude = "";
    private String ip = "";
    private String country = "";
    private String deviceName = "";
    private String address = "";
    private String city = "";

    public OTPForUnregisteredUser() {
    }

    public OTPForUnregisteredUser(String email, String oneTimePassword, String phoneNo) {
        this.email = email;
        this.oneTimePassword = oneTimePassword;
        this.phoneNo = phoneNo;
    }

    public OTPForUnregisteredUser(String email, String phoneNo, String oneTimePassword, String token, String userName, String fullName, String password, String profileUrl, String bio, String website, String dob,  Integer accountType, Long createdAt, Long updatedAt, String gender, String firebaseToken, Boolean flag, Integer access, String snapchatUrl, Boolean verified) {
        this.email = email;
        this.phoneNo = phoneNo;
        this.oneTimePassword = oneTimePassword;
        this.token = token;
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.profileUrl = profileUrl;
        this.bio = bio;
        this.website = website;
        this.dob = dob;
        this.accountType = accountType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.gender = gender;
        this.firebaseToken = firebaseToken;
        this.flag = flag;
        this.access = access;
        this.snapchatUrl = snapchatUrl;
        this.verified = verified;
    }


    public OTPForUnregisteredUser(String email, String phoneNo, String otp, String s, String userName, String fullName, String password, String profileUrl, String bio, String dob, String s1, long l, long l1, String gender, String firebaseToken, Boolean flag, Integer access, Boolean verified, String latitude, String longitude, String ip, String country, String deviceName, String address, String city) {
        this.email = email;
        this.phoneNo = phoneNo;
        this.oneTimePassword = oneTimePassword;
        this.token = token;
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.profileUrl = profileUrl;
        this.bio = bio;
        this.dob = dob;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getSnapchatUrl() {
        return snapchatUrl;
    }

    public void setSnapchatUrl(String snapchatUrl) {
        this.snapchatUrl = snapchatUrl;
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
}
