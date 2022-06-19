package com.bazigar.bulandawaaz.likes.posts;

import com.bazigar.bulandawaaz.User.UserDataWithoutPassword;
import com.bazigar.bulandawaaz.fcmpushnotifications.model.PushNotificationRequest;
import com.bazigar.bulandawaaz.fcmpushnotifications.service.PushNotificationService;
import com.bazigar.bulandawaaz.uploads.posts.Post;
import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.likes.LikesHelper;
import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostLikesService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikesRepository postLikesRepository;
    @Autowired
    private PushNotificationService pushNotificationService;

    public Response postLikeOrDislike(LikesHelper helper) {
        if (helper.getUserId() == null || helper.getPostId() == null) {
            return new Response(
                    "UserId and postId are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }

        Optional<User> user = userRepository.findById(helper.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with this id!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Post> post = postRepository.findById(helper.getPostId());
        if (post.isEmpty()) {
            return new Response(
                    "No post found with this id!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Timestamp timer = Timestamp.from(Instant.now());
        Long likeCount = post.get().getLikeCount();
        List<PostLikes> postsLikedByUser = postLikesRepository.findByUserId(helper.getUserId());
        for (PostLikes likes : postsLikedByUser) {
            if (Objects.equals(likes.getPostId(), helper.getPostId())) {
                post.get().setLikeCount(likeCount - 1);
                postLikesRepository.delete(likes);
                return new Response(
                        "Post disliked!",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        )
                );
            }
        }
        PostLikes postLikes = new PostLikes(
                helper.getPostId(),
                user.get(),
                0L
        );
        postLikes.setLikedAt(timer.getTime());
        post.get().setLikeCount(likeCount + 1);
        postLikesRepository.save(postLikes);
        Optional<User> user1 = userRepository.findById(post.get().getUser().getId());

        String title="Buland Awaaz",message=user.get().getUserName()+" liked your post.",topic="like",postUrl;
        if (post.get().getHlsUrl()==null)
            postUrl=post.get().getPostUrl();
        else
        postUrl=post.get().getThumbUrl();
        String type="post",userProfile=user1.get().getProfileUrl(),token=user1.get().getFirebaseToken();
        Long postId=post.get().getPostId();

        PushNotificationRequest request=new PushNotificationRequest(title,message,topic,token,postUrl,postId,userProfile,type);
            request.setUserId(user1.get().getId());
        pushNotificationService.sendPushNotificationToToken(request);

        return new Response(
                "Post liked!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Response getLiked(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        List<PostLikes> postLikes = postLikesRepository.findByUserId(userId);
        ArrayList<PostLikesHelper> postLikedByUser = new ArrayList<>();
        for (PostLikes i : postLikes) {
            postLikedByUser.add(new PostLikesHelper(
                    i.getPostId(),
                    i.getUser().getId(),
                    i.getLikedAt()
            ));
        }
        return new Response(
                "You have " + postLikedByUser.size() + " liked posts",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                postLikedByUser
        );
    }

    public Response getLikes(Long postId) {
        if (postId == null) {
            return new Response(
                    "postId is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new Response(
                    "No post found with the given postId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        List<PostLikes> postLikes = postLikesRepository.findByPostId(postId);
        ArrayList<UserDataWithoutPassword> users = new ArrayList<>();
        for (PostLikes i : postLikes) {
            User user = userRepository.findById(i.getUser().getId()).get();
            users.add(new UserDataWithoutPassword(
                    user.getId(),
                    user.getToken(),
                    user.getUserName(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getPhoneNo(),
                    user.getProfileUrl(),
                    user.getBio(),
                    user.getDob(),
                    user.getCreatedAt(),
                    user.getUpdatedAt(),
                    user.getGender(),
                    user.getFirebaseToken(),
                    user.getFlag(),
                    user.getAccess(),
                    user.getVerified(),
                    user.getDeviceName(),
                    user.getProfession()
            ));
        }
        return new Response(
                users.size() + " people liked your post",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                users
        );
    }

    public Boolean isPostLikedByUser(Long postId, Long userId) {
        Optional<PostLikes> postLikesOptional = postLikesRepository.isPostLikedByUser(postId, userId);
        if (postLikesOptional.isPresent()) {
            return true;
        }
        else {
            return false;
        }
    }
}
