package com.bazigar.bulandawaaz.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/my-post-api/index.php/api/User")
public class EmailController {

    @Autowired
    private EmailOTPSender emailOTPSender;

//    @PostMapping("/sendEmailOTPForRegistration")
//    public Response sendOTPForRegistration(String emailId) {
//        return emailOTPSender.sendOTPEmailForRegistration(emailId);
//    }
//
//    @PostMapping("/sendEmailOTPForLogin")
//    public Response sendOTPForLogin(Long userId, String emailId) {
//        return emailOTPSender.sendOTPEmailForLogin(userId, emailId);
//    }
//
//    @PostMapping("/sendEmailOTPForPasswordReset")
//    public Response sendEmailOTPForPasswordReset(Long userId, String emailId) {
//        return emailOTPSender.sendOTPEmailForPasswordReset(userId, emailId);
//    }

}
