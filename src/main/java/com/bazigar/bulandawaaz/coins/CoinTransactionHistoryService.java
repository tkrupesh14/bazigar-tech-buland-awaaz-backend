package com.bazigar.bulandawaaz.coins;

import com.bazigar.bulandawaaz.uploads.posts.Post;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.uploads.story.Story;
import com.bazigar.bulandawaaz.uploads.story.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CoinTransactionHistoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoinTransactionHistoryRepository coinTransactionHistoryRepository;

    @Autowired
    private PostRepository postRepository;



    @Autowired
    private StoryRepository storyRepository;

    public Response sendCoins(CoinTransactionHelper helper) {
        if (helper.getCoins() == null || helper.getOtherUserId() == null ||
            helper.getUserId() == null || helper.getPostId() == null ||
                helper.getType() == null) {
            return new Response(
                    "One or more of required fields - userId, otherUserId, coins, postId, " +
                            " and type - is missing!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(helper.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<User> otherUser = userRepository.findById(helper.getOtherUserId());
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        String type = helper.getType();
        if (Objects.equals(type, "post")) {
            Optional<Post> post = postRepository.findById(helper.getPostId());
            if (post.isEmpty()) {
                return new Response(
                        "No post found with the given postId!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            CoinTransactionHistory coinTransactionHistory = new CoinTransactionHistory(
                    user.get(),
                    otherUser.get(),
                    helper.getPostId(),
                    "post",
                    helper.getCoins(),
                    System.currentTimeMillis()
            );
            coinTransactionHistoryRepository.save(coinTransactionHistory);
            return new Response(
                    "Coins successfully sent!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (Objects.equals(type, "story")) {
            Optional<Story> story = storyRepository.findById(helper.getPostId());
            if (story.isEmpty()) {
                return new Response(
                        "No story found with the given postId!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            CoinTransactionHistory coinTransactionHistory = new CoinTransactionHistory(
                    user.get(),
                    otherUser.get(),
                    helper.getPostId(),
                    "story",
                    helper.getCoins(),
                    System.currentTimeMillis()
            );
            coinTransactionHistoryRepository.save(coinTransactionHistory);
            return new Response(
                    "Coins successfully sent!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "type has to be post, vibe, mptv or story!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }
}
