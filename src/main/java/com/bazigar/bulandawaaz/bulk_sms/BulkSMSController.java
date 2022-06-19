package com.bazigar.bulandawaaz.bulk_sms;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BulkSMSController {

    @Autowired
    private BulkSMSService bulkSMSService;

    @PostMapping("/send_whatsapp_otp")
    public Response sendWhatsappMessage(String phoneNo, String message) throws IOException {
        return bulkSMSService.sendOTP(phoneNo, message);
    }

}
