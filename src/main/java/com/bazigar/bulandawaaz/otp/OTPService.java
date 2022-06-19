package com.bazigar.bulandawaaz.otp;

import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class OTPService {

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private TemporaryOTPRepository temporaryOTPRepository;

    @Autowired
    private UserRepository userRepository;


    public Long sendOtpOnEmailAddress(String OTP, String email, String phoneNo) {
        if (OTP == null) {
            return 0L;
        }

        Optional<OTP> otpData = otpRepository.findOTPByEmailAddress(email);
        if (otpData.isPresent()) {
            OTP otp = otpData.get();
            Timestamp timer = Timestamp.from(Instant.now());
            otp.setUpdatedAt(timer.getTime());
            otpRepository.updateOTPByOtpId(otpData.get().getId(), OTP);
            return otp.getId();
        } else {
            OTP otp = new OTP(
                    phoneNo,
                    email,
                    OTP
            );
            Timestamp timer = Timestamp.from(Instant.now());
            otp.setUpdatedAt(timer.getTime());
            return otpRepository.save(otp).getId();
        }
    }

    public Long sendOtpOnMobileNumber(String OTP, String email, String phoneNo) {
        if (OTP == null) {
            return 0L;
        }
        Optional<OTP> otpData = otpRepository.findOTPByPhoneNo(phoneNo);
        if (otpData.isPresent()) {
            OTP otp = otpData.get();
            Timestamp timer = Timestamp.from(Instant.now());
            otp.setUpdatedAt(timer.getTime());
            otpRepository.updateOTPByOtpId(otpData.get().getId(), OTP);
            return otp.getId();
        } else {
            OTP otp = new OTP(
                    phoneNo,
                    email,
                    OTP
            );
            Timestamp timer = Timestamp.from(Instant.now());
            otp.setUpdatedAt(timer.getTime());
            return otpRepository.save(otp).getId();
        }
    }

    public Response addOTP(Long userId, String OTP, String emailId,String mobileNumber) {
        if (userId == null || OTP == null) {
            return new Response(
                    "Both userId and OTP is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<OTP> existingOTP = otpRepository.findOTPByUserId(userId);
        if (existingOTP.isPresent()) {
            otpRepository.updateOTPByOtpId(existingOTP.get().getId(), OTP);
            return new Response(
                    "OTP updated successfully!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        otpRepository.save(new OTP(
                mobileNumber,
                emailId,
                OTP,
                userId
        ));
        return new Response(
                "OTP added successfully!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

}
