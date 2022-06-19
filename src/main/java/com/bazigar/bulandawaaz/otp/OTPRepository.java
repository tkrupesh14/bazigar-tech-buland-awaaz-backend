package com.bazigar.bulandawaaz.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {

    @Query(value = "select s from OTP s where s.id = ?1")
    public Optional<OTP> findOTPByOtpId(Long otpId);

    @Query(value = "select s from OTP s where s.userId = ?1")
    public Optional<OTP> findOTPByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "update OTP o set o.oneTimePassword = ?2 where o.id = ?1")
    public void updateOTPByOtpId(Long otpId, String OTP);

    @Query(value = "select s from OTP s where s.mobileNumber = ?1")
    public Optional<OTP> findOTPByPhoneNo(String phoneNo);


    @Query(value = "select s from OTP s where s.emailAddress = ?1")
    public Optional<OTP> findOTPByEmailAddress(String emailaddress);
}
