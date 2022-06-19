package com.bazigar.bulandawaaz.following;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.fcmpushnotifications.model.PushNotificationRequest;
import com.bazigar.bulandawaaz.fcmpushnotifications.service.PushNotificationService;
import com.bazigar.bulandawaaz.util.*;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FollowingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private PushNotificationService pushNotificationService;

    public int getFollowersCount(Long userId) {
        return followingRepository.findUsersFollowingUserId(userId).size();
    }

    public int getFollowingCount(Long userId) {
        return followingRepository.findUsersFollowedByUserId(userId).size();
    }

    public Response addUserToFollowing(FollowHelper helper) {
        if (helper.getUserId() == null || helper.getOtherUserId() == null) {
            return new Response(
                    "userId and otherUserId are both required",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (Objects.equals(helper.getUserId(), helper.getOtherUserId())) {
            return new Response(
                    "You cannot follow yourself!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(helper.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<User> otherUser = userRepository.findById(helper.getOtherUserId());
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Timestamp timer = Timestamp.from(Instant.now());
        List<Following> usersFollowedByUserId = followingRepository
                .findUsersFollowedByUserId(helper.getUserId());
        for (Following following : usersFollowedByUserId) {
            if (Objects.equals(following.getFollowingUserId(), helper.getOtherUserId())) {
                followingRepository.delete(following);
                userRepository.updateUserFollowers(following.getFollowingUserId(),-1L);
                return new Response(
                        "You successfully unfollowed " + otherUser.get().getUserName(),
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        )
                );
            }
        }
        Following following = new Following(
                helper.getUserId(),
                helper.getOtherUserId()
        );
        following.setCreatedAt(timer.getTime());
        followingRepository.save(following);
        userRepository.updateUserFollowers(following.getFollowingUserId(),1L);


        String title="Buland Awaaz",message=user.get().getUserName()+" started following you",topic="follow",postUrl="";
//        if (.get().getHlsUrl()==null)
//            postUrl=post.get().getPostUrl();
//        else
//            postUrl=post.get().getThumbUrl();
        String type="user",userProfile=otherUser.get().getProfileUrl(),token=otherUser.get().getFirebaseToken();
        Long postId=-1L;



        PushNotificationRequest request=new PushNotificationRequest(title,message,topic,token,postUrl,postId,userProfile,type);
        request.setUserId(otherUser.get().getId());
        pushNotificationService.sendPushNotificationToToken(request);


        return new Response(
                "You have successfully followed " + otherUser.get().getUserName(),
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Response getAllFollowers(Long userId,Integer pageNo) {
        int totalpages=0;
        Pageable page = PageRequest.of(pageNo, 15);
        List<Following> followingsList=followingRepository.findUsersFollowedByUserId(userId);
        Page<Following> followersPage=followingRepository.getFollowingByUser(userId,page);

        totalpages=followersPage.getTotalPages();

        List<Following> followersList = followersPage.toList();
        ArrayList<User> followers = new ArrayList<>();
        for (Following user : followersList) {
            User user1 = userRepository.findById(user.getUserId()).get();
            followers.add(user1);
        }
        if (followers.isEmpty()) {
            return new Response(
                    "The given userId has no followers!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
        ArrayList<PrettyUser> prettyFollowers = new ArrayList<>();
        for (User i : followers) {
            boolean isFollowing=false;
            for (Following f:followingsList)
            if (Objects.equals(f.getFollowingUserId(), i.getId())){
                isFollowing=true;
                break;
            }
            prettyFollowers.add(new PrettyUser(
                    i.getId(),
                    i.getUserName(),
                    i.getFullName(),
                    i.getProfileUrl(),
                    isFollowing

            ));
        }
        return new Response(
                "Followers obtained successfully!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        prettyFollowers.toArray(),
                        pageNo,
                        totalpages
                )

        );
    }

    public Response getAllFollowing(Long userId,Integer pageNo) {

        int totalpages=0;
        Pageable page = PageRequest.of(pageNo, 15);
        Page<Following> followingPage=followingRepository.getFollowedByUser(userId,page);
        totalpages=followingPage.getTotalPages();

        List<Following> followingList = followingPage.toList();
        ArrayList<User> followings = new ArrayList<>();
        for (Following following : followingList) {
            User user = userRepository.findById(following.getFollowingUserId()).get();
            followings.add(user);
        }
        if (followings.isEmpty()) {
            return new Response(
                    "You don't seem to have any following!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
        ArrayList<PrettyUser> prettyFollowings = new ArrayList<>();
        for (User i : followings) {
            prettyFollowings.add(new PrettyUser(
                    i.getId(),
                    i.getUserName(),
                    i.getFullName(),
                    i.getProfileUrl(),
                    true
            ));
        }
        return new Response(
                "Following obtained successfully!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        prettyFollowings.toArray(),
                        pageNo,
                        totalpages
                )
        );
    }

    public List<PrettyUser> getAllFollowingList(Long userId) {
        List<Following> followingList = followingRepository.findUsersFollowedByUserId(userId);
        ArrayList<User> followings = new ArrayList<>();
        for (Following following : followingList) {
            User user = userRepository.findById(following.getFollowingUserId()).get();
            followings.add(user);
        }
        ArrayList<PrettyUser> prettyFollowings = new ArrayList<>();
        for (User i : followings) {
            prettyFollowings.add(new PrettyUser(
                    i.getId(),
                    i.getUserName(),
                    i.getFullName(),
                    i.getProfileUrl(),
                    true
            ));
        }
        return prettyFollowings;
    }

}
