package com.bazigar.bulandawaaz.comment_replies;

import com.bazigar.bulandawaaz.comment_replies.post.PostCommentReplyService;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/my-post-api/index.php/api/User")
public class ReplyController {

    @Autowired
    private PostCommentReplyService postCommentReplyService;



    @PostMapping("/add_post_comment_reply")
    public Response postCommentReplyAdd(ReplyHelper helper) {
        return postCommentReplyService.addReply(helper);
    }

    @PostMapping("/remove_post_comment_reply")
    public Response postCommentReplyRemove(ReplyRemover remover) {
        return postCommentReplyService.removeReply(remover);
    }



}
