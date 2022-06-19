package com.bazigar.bulandawaaz.User;


import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String token = "";
    private @Nullable String userName;
    private @Nullable String fullName;
    private @Nullable String email = "";
    private @Nullable String phoneNo = "";
    private @Nullable String password;
    private String profileUrl = "";
    private String bio = "";
    private String dob = "";
    /*
    * 1 = public account
    * 2 = private account
    * 3 = creator account
    * */
    private Long createdAt;
    private Long updatedAt;
    private String gender = "unknown";
    private @Nullable String firebaseToken = "";
    private @Nullable Boolean flag = false;
    private @Nullable Integer access = 0;
    private @Nullable Boolean verified = false;
    private @Nullable String registrationOTP = "";
    private String fullPhoneNo = "";
    private @Nullable Double latitude=0.0;
    private @Nullable Double longitude=0.0;
    private String country="";
    private String city="";
    private String state="";
    private String deviceName="";
    private String signUpType = "";
    private @Nullable String googleSignInToken = "";
    private @Nullable  Boolean isReporter=false;
    private @Nullable Boolean isPopular=false;
    private Long followerCount=0L;
    private @Nullable String facebookSingInToken ="";
    private Boolean receiveNotification=false;

    public Boolean getReceiveNotification() {
        return receiveNotification;
    }

    public void setReceiveNotification(Boolean receiveNotification) {
        this.receiveNotification = receiveNotification;
    }

    public Boolean getPopular() {
        return isPopular;
    }

    public void setPopular(Boolean popular) {
        isPopular = popular;
    }

    public String getFacebookSingInToken() {
        return facebookSingInToken;
    }

    public void setFacebookSingInToken(String facebookSingInToken) {
        this.facebookSingInToken = facebookSingInToken;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    private String profession ="";

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

    public User(@Nullable String email, @Nullable String phoneNo, String registrationOTP) {
        this.email = email;
        this.phoneNo = phoneNo;
        this.registrationOTP = registrationOTP;
    }

    public User(String token, @Nullable String userName, @Nullable String fullName, @Nullable String email,
                @Nullable String phoneNo, @Nullable String password, String profileUrl, String bio, String dob, Long createdAt,
                Long updatedAt, String gender, String firebaseToken, Boolean flag, Integer access, Boolean verified,
                String registrationOTP, String fullPhoneNo, Double latitude, Double longitude, String country, String city,
                String state, String deviceName, String signUpType, String googleSignInToken,Boolean isReporter,Long followerCount,
                String profession,String facebookSingInToken) {
        this.token = token;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.phoneNo = phoneNo;
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
        this.registrationOTP = registrationOTP;
        this.fullPhoneNo = fullPhoneNo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.state = state;
        this.deviceName = deviceName;
        this.signUpType = signUpType;
        this.googleSignInToken = googleSignInToken;
        this.isReporter=isReporter;
        this.followerCount=followerCount;
        this.profession=profession;
        this.facebookSingInToken = facebookSingInToken;

    }

    public String getFullPhoneNo() {
        return fullPhoneNo;
    }

    public void setFullPhoneNo(String fullPhoneNo) {
        this.fullPhoneNo = fullPhoneNo;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Nullable
    public String getUserName() {
        return userName;
    }

    public void setUserName(@Nullable String userName) {
        this.userName = userName;
    }

    @Nullable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@Nullable String fullName) {
        this.fullName = fullName;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(@Nullable String phoneNo) {
        this.phoneNo = phoneNo;
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
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User() {
    }



    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getRegistrationOTP() {
        return registrationOTP;
    }

    public void setRegistrationOTP(String registrationOTP) {
        this.registrationOTP = registrationOTP;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo=" + phoneNo +
                ", password='" + password + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", bio='" + bio + '\'' +
                ", dob='" + dob + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", gender='" + gender + '\'' +
                ", firebaseToken='" + firebaseToken + '\'' +
                ", flag=" + flag +
                ", access=" + access +
                ", verified=" + verified +
                '}';
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Boolean getReporter() {
        return isReporter;
    }

    public void setReporter(Boolean reporter) {
        isReporter = reporter;
    }

    public Long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Long followerCount) {
        this.followerCount = followerCount;
    }
}
