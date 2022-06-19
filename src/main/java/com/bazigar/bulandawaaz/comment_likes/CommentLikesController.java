package com.bazigar.bulandawaaz.comment_likes;

import com.bazigar.bulandawaaz.comment_likes.post.PostCommentLikeService;
import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/buland-awaaz/api/User")
public class CommentLikesController {


    @Autowired
    private PostCommentLikeService postCommentLikeService;



    @PostMapping("/like_post_comment")
    public Response postCommentLikeOrDislike(CommentLiker liker) {
        return postCommentLikeService.likeOrDislike(liker);
    }


}
