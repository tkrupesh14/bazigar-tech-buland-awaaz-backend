package com.bazigar.bulandawaaz.storyview;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.uploads.story.Story;
import com.bazigar.bulandawaaz.uploads.story.StoryRepository;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StoryviewService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoryViewRepository storyViewRepository;

    public Response storyviewClicked(StoryviewHelper helper) {
        if (helper.getStoryId() == null || helper.getUserId() == null) {
            return new Response(
                    "storyId and userId are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<Story> story = storyRepository.findById(helper.getStoryId());
        if (story.isEmpty()) {
            return new Response(
                    "No story found with given storyId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(helper.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with given storyId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        if (Objects.equals(user.get().getId(), story.get().getUser().getId())) {
            return new Response(
                    "You viewed your own story!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        Timestamp timer = Timestamp.from(Instant.now());
        List<Storyview> storyviews = storyViewRepository.findByStoryId(helper.getStoryId());
        for (Storyview s: storyviews) {
            if (Objects.equals(s.getUserId(), helper.getUserId())) {
                return new Response(
                        user.get().getUserName()+" viewed your story again!",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        )
                );
            }
        }
        Storyview storyview = new Storyview(
                helper.getStoryId(),
                helper.getUserId()
        );
        storyview.setViewedAt(timer.getTime());
        List<Storyview> storyviews1 = storyViewRepository.findByStoryId(helper.getStoryId());
        int storyviewCount = storyviews1.size();
        story.get().setStoryViewer(""+(storyviewCount+1));
        storyViewRepository.save(storyview);
        return new Response(
                user.get().getUserName()+" viewed your story!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Response storyViewedBy(Long storyId) {
        if (storyId == null) {
            return new Response(
                    "storyId cannot be empty!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        List<Storyview> storyviews = storyViewRepository.findByStoryId(storyId);
        return new Response(
                "Your story was viewed by "+storyviews.size()+" people",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                storyviews
        );
    }
}
