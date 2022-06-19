package com.bazigar.bulandawaaz.fcmpushnotifications.firebase;

import com.google.firebase.messaging.*;
import com.bazigar.bulandawaaz.fcmpushnotifications.model.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {
    private String token = "fx-tueLoTUy-CmsbtoroLl:APA91bGlnVodficrDFeKt_J0FAn-ltWxhwyweje8KI5kpn0WdpMxSb_b81xWlNHRhMJ4WDbYIZ17MYVlPRpDlL1-Om3WwEpnfrYnnyXlR-FIy3PvfpX6mRUGOkWMhnHkdfNvwSagpPSZ";
    private Logger logger = LoggerFactory.getLogger(FCMService.class);
    private String title = "title";
    private String message = "message";
    private String topic = "topic";
private String url = "google.co.in";

    public void sendMessage(PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithData(request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message with data. Topic: " + request.getTopic() + ", " + response);
    }


    public void sendMessageToToken(PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + token     + ", " + response);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).putAllData(getDataAsMap(request)).setToken(request.getToken())
                .build();
    }

    private Message getPreconfiguredMessageWithData(PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).putAllData(getDataAsMap(request)).setTopic(request.getTopic())
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        Notification notification= new Notification(request.getTitle(),request.getMessage());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        new Notification(request.getTitle(),request.getMessage()));
    }


    private Map<String, String> getDataAsMap(PushNotificationRequest request) {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("title", request.getTitle());
        pushData.put("message", request.getMessage());
        pushData.put("topic", request.getTopic());
        pushData.put("token", request.getToken());
        pushData.put("postId", request.getPostId().toString());
        pushData.put("userProfile", request.getUserProfile());
        pushData.put("postUrl", request.getPostUrl());
        pushData.put("type", request.getType());
        pushData.put("userId", request.getUserId().toString());
        return pushData;
    }


}
