package com.bazigar.bulandawaaz.fcmpushnotifications.service;

import com.bazigar.bulandawaaz.fcmpushnotifications.firebase.FCMService;
import com.bazigar.bulandawaaz.notifications.NotificationHelper;
import com.bazigar.bulandawaaz.notifications.NotificationService;
import com.bazigar.bulandawaaz.fcmpushnotifications.model.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

    private String  topic = "common";
    private String  title = "title";
    private String  message = "message";
    private String  token = "dPM8p6tkTUCt5IE4StCYen:APA91bEyCCoSnFo4n5EeZ_c_gTMkB_OZ4RGIU-sIJnHU_O8zSmEil2ncIWKBvsW6P_6aVjdg26qhhRB1fQN-AfF4FoVXx9124kg2FW04gPl0xN328ZQlB8H-sSvcVTMFKGnOFZepEjiB";
    private String  payloadMessageId = "payloadMessageId";
    private String  payloadData = "payloadData";

//    @Value("#{${app.notifications.default}}")
    private Map<String, String> defaults;

    @Autowired
    private NotificationService notificationService;

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    public void sendPushNotification(PushNotificationRequest request) {
        try {
            fcmService.sendMessage(request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendNotificationToMultipleUsers(PushNotificationRequest request, List<String>tokens){
        for (String s:tokens){
            request.setToken(s);
            sendPushNotificationToToken(request);
        }

    }


    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
            NotificationHelper helper=new NotificationHelper();
            helper.setPostId(request.getPostId());
            helper.setMessage(request.getMessage());
            helper.setType(request.getType());
            helper.setUserId(request.getUserId());
            helper.setPostUrl(request.getPostUrl());
            notificationService.createNotification(helper);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }


    private Map<String, String> getSamplePayloadData(PushNotificationRequest request) {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("messageId",payloadMessageId);
        pushData.put("text", payloadData + " " + LocalDateTime.now());
        return pushData;
    }


    private PushNotificationRequest getSamplePushNotificationRequest() {
        PushNotificationRequest request = new PushNotificationRequest(title,
               message,
               topic);
        return request;
    }


}
