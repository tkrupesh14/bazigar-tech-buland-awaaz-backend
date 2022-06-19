package com.bazigar.bulandawaaz.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/my-post-api/index.php/api/User")
public class PhoneController {

    @Autowired
    private PhoneOTPService phoneSMSsender;

//    @PostMapping("/sendSMSOTPForRegistration")
//    public Response sendOTPForRegistration(String phoneNo) {
//        return phoneSMSsender.sendOTPPhoneForRegistration(phoneNo);
//    }
//
//    @PostMapping("/sendSMSOTPForLogin")
//    public Response sendOTPForLogin(Long userId, String phoneNo) {
//        return phoneSMSsender.sendOTPPhoneForLogin(userId, phoneNo);
//    }
//
//    @PostMapping("/sendSMSOTPForPasswordReset")
//    public Response sendOTPForPasswordReset(Long userId, String phoneNo) {
//        return phoneSMSsender.sendOTPForPasswordReset(userId, phoneNo);
//    }

}
