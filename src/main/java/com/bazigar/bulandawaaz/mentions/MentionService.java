package com.bazigar.bulandawaaz.mentions;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.comments.posts.PostComments;
import com.bazigar.bulandawaaz.comments.posts.PostCommentsRepository;
import com.bazigar.bulandawaaz.uploads.posts.Post;
import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.uploads.story.Story;
import com.bazigar.bulandawaaz.uploads.story.StoryRepository;
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
public class MentionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MentionRepository mentionRepository;

    @Autowired
    private PostRepository postRepository;



    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private PostCommentsRepository postCommentsRepository;


    public Response addMention(MentionHelper helper) {
        if (helper.getMentionedUserId() == null
                || helper.getPostId() == null
                || helper.getUserId() == null
                || helper.getType() == null) {
            return new Response(
                    "userId, mentionedUserId, postId and type are required",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(helper.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<User> mentionedUser = userRepository.findById(helper.getMentionedUserId());
        if (mentionedUser.isEmpty()) {
            return new Response(
                    "No user found with given mentionedUserId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Timestamp timer = Timestamp.from(Instant.now());
        if (Objects.equals(helper.getType(), "post")) {
            Optional<Post> post = postRepository.findById(helper.getPostId());
            if (post.isEmpty()) {
                return new Response(
                        "No post found with given postId",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            Mention mention = new Mention(
                    helper.getUserId(),
                    helper.getMentionedUserId(),
                    helper.getPostId(),
                    0L,
                    "post"
            );
            mention.setCreatedAt(timer.getTime());
            mentionRepository.save(mention);
            return new Response(
                    "Mention successfully created!",
                    new ResponseStatus(
                            HttpStatus.CREATED.value(),
                            HttpStatus.CREATED.getReasonPhrase()
                    )
            );
        }
        else if (Objects.equals(helper.getType(), "story")) {
            Optional<Story> story = storyRepository.findById(helper.getPostId());
            if (story.isEmpty()) {
                return new Response(
                        "No story found with given postId",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            Mention mention = new Mention(
                    helper.getUserId(),
                    helper.getMentionedUserId(),
                    helper.getPostId(),
                    0L,
                    "story"
            );
            mention.setCreatedAt(timer.getTime());
            mentionRepository.save(mention);
            return new Response(
                    "Mention successfully created!",
                    new ResponseStatus(
                            HttpStatus.CREATED.value(),
                            HttpStatus.CREATED.getReasonPhrase()
                    )
            );
        }
        else if (Objects.equals(helper.getType(), "postComment")) {
            Optional<PostComments> postComment = postCommentsRepository.findById(helper.getPostId());
            if (postComment.isEmpty()) {
                return new Response(
                        "No postComment found with given postId",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            Mention mention = new Mention(
                    helper.getUserId(),
                    helper.getMentionedUserId(),
                    helper.getPostId(),
                    0L,
                    "postComment"
            );
            mention.setCreatedAt(timer.getTime());
            mentionRepository.save(mention);
            return new Response(
                    "Mention successfully created!",
                    new ResponseStatus(
                            HttpStatus.CREATED.value(),
                            HttpStatus.CREATED.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "post, vibe, story, postComment, vibeComment and mptvComment are the accepted types!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public List<String> prettyMentions(Long postId, String type) {
        List<Mention> mentions = mentionRepository.findMentionByPostIdAndType(postId, type);
        ArrayList<String> mentionsPretty = new ArrayList<>();
        for (Mention i: mentions) {
            Optional<User> user = userRepository.findById(i.getMentionedUserId());
            if (user.isPresent()) {
                mentionsPretty.add("@"+user.get().getUserName()+"-"+i.getMentionedUserId());
            }
        }
        return mentionsPretty;
    }


}
