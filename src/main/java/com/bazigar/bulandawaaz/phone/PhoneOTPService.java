package com.bazigar.bulandawaaz.phone;

import com.bazigar.bulandawaaz.otp.OTPForUnregisteredUserService;
import com.bazigar.bulandawaaz.otp.OTPService;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.UsefulFunctions;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.bulk_sms.BulkSMSService;
import com.bazigar.bulandawaaz.twilio.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PhoneOTPService {

    @Autowired
    private OTPService otpService;

    @Autowired
    private OTPForUnregisteredUserService otpForUnregisteredUserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TwilioService twilioService;

    @Autowired
    private UsefulFunctions usefulFunctions;

    @Autowired
    private BulkSMSService bulkSMSService;

//    public Response sendOTPPhoneForRegistration(UserDataProvider userDataProvider) throws JSONException {
//        String OTP = usefulFunctions.OTPGenerator();
//        Long tempUserId = otpForUnregisteredUserService.addOTPForPhoneNo(userDataProvider, OTP);
//        Response response = twilioService.sendOTP(
//                userDataProvider.getPhoneNo(),
//                "Your OTP for Mypost registration is "+OTP
//        );
//        response.setData(tempUserId);
//        return response;
//    }

    public Response sendOTPForRegistration(String phoneNo) throws IOException {
        String OTP = usefulFunctions.OTPGenerator();

        Long otpId = otpService.sendOtpOnMobileNumber(
                OTP,
                "",
                phoneNo
        );
        Response response = twilioService.sendOTP(
                phoneNo,
                "Your OTP for Mypost Registration is "+OTP
        );
//        Response response = bulkSMSService.sendOTP(
//                phoneNo,
//                "Your OTP for Mypost Registration is "+OTP
//        );
        response.setData(otpId);
        return response;
    }

    public Response sendOTPPhoneForLogin(Long userId, String phoneNo) {
        String otpVal = usefulFunctions.OTPGenerator();
        otpService.addOTP(userId, otpVal,"",phoneNo);
        return twilioService.sendOTP(
                phoneNo,
                "Your OTP for Mypost login is "+otpVal
        );
    }

    public Response sendOTPForPasswordReset(Long userId, String phoneNo) {
        String otpVal = usefulFunctions.OTPGenerator();
        otpService.addOTP(userId, otpVal,"",phoneNo);
        return twilioService.sendOTP(
                phoneNo,
                "Your OTP for Mypost password reset is "+otpVal
        );
    }
}
