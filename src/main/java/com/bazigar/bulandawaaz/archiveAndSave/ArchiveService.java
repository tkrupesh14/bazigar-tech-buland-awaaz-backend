package com.bazigar.bulandawaaz.archiveAndSave;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.uploads.posts.Post;
import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.uploads.story.Story;
import com.bazigar.bulandawaaz.uploads.story.StoryRepository;

import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ArchiveService {

    @Autowired
    private PostRepository postRepository;



    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArchiveRepository archiveRepository;

    public Response archivePost(ArchiveHelper helper) {
        if (helper.getType() == null || helper.getPostId() == null ||
                helper.getUserId() == null) {
            return new Response(
                    "All of the fields are required!",
                    new ResponseStatus(
                            HttpStatus.NOT_ACCEPTABLE.value(),
                            HttpStatus.NOT_ACCEPTABLE.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(helper.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the provided id",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        if (helper.getType() == 1) {
            Optional<Post> post = postRepository.findById(helper.getPostId());
            if (post.isEmpty()) {
                return new Response(
                        "No post found with the given id!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            List<Archivepost> postsArchivedByUser = archiveRepository
                    .findByUserId(user.get().getId());
            ArrayList<Long> postIds = new ArrayList<Long>();
            for (Archivepost archivePost: postsArchivedByUser) {
                postIds.add(archivePost.getPost_id());
            }
            if (postIds.contains(helper.getPostId())) {
                int index = postIds.indexOf(helper.getPostId());
                Archivepost tobeDeleted = postsArchivedByUser.get(index);
                if (Objects.equals(tobeDeleted.getType(), helper.getType())) {
                    archiveRepository.delete(tobeDeleted);
                    return new Response(
                            "Archived post has been removed successfully!",
                            new ResponseStatus(
                                    HttpStatus.RESET_CONTENT.value(),
                                    HttpStatus.RESET_CONTENT.getReasonPhrase()
                            )
                    );
                }
            }
            Archivepost archivePost = new Archivepost(
                    user.get(),
                    helper.getType(),
                    helper.getPostId(),
                    helper.getPostUrl(),
                    System.currentTimeMillis()
            );
            archiveRepository.save(archivePost);
            return new Response(
                    "Post successfully archived!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (helper.getType() == 4) {
            Optional<Story> story = storyRepository.findById(helper.getPostId());
            if (story.isEmpty()) {
                return new Response(
                        "No story found with the given id!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            List<Archivepost> postsArchivedByUser = archiveRepository
                    .findByUserId(user.get().getId());
            ArrayList<Long> postIds = new ArrayList<Long>();
            for (Archivepost archivePost: postsArchivedByUser) {
                postIds.add(archivePost.getPost_id());
            }
            if (postIds.contains(helper.getPostId())) {
                int index = postIds.indexOf(helper.getPostId());
                Archivepost tobeDeleted = postsArchivedByUser.get(index);
                if (Objects.equals(tobeDeleted.getType(), helper.getType())) {
                    archiveRepository.delete(tobeDeleted);
                    return new Response(
                            "Archived story has been removed successfully!",
                            new ResponseStatus(
                                    HttpStatus.RESET_CONTENT.value(),
                                    HttpStatus.RESET_CONTENT.getReasonPhrase()
                            )
                    );
                }
            }
            Archivepost archivePost = new Archivepost(
                    user.get(),
                    helper.getType(),
                    helper.getPostId(),
                    helper.getPostUrl(),
                    System.currentTimeMillis()
            );
            archiveRepository.save(archivePost);
            return new Response(
                    "Story successfully archived!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "type must be between 1-4",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response fetchArchivedPosts(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        List<Archivepost> archiveposts = archiveRepository.findByUserId(userId);
        ArrayList<ArchiveHelper> userArchivedPosts = new ArrayList<>();
        for (Archivepost i: archiveposts) {
            userArchivedPosts.add(new ArchiveHelper(
                    i.getUser().getId(),
                    i.getType(),
                    i.getPost_id(),
                    i.getPostUrl()
            ));
        }
        return new Response(
                "You have "+userArchivedPosts.size()+" archivedPosts!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                userArchivedPosts
        );
    }
}
