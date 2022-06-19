package com.bazigar.bulandawaaz.comment_likes.post;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.comment_likes.CommentLiker;
import com.bazigar.bulandawaaz.comments.posts.PostComments;
import com.bazigar.bulandawaaz.comments.posts.PostCommentsRepository;
import com.bazigar.bulandawaaz.fcmpushnotifications.model.PushNotificationRequest;
import com.bazigar.bulandawaaz.fcmpushnotifications.service.PushNotificationService;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostCommentLikeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostCommentsRepository postCommentsRepository;

    @Autowired
    private PostCommentLikeRepository postCommentLikeRepository;

    @Autowired
    private PushNotificationService pushNotificationService;

    public Response likeOrDislike(CommentLiker liker) {
        if (liker.getUserId() == null || liker.getCommentId() == null) {
            return new Response(
                    "Both userId and commentId are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(liker.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given id!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<PostComments> postComments = postCommentsRepository.findById(liker.getCommentId());
        if (postComments.isEmpty()) {
            return new Response(
                    "No comment found with the given id!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Timestamp timer = Timestamp.from(Instant.now());
        Long likeCount = postComments.get().getLikeCount();
        // If comment has already been liked then dislike it
        List<PostCommentLike> postCommentLikeList = postCommentLikeRepository.findCommentsLikedByUserId(liker.getUserId());
        for (PostCommentLike commentLike: postCommentLikeList) {
            if (Objects.equals(commentLike.getPostComments().getId(), liker.getCommentId())) {
                postComments.get().setLikeCount(likeCount - 1);
                postCommentLikeRepository.delete(commentLike);
                return new Response(
                        "Comment disliked!",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        )
                );
            }
        }
        PostCommentLike like = new PostCommentLike(
                user.get(),
                postComments.get(),
                0L
        );
        like.setLikedAt(timer.getTime());
        postComments.get().setLikeCount(likeCount + 1);
        postCommentLikeRepository.save(like);



//        Optional<Post> post = postRepository.findById(postComments.get().getPostId());

        String title="Buland Awaaz",message=user.get().getUserName()+" liked your comment",topic="LikeComment",postUrl="";
//        if (post.get().getHlsUrl()==null)
//            postUrl=post.get().getPostUrl();
//        else
//            postUrl=post.get().getThumbUrl();
        String type="post",userProfile=user.get().getProfileUrl(),token=postComments.get().getUser().getFirebaseToken();
        Long postId=postComments.get().getPostId();


        PushNotificationRequest request=new PushNotificationRequest(title,message,topic,token,postUrl,postId,userProfile,type);
        request.setUserId(postComments.get().getUser().getId());
        pushNotificationService.sendPushNotificationToToken(request);

        return new Response(
                "Comment successfully liked!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Boolean commentLikedByUser(Long userId, Long postCommentId) {
        Optional<PostCommentLike> postCommentLike = postCommentLikeRepository.isLikedByUser(userId, postCommentId);
        if (postCommentLike.isPresent()) {
            return true;
        }
        return false;
    }
}
