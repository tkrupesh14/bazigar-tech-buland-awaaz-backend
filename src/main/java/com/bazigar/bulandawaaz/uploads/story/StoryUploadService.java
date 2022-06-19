package com.bazigar.bulandawaaz.uploads.story;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.following.Following;
import com.bazigar.bulandawaaz.following.FollowingRepository;
import com.bazigar.bulandawaaz.uploads.UploadMedia;
import com.bazigar.bulandawaaz.util.BunnyCDN;
import com.bazigar.bulandawaaz.util.PagedObject;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.hashtags.HashTagService;
import com.bazigar.bulandawaaz.mentions.MentionHelper;
import com.bazigar.bulandawaaz.mentions.MentionService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class StoryUploadService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private HashTagService hashTagService;

    @Autowired
    private MentionService mentionService;

    public Response uploadMedia(UploadMedia uploadMedia) throws IOException, JSONException {
        Optional<User> user = userRepository.findById(uploadMedia.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with this id!",
                    new ResponseStatus(
                            HttpStatus.CONFLICT.value(),
                            HttpStatus.CONFLICT.getReasonPhrase()
                    )
            );
        }

        MultipartFile media = uploadMedia.getUserFile();
        BunnyCDN bunny = new BunnyCDN();
//        System.out.println(media.getContentType());
//        System.out.println(media.getName());
//        System.out.println(media.getResource());
//        System.out.println(media.getSize());
//        System.out.println(media.getInputStream());
        Object value = bunny.uploadFile(media.getOriginalFilename(),
                "story", uploadMedia.getUserId().toString(), "USER", media);
        if (value == "Failed to create video for uploading" ||
                value == "Failed to upload image") {
            return new Response(
                    value.toString(),
                    new ResponseStatus(
                            HttpStatus.NOT_IMPLEMENTED.value(),
                            HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                    )
            );
        }
        else {
            Timestamp timer = Timestamp.from(Instant.now());
            if (value instanceof String) {
                Story story = new Story(
                    user.get(),
                    value.toString(),
                        uploadMedia.getLocation(),
                        "",
                        0,
                        1,
                        0L,
                        0,
                        uploadMedia.getCaption(),
                        "image"
                );
                story.setCreatedAt(timer.getTime());
                storyRepository.save(story);
                return new Response(
                        "Image added as story successfully!",
                        new ResponseStatus(
                                HttpStatus.CREATED.value(),
                                HttpStatus.CREATED.getReasonPhrase()
                        ),
                        value
                );
            }
            else {
                List<String> videoUrls = (List<String>) value;
                Story story = new Story(
                        user.get(),
                        videoUrls.get(0),
                        uploadMedia.getLocation(),
                        "",
                        0,
                        1,
                        0L,
                        0,
                        uploadMedia.getCaption(),
                        "video"
                );
                story.setCreatedAt(timer.getTime());
                storyRepository.save(story);
                return new Response(
                        "Video added to story!",
                        new ResponseStatus(
                                HttpStatus.CREATED.value(),
                                HttpStatus.CREATED.getReasonPhrase()
                        ),
                        value
                );
            }
        }
    }

    public Object fetchStories(Long userId, Integer pageNo) {
        int totalPages = 0;
        if (userId == null) {
            return new Response(
                    "userId is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (pageNo == null) {
            return new Response(
                    "Please provide a page starting at 0!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given id!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        List<Following> followingUsersList = followingRepository.findUsersFollowedByUserId(userId);
        ArrayList<Story> followingUserStories = new ArrayList<>();
        for (Following i: followingUsersList) {
            // Get stories of each followed user
            Pageable paged = PageRequest.of(pageNo, 15);
            Page<Story> storiesByThisUserPage = storyRepository.findByUserId(i.getFollowingUserId(), paged);
            totalPages = storiesByThisUserPage.getTotalPages();
            List<Story> storiesByThisUser = storiesByThisUserPage.toList();
            // Add these stories to followingUsersPosts
            followingUserStories.addAll(storiesByThisUser);
        }
        ArrayList<StorySimplified> followingStories = new ArrayList<>();
        for (Story i: followingUserStories) {
            followingStories.add(new StorySimplified(
                    i.getStoryId(),
                    i.getUser().getId(),
                    i.getStoryUrl(),
                    i.getLocation(),
                    i.getStoryViewer(),
                    i.getIsVisible(),
                    i.getHighlighted(),
                    i.getCreatedAt(),
                    i.getFlag(),
                    i.getUser().getProfileUrl(),
                    i.getUser().getUserName(),
                    i.getType()
            ));
        }
        if (followingStories.isEmpty()) {
            return new Response(
                    "It seems that you don't follow anybody!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
        Collections.shuffle(followingStories);
        return new Response(
                "Stories successfully fetched!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        followingStories.toArray(),
                        pageNo,
                        totalPages
                )
        );
    }

    public Response deleteYourStory(Long userId, Long storyId) {
        if (userId == null || storyId == null) {
            return new Response(
                    "userId and storyId is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Story> story = storyRepository.findById(storyId);
        if (story.isEmpty()) {
            return new Response(
                    "No story found with the given storyId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        if (userId.equals(story.get().getUser().getId())) {
            storyRepository.delete(story.get());
            return new Response(
                    "Story successfully deleted!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "You cannot delete somebody else's story!",
                new ResponseStatus(
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase()
                )
        );
    }

    public Response editStoryCaption(Long storyId, Long userId, String caption) {
        if (storyId == null || userId == null) {
            return new Response(
                    "storyId and userId are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (caption == null) {
            return new Response(
                    "caption is required and cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<Story> story = storyRepository.findById(storyId);
        if (story.isEmpty()) {
            return new Response(
                    "No story found with the given storyId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
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
        if (Objects.equals(story.get().getUser().getId(), userId)) {
            Pattern mentionPattern = Pattern.compile("@\\w+");
            for (String j: caption.split("\\s")) {
                if (mentionPattern.matcher(j).find()) {
                    String mentionedUserName = j.substring(1);
                    Optional<User> mentionedUser = userRepository.findUserByUserName(mentionedUserName);
                    if (mentionedUser.isPresent()) {
                        MentionHelper helper = new MentionHelper(
                                userId,
                                mentionedUser.get().getId(),
                                storyId,
                                "story"
                        );
                        mentionService.addMention(helper);
                    }
                }
            }
            storyRepository.updateStoryCaption(storyId, caption);
            return new Response(
                    "story caption successfully edited!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "You can only edit your own story!",
                new ResponseStatus(
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase()
                )
        );
    }
}
