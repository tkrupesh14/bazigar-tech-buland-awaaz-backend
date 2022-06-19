package com.bazigar.bulandawaaz.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporaryOTPRepository extends JpaRepository<TemporaryOTP, Long> {

}
