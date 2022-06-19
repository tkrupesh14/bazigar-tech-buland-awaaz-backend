package com.bazigar.bulandawaaz.hide_post;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.uploads.posts.Post;
import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HidePostService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HidePostRepository hidePostRepository;

    @Autowired
    private PostRepository postRepository;


    public Response hidePost(HidePost post) {
        if (post.getPostId()==null||post.getUserId()==null) {
            return new Response(
                    "userId and postId is required is required and cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(post.getUserId());

        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId " + post.getUserId(),
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Post> post1 = postRepository.findById(post.getPostId());

        if (post1.isEmpty()) {
            return new Response(
                    "No post found with the given postId " + post.getPostId(),
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        hidePostRepository.save(post);
        return new Response(
                "Post hidden",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );

    }

    public Response fetchHiddenPost(Long userId) {
        if (userId==null) {
            return new Response(
                    "userId is required and cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId ",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        List<HidePost> posts=hidePostRepository.findHiddenPosts(userId);

        if (posts.isEmpty()) {
            return new Response(
                    "No Hidden Posts found with the given postId ",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }

        return new Response(
                "Hidden posts fetched successfully",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),posts
        );

    }
}
