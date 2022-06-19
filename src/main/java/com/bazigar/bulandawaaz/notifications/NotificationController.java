package com.bazigar.bulandawaaz.notifications;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/buland-awaaz/api/User")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create_notification")
    public Response createNotification(NotificationHelper helper) {
        return notificationService.createNotification(helper);
    }

    @PostMapping("/fetch_notifications")
    public Response fetchNotifications(Long userId,Integer pageNo) {
        return notificationService.getNotifications(userId,pageNo);
    }

}
