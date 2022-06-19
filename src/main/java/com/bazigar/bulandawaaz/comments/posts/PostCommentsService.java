package com.bazigar.bulandawaaz.comments.posts;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.comment_likes.post.PostCommentLikeService;
import com.bazigar.bulandawaaz.comments.CommentEditor;
import com.bazigar.bulandawaaz.comments.CommentHelper;
import com.bazigar.bulandawaaz.comments.CommentsPretty;
import com.bazigar.bulandawaaz.fcmpushnotifications.model.PushNotificationRequest;
import com.bazigar.bulandawaaz.fcmpushnotifications.service.PushNotificationService;
import com.bazigar.bulandawaaz.uploads.posts.Post;
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
public class PostCommentsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentsRepository postCommentsRepository;

    @Autowired
    private PostCommentLikeService postCommentLikeService;

    @Autowired
    private PushNotificationService pushNotificationService;

    public Response addComment(CommentHelper helper) {
        if (helper.getComment() == null || helper.getPostId() == null
                || helper.getUserId() == null) {
            return new Response(
                    "Comment, userId and postId are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (helper.getComment().isEmpty()) {
            return new Response(
                    "Comment cannot be empty!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(helper.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with given id!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }

        Optional<Post> post = postRepository.findById(helper.getPostId());
        if (post.isEmpty()) {
            return new Response(
                    "No post found with given id!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Timestamp timer = Timestamp.from(Instant.now());
        PostComments postComments = new PostComments(
                helper.getPostId(),
                user.get(),
                helper.getComment(),
                0L,
                true,
                false
        );
        postComments.setCreatedAt(timer.getTime());
        Long commentCount = post.get().getCommentCount();
        post.get().setCommentCount(commentCount + 1);
        postCommentsRepository.save(postComments);




        String title="Buland Awaaz",message=user.get().getUserName()+" commented your post.",topic="comment",postUrl;
        if (post.get().getHlsUrl()==null)
            postUrl=post.get().getPostUrl();
        else
            postUrl=post.get().getThumbUrl();
        String type="post",userProfile=user.get().getProfileUrl(),token=post.get().getUser().getFirebaseToken();
        Long postId=post.get().getPostId();


        PushNotificationRequest request=new PushNotificationRequest(title,message,topic,token,postUrl,postId,userProfile,type);
        request.setUserId(post.get().getUser().getId());
        pushNotificationService.sendPushNotificationToToken(request);

        return new Response(
                "Comment successfully added to post!",
                new ResponseStatus(
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.getReasonPhrase()
                )
        );
    }

    public Response deleteComment(CommentEditor editor) {
        if (editor.getCommentId() == null || editor.getUserId() == null) {
            return new Response(
                    "Comment id and user id is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<PostComments> postComments = postCommentsRepository.findById(editor.getCommentId());
        if (postComments.isEmpty()) {
            return new Response(
                    "No comment found with the given id!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (!Objects.equals(postComments.get().getUser().getId(), editor.getUserId())) {
            return new Response(
                    "You can delete only your own comment!",
                    new ResponseStatus(
                            HttpStatus.FORBIDDEN.value(),
                            HttpStatus.FORBIDDEN.getReasonPhrase()
                    )
            );
        }
        Post post = postRepository.getById(postComments.get().getPostId());
        Long commentCount = post.getCommentCount();
        post.setCommentCount(commentCount - 1);
        postCommentsRepository.delete(postComments.get());
        return new Response(
                "Comment was successfully deleted!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Response getComments(Long userId, Long postId) {
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
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
        List<PostComments> postComments = postCommentsRepository.findByPostId(postId);
        ArrayList<CommentsPretty> commentsOnPost = new ArrayList<>();
        for (PostComments i: postComments) {
            commentsOnPost.add(new CommentsPretty(
                i.getId(),
                i.getPostId(),
                i.getUser().getId(),
                i.getComment(),
                i.getReplyCount(),
                i.getLikeCount(),
                postCommentLikeService.commentLikedByUser(userId, i.getId())
            ));
        }
        return new Response(
                commentsOnPost.size()+" comments",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                commentsOnPost
        );
    }
}
