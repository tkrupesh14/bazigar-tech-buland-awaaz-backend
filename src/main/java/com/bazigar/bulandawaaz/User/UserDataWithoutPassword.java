package com.bazigar.bulandawaaz.User;

import org.springframework.lang.Nullable;

public class UserDataWithoutPassword {

    private Long id;
    private String token = "";
    private String userName = "";
    private String fullName = "";
    private String email = "";
    private String phoneNo = "";
    private String profileUrl = "";
    private String bio = "";
    private String dob = "";
    private String postCount = "";
    private String followersCont = "";
    private String followingCount = "";
    /*
     * 1 = public account
     * 2 = private account
     * 3 = creator account
     * */
    private Long createdAt;
    private Long updatedAt;
    private String gender = "unknown";
    private String firebaseToken = "";
    private Boolean flag = false;
    private Integer access = 0;
    private Boolean verified = false;
    private String signUpType = "";
    private String googleSignInToken = "";
    private String facebookSingInToken="";
    private Boolean isReporter = false;
    private Boolean isPopular = false;
    private Boolean followingOrNot=false;
    private String profession="";
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

    public Boolean getFollowingOrNot() {
        return followingOrNot;
    }

    public void setFollowingOrNot(Boolean followingOrNot) {
        this.followingOrNot = followingOrNot;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }



    public UserDataWithoutPassword(Long id, String token, String userName, String fullName, String email,
                                   String phoneNo, String profileUrl, String bio, String dob, Long createdAt,
                                   Long updatedAt, String gender, String firebaseToken, Boolean flag, Integer access,
                                   Boolean verified, String deviceName, String signUpType, String googleSignInToken,
                                   Boolean isReporter,String facebookSingInToken) {

        this.id = id;
        this.token = token;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.phoneNo = phoneNo;
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
        this.deviceName = deviceName;
        this.signUpType = signUpType;
        this.googleSignInToken = googleSignInToken;
        this.isReporter=isReporter;
        this.facebookSingInToken=facebookSingInToken;

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


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    private String deviceName = "";


    public UserDataWithoutPassword(Long id, String token, String userName, String fullName, String email, String phoneNo, String profileUrl, String bio, String website, String dob, Integer accountType, Long createdAt, Long updatedAt, String gender, String firebaseToken, Boolean flag, Integer access, String snapchatUrl, Boolean verified) {
        this.id = id;
        this.token = token;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.phoneNo = phoneNo;
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
    }

    public UserDataWithoutPassword(Long id, String token, String userName, String fullName, String email,
                                   String phoneNo, String profileUrl, String bio, String dob, Long createdAt,
                                   Long updatedAt, String gender, String firebaseToken, Boolean flag,
                                   Integer access, Boolean verified, String deviceName,String profession) {
        this.id = id;
        this.token = token;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.phoneNo = phoneNo;
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
        this.deviceName = deviceName;
        this.profession=profession;
    }


    public String getPostCount() {
        return postCount;
    }

    public void setPostCount(String postCount) {
        this.postCount = postCount;
    }

    public String getFollowersCont() {
        return followersCont;
    }

    public void setFollowersCont(String followersCont) {
        this.followersCont = followersCont;
    }

    public String getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(String followingCount) {
        this.followingCount = followingCount;
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

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "UserDataWithoutPassword{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo=" + phoneNo +
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

    public Boolean getReporter() {
        return isReporter;
    }

    public void setReporter(Boolean reporter) {
        isReporter = reporter;
    }
}
