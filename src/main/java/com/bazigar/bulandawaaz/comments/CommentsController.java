package com.bazigar.bulandawaaz.comments;

import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.comments.posts.PostCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buland-awaaz/api/User")
public class CommentsController {

    @Autowired
    private PostCommentsService postCommentsService;



    @PostMapping("/add_post_comment")
    private Response addCommentToPost(CommentHelper helper) {
        return postCommentsService.addComment(helper);
    }

    @PostMapping("/delete_post_comment")
    private Response deletePostComment(CommentEditor editor) {
        return postCommentsService.deleteComment(editor);
    }





    @PostMapping("/getPostComments")
    private Response getCommentsOnPost(Long userId, Long postId) {
        return postCommentsService.getComments(userId, postId);
    }


}
