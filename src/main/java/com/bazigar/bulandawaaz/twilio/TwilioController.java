package com.bazigar.bulandawaaz.twilio;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/my-post-api/index.php/api/User")
public class TwilioController {

    @Autowired
    private TwilioService twilioService;

    @PostMapping("/sendOTP")
    public Response sendOTP(String number, String message) {
        return twilioService.sendOTP(number, message);
    }
}
