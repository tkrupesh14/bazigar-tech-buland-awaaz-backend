package com.bazigar.bulandawaaz.tags;

import com.bazigar.bulandawaaz.uploads.posts.Post;
import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.uploads.story.Story;
import com.bazigar.bulandawaaz.uploads.story.StoryRepository;

import com.bazigar.bulandawaaz.util.PagedObject;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;



    @Autowired
    private StoryRepository storyRepository;

    public Response tagUserOnPost(TagHelper helper) {
        if (helper.getUserId() == null || helper.getPostId() == null
                || helper.getType() == null || helper.getTaggedUserId() == null) {
            return new Response(
                    "userId, taggedUserId, type and postId are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(helper.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<User> taggedUser = userRepository.findById(helper.getTaggedUserId());
        if (taggedUser.isEmpty()) {
            return new Response(
                    "No user found with the given taggedUserId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        if (Objects.equals(helper.getType(), "post")) {
            Optional<Post> post = postRepository.findById(helper.getPostId());
            if (post.isEmpty()) {
                return new Response(
                        "No post found with the given postId",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            Tag tag = new Tag(
                    helper.getPostId(),
                    helper.getUserId(),
                    helper.getTaggedUserId(),
                    "post",
                    post.get().getPostUrl()
            );
            tagRepository.save(tag);
            return new Response(
                    "Tagged "+taggedUser.get().getUserName()+" successfully!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (Objects.equals(helper.getType(), "story")) {
            Optional<Story> story = storyRepository.findById(helper.getPostId());
            if (story.isEmpty()) {
                return new Response(
                        "No story found with the given postId",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            Tag tag = new Tag(
                    helper.getPostId(),
                    helper.getUserId(),
                    helper.getTaggedUserId(),
                    "story",
                    story.get().getStoryUrl()
            );
            tagRepository.save(tag);
            return new Response(
                    "Tagged "+taggedUser.get().getUserName()+" successfully!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "post, vibe and story are the only accepted types!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response getPostsWhereIAmTagged(Long userId, Integer pageNo) {
        if (userId == null || pageNo == null) {
            return new Response(
                    "userId and pageNo is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Pageable paged = PageRequest.of(pageNo, 15);
        Page<Tag> taggedPostsPage = tagRepository.findTagByUserId(userId, paged);
        List<Tag> taggedPosts = taggedPostsPage.toList();
        return new Response(
                taggedPosts.size()+" posts found where you have been tagged!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        taggedPosts.toArray(),
                        pageNo,
                        taggedPostsPage.getTotalPages()
                )
        );
    }
}
