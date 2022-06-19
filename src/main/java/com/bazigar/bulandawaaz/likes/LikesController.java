package com.bazigar.bulandawaaz.likes;

import com.bazigar.bulandawaaz.likes.posts.PostLikesService;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/buland-awaaz/api/User")
public class LikesController {



    @Autowired
    private PostLikesService postLikesService;


    @PostMapping("/like_post")
    public Response likePost(LikesHelper helper) {
        return postLikesService.postLikeOrDislike(helper);
    }

    @PostMapping("fetch_liked_posts")
    public Response getLikedPosts(Long userId) {
        return postLikesService.getLiked(userId);
    }


    @PostMapping("/post_liked_by")
    public Response getUsersLikingThePost(Long postId) {
        return postLikesService.getLikes(postId);
    }


}
