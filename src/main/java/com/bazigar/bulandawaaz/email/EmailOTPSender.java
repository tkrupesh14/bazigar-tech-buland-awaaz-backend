package com.bazigar.bulandawaaz.email;

import com.bazigar.bulandawaaz.otp.OTPService;
import com.bazigar.bulandawaaz.util.HtmlMessages;
import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.otp.OTPForUnregisteredUserService;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import com.bazigar.bulandawaaz.util.UsefulFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class EmailOTPSender {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UsefulFunctions usefulFunctions;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPForUnregisteredUserService otpForUnregisteredUserService;

    @Autowired
    private OTPService otpService;

//    public Response sendOTPEmailForRegistration(UserDataProvider user) throws JSONException {
////        Optional<User> user = userRepository.findUserByEmail(emailId);
////        if (user.isEmpty()) {
////            return new Response(
////                    "No user found with the given emailId",
////                    new ResponseStatus(
////                            HttpStatus.NOT_FOUND.value(),
////                            HttpStatus.NOT_FOUND.getReasonPhrase()
////                    )
////            );
////        }
//        String OTP = usefulFunctions.OTPGenerator();
//        Long tempUserId = otpForUnregisteredUserService.addOTPForEmail(
//                user,
//                OTP
//        );
//        Response response = emailSender.sendEmail(
//                user.getEmail(),
//                "Dear user, your OTP for registration is "+OTP,
//                "Registration OTP"
//        );
//        response.setData(tempUserId);
//        return response;
//    }
//
//    public Response verifyRegistrationOTP(String email, String OTP) {
//        Optional<OTPForUnregisteredUser> user = otpForUnregisteredUserService.findOTPForUnregisteredUserByEmail(email);
//        if (user.isPresent()) {
//            if (Objects.equals(user.get().getOneTimePassword(), OTP)) {
//                return new Response(
//                        "You have successfully registered for bulandawaaz!",
//                        new ResponseStatus(
//                                HttpStatus.OK.value(),
//                                HttpStatus.OK.getReasonPhrase()
//                        )
//                );
//            }
//            else {
//                return new Response(
//                        "Invalid OTP!",
//                        new ResponseStatus(
//                                HttpStatus.BAD_REQUEST.value(),
//                                HttpStatus.BAD_REQUEST.getReasonPhrase()
//                        )
//                );
//            }
//        }
//        return new Response(
//                "No temporary user with email found!!",
//                new ResponseStatus(
//                        HttpStatus.NOT_FOUND.value(),
//                        HttpStatus.NOT_FOUND.getReasonPhrase()
//                )
//        );
//    }

    public Response sendOTPForRegistration(String emailId) {
        String OTP = usefulFunctions.OTPGenerator();
        Long userId = otpService.sendOtpOnEmailAddress(
                OTP,
                emailId, ""
        );
        Response response = emailSender.prepareAndSend(
                emailId,
                HtmlMessages.registerMessage(OTP),
                "Buland Awaaz Verification"
        );
        response.setData(userId);
        return response;
    }

    public Response sendOTPEmailForLogin(Long userId, String emailId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        String OTP = usefulFunctions.OTPGenerator();
        otpService.addOTP(
                userId,
                OTP,
                emailId,
                ""
        );
        return emailSender.prepareAndSend(
                emailId,
                HtmlMessages.logInMessage(OTP),
                "Login OTP"
        );
    }

    public Response sendOTPEmailForPasswordReset(Long userId, String emailId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        String OTP = usefulFunctions.OTPGenerator();
        otpService.addOTP(
                userId,
                OTP,
                emailId,
                ""
        );
        return emailSender.prepareAndSend(
                emailId,
                HtmlMessages.passwordResetMessage(OTP),
                "Password reset OTP"
        );
    }

}
