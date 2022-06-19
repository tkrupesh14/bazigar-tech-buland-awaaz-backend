package com.bazigar.bulandawaaz.hashtags;


import com.bazigar.bulandawaaz.uploads.posts.Post;
import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.util.PagedObject;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HashTagService {

    @Autowired
    private PostRepository postRepository;


    @Autowired
    private HashTagRepository hashTagRepository;

//    @Value("${bazigar.password}")
//    private String bazigarPassword;

    public Response addOrUpdateHashtag(String hashtag, Long postId, String type) {
        if (hashtag == null || postId == null || type == null) {
            return new Response(
                    "hashtag, postId and type are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (hashtag.charAt(0) != '#') {
            return new Response(
                    "hashtag must start with #",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (type.equals("post")) {
            Optional<Post> post = postRepository.findById(postId);
            if (post.isEmpty()) {
                return new Response(
                        "No post found with the given postId!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
        }
        Optional<HashTag> hashTag = hashTagRepository.findHashTagByHashtagName(hashtag);
        if (hashTag.isPresent()) {
            Long currentPostsCount = hashTag.get().getPostsCount();
            String postsForTag = hashTag.get().getPosts();
            String[] postsList = postsForTag.split(", ");
            ArrayList<String> mutablePostsList = new ArrayList<>(Arrays.asList(postsList));
            if (Objects.equals(type, "post")) {
                if (mutablePostsList.contains("post-" + postId)) {
                    return new Response(
                            "This post has already been added to this hashtag!",
                            new ResponseStatus(
                                    HttpStatus.CONFLICT.value(),
                                    HttpStatus.CONFLICT.getReasonPhrase()
                            )
                    );
                }
                mutablePostsList.add("post-" + postId);
            }
            String updatedPosts = String.join(", ", mutablePostsList);
            hashTagRepository.updateHashTag(updatedPosts, currentPostsCount + 1L, hashTag.get().getId());
            return new Response(
                    "Hashtag updated!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        if (Objects.equals(type, "post")) {
            HashTag postTag = new HashTag(
                    hashtag,
                    1L,
                    "post-" + postId + ", "
            );
            hashTagRepository.save(postTag);
        }
        return new Response(
                "Hashtag created!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }
    public Response getHashTags(String password, Integer pageNo) {
        if (password == null) {
            return new Response(
                    "Password is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        } else {
            if (password.equals("123456")) { // todo : add compairing condition
                Pageable paged = PageRequest.of(pageNo, 25);
                Page<HashTag> hashTagsPage =  hashTagRepository.findAllHashTags(paged);
                List<HashTag> hashTags = hashTagsPage.toList();
                return new Response(
                        "Found " + hashTags.size() + " hashtags!",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        ),
                        new PagedObject(
                                hashTags.toArray(),
                                pageNo,
                                hashTagsPage.getTotalPages()
                        )
                );
            }
            return new Response(
                    "Wrong password!",
                    new ResponseStatus(
                            HttpStatus.UNAUTHORIZED.value(),
                            HttpStatus.UNAUTHORIZED.getReasonPhrase()
                    )
            );
        }
    }





    public Response getHashTagsByTag(String hashtag) {
        if (hashtag == null) {
            return new Response(
                    "hashTag is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        List<HashTag> hashTags=hashTagRepository.findHashTagByHashTag('%'+hashtag+'%');
        if (hashTags==null)
            hashTags=new ArrayList<>();


        return new Response(
                "found "+hashTags.size()+" hashTags",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                hashTags
        );
    }
}

