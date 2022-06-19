package com.bazigar.bulandawaaz.comment_replies.post;

import com.bazigar.bulandawaaz.comment_replies.ReplyHelper;
import com.bazigar.bulandawaaz.comment_replies.ReplyRemover;
import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.comments.posts.PostComments;
import com.bazigar.bulandawaaz.comments.posts.PostCommentsRepository;
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
import java.util.Objects;
import java.util.Optional;

@Service
public class PostCommentReplyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostCommentsRepository postCommentsRepository;

    @Autowired
    private PostCommentReplyRepository postCommentReplyRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PushNotificationService pushNotificationService;

    public Response addReply(ReplyHelper helper) {
        if (helper.getReply() == null || helper.getCommentId() == null
                || helper.getUserId() == null) {
            return new Response(
                    "userId, commentId and reply are all required!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (helper.getReply().isEmpty()) {
            return new Response(
                    "reply cannot be empty!",
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
        Optional<PostComments> postComments = postCommentsRepository.findById(helper.getCommentId());
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
        PostCommentReply reply = new PostCommentReply(
                user.get(),
                helper.getReply(),
                0L,
                postComments.get()
        );
        reply.setCreatedAt(timer.getTime());
        Long replyCount = postComments.get().getReplyCount();
        postComments.get().setReplyCount(replyCount + 1);
        postCommentReplyRepository.save(reply);

        Optional<Post> post = postRepository.findById(postComments.get().getPostId());

        String title="Buland Awaaz",message=user.get().getUserName()+" replied to your comment",topic="commentReply",postUrl="";
        if (post.get().getHlsUrl()==null)
            postUrl=post.get().getPostUrl();
        else
            postUrl=post.get().getThumbUrl();
        String type="post",userProfile=user.get().getProfileUrl(),token=postComments.get().getUser().getFirebaseToken();
        Long postId=postComments.get().getPostId();


        PushNotificationRequest request=new PushNotificationRequest(title,message,topic,token,postUrl,postId,userProfile,type);
        request.setUserId(post.get().getUser().getId());
        pushNotificationService.sendPushNotificationToToken(request);


        return new Response(
                "Reply successfully added!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Response removeReply(ReplyRemover helper) {
        if (helper.getReplyId() == null
                || helper.getUserId() == null) {
            return new Response(
                    "replyId and userId are required!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
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
        Optional<PostCommentReply> reply = postCommentReplyRepository.findById(helper.getReplyId());
        if (reply.isEmpty()) {
            return new Response(
                    "No reply found with the given id!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        // Only the replier or the person on whose comment the reply was added
        // can delete the reply
        if (!Objects.equals(reply.get().getUser().getId(), helper.getUserId())
                && !Objects.equals(reply.get().getPostComments().getUser().getId(), helper.getUserId())) {
            return new Response(
                    "You can remove only your reply, or a reply on your comment!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<PostComments> postComment = postCommentsRepository.findById(reply.get().getPostComments().getId());
        if (postComment.isPresent()) {
            Long replyCount = postComment.get().getReplyCount();
            postComment.get().setReplyCount(replyCount - 1);
        }
        postCommentReplyRepository.delete(reply.get());
        return new Response(
                "Reply successfully removed!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }
}
