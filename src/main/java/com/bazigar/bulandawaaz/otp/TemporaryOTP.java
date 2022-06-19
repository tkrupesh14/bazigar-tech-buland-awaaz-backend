package com.bazigar.bulandawaaz.otp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TemporaryOTP {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String OTP;
    private String email = "";
    private String phoneNo = "";

    public TemporaryOTP() {
    }

    public TemporaryOTP(String OTP, String email, String phoneNo) {
        this.OTP = OTP;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
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
}
