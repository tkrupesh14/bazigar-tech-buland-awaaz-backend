package com.bazigar.bulandawaaz.twilio;

import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Autowired
    private TwilioConfig twilioConfig;

    public Response sendOTP(String number, String message) {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
        PhoneNumber to = new PhoneNumber(number);
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
        MessageCreator created = Message.creator(
                to,
                "MG4023714d8f6382fef6c605ea12f31d40",
                message
        );
        created.create();
        return new Response(
                "OTP SMS successfully sent!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

}
