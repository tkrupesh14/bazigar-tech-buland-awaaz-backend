package com.bazigar.bulandawaaz.otp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OTP {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String mobileNumber;
    private String emailAddress;
    private String oneTimePassword;
    private Long userId;
    private Long updatedAt;

    public OTP() {
    }

    public OTP(String mobileNumber, String emailAddress, String oneTimePassword) {
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.oneTimePassword = oneTimePassword;
    }

    public OTP(String mobileNumber, String emailAddress, String oneTimePassword, Long userId) {
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.oneTimePassword = oneTimePassword;
        this.userId = userId;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }
}
