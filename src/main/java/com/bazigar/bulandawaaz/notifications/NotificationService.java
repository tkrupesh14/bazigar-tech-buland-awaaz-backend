package com.bazigar.bulandawaaz.notifications;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.uploads.posts.Post;
import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.uploads.story.StoryRepository;
import com.bazigar.bulandawaaz.util.PagedObject;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PostRepository postRepository;


    @Autowired
    private StoryRepository storyRepository;

    public Response createNotification(NotificationHelper helper) {
        if (helper.getUserId() == null || helper.getPostId() == null ||
            helper.getMessage() == null || helper.getType() == null) {
            return new Response(
                    "userId, message, postId and type are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (helper.getMessage().isEmpty()) {
            return new Response(
                    "message for notification cannot be empty",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(helper.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given id!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Timestamp timer = Timestamp.from(Instant.now());
            Optional<Post> post = postRepository.findById(helper.getPostId());

            if (post.isEmpty()&&helper.getPostId()!=-1) {
                return new Response(
                        "No post found with the given id!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            Notification notification = new Notification(
                    user.get(),
                    "post",
                    helper.getPostId(),
                    0L,
                    helper.getMessage(),
                    helper.getPostUrl()
            );
            notification.setCreatedAt(timer.getTime());
            notificationRepository.save(notification);
            Long notificationId = notification.getId();
            return new Response(
                "Notification successfully created!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ), notificationId
            );
//
//        else if (Objects.equals(helper.getType(), "story")) {
//            Optional<Story> story = storyRepository.findById(helper.getPostId());
//            if (story.isEmpty()) {
//                return new Response(
//                        "No story found with the given id!",
//                        new ResponseStatus(
//                                HttpStatus.NOT_FOUND.value(),
//                                HttpStatus.NOT_FOUND.getReasonPhrase()
//                        )
//                );
//            }
//            Notification notification = new Notification(
//                    user.get(),
//                    "story",
//                    helper.getPostId(),
//                    0L,
//                    helper.getMessage()
//            );
//            notification.setCreatedAt(timer.getTime());
//            notificationRepository.save(notification);
//            return new Response(
//                    "Notification successfully created!",
//                    new ResponseStatus(
//                            HttpStatus.OK.value(),
//                            HttpStatus.OK.getReasonPhrase()
//                    ),
//                    notification.getId()
//            );
//        }
//        else {
//            return new Response(
//                    "post, vibe and mptv are the accepted types!",
//                    new ResponseStatus(
//                            HttpStatus.BAD_REQUEST.value(),
//                            HttpStatus.BAD_REQUEST.getReasonPhrase()
//                    )
//            );
//        }
    }

    public Response getNotifications(Long userId, Integer pageNo) {
        if (userId == null) {
            return new Response(
                    "userId is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Pageable paged = PageRequest.of(pageNo, 25);
        Page<Notification> notificationsPage = notificationRepository.findByUserId(userId,paged);
        ArrayList<NotificationHelper> notificationForUser = new ArrayList<>();
        for (Notification i: notificationsPage.toList()) {
            notificationForUser.add(new NotificationHelper(
                    i.getUser().getId(),
                    i.getPostId(),
                    i.getType(),
                    i.getMessage(),
                    i.getPostUrl()
            ));

        }
        return new Response(
                "You have "+notificationForUser.size()+" notifications",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        notificationForUser.toArray(),
                        pageNo,
                        notificationsPage.getTotalPages()
                )

        );
    }
}
