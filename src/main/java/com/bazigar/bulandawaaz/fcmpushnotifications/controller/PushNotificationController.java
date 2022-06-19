package com.bazigar.bulandawaaz.fcmpushnotifications.controller;

import com.bazigar.bulandawaaz.fcmpushnotifications.model.PushNotificationRequest;
import com.bazigar.bulandawaaz.fcmpushnotifications.model.PushNotificationResponse;
import com.bazigar.bulandawaaz.fcmpushnotifications.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PushNotificationController {

    @Autowired
    private PushNotificationService pushNotificationService;


//
//    public PushNotificationController(PushNotificationService pushNotificationService) {
//        this.pushNotificationService = pushNotificationService;
//    }


    @PostMapping("/notification/token")
    public ResponseEntity sendTokenNotification(PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationToToken(request);

        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @PostMapping("/notification/data")
    public ResponseEntity sendDataNotification(PushNotificationRequest request) {
        pushNotificationService.sendPushNotification(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @PostMapping("/notification/multiple")
    public ResponseEntity sentToMultipleUsers(PushNotificationRequest request, List<String> tokens) {
        pushNotificationService.sendNotificationToMultipleUsers(request,tokens);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

}
