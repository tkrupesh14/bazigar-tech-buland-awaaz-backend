package com.bazigar.bulandawaaz.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface OTPForUnregisteredRepository extends JpaRepository<OTPForUnregisteredUser, Long> {

    @Transactional
    @Modifying
    @Query(value = "update OTPForUnregisteredUser o set o.oneTimePassword = ?2 where o.email = ?1")
    public void updateOTPByEmailId(String emailId, String OTP);

    @Transactional
    @Modifying
    @Query(value = "update OTPForUnregisteredUser o set o.oneTimePassword = ?2 where o.phoneNo = ?1")
    public void updateOTPByPhoneNo(String phoneNo, String OTP);

    @Query(value = "select s from OTPForUnregisteredUser s where s.email = ?1")
    public Optional<OTPForUnregisteredUser> findOTPForUnregisteredUserByEmail(String email);

    @Query(value = "select s from OTPForUnregisteredUser s where s.phoneNo = ?1")
    public Optional<OTPForUnregisteredUser> findOTPForUnregisteredUserByPhoneNo(String phoneNo);

}
