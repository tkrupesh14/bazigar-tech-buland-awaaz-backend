package com.bazigar.bulandawaaz.settings;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.search_users.SearchResult;
import com.bazigar.bulandawaaz.uploads.posts.Post;
import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SettingsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private PostRepository postRepository;



    public Response blockUser(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String blockedUsers = userSettings.get().getBlockedAccounts();
            String[] blockedUserList = blockedUsers.split(",");
            ArrayList<String> mutableBlockedUserList = new ArrayList<>(Arrays.asList(blockedUserList));
            if (mutableBlockedUserList.contains(otherUserId.toString())) {
                return new Response(
                        "This user has already been blocked!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            mutableBlockedUserList.add(otherUserId.toString());
            String newBlockedUsersList = String.join(",",mutableBlockedUserList);
            System.out.println(newBlockedUsersList);
            settingsRepository.updateBlockedAccounts(userId, newBlockedUsersList);
            return new Response(
                    otherUser.get().getUserName()+" successfully blocked!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response unblockUser(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String blockedUsers = userSettings.get().getBlockedAccounts();
            String[] blockedUserList = blockedUsers.split(",");
            ArrayList<String> mutableBlockedUserList = new ArrayList<>(Arrays.asList(blockedUserList));
            mutableBlockedUserList.remove(otherUserId.toString());
            String newBlockedUsersList = String.join(",",mutableBlockedUserList);
            System.out.println(newBlockedUsersList);
            settingsRepository.updateBlockedAccounts(userId, newBlockedUsersList);
            return new Response(
                    otherUser.get().getUserName()+" successfully unblocked!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response hideStoryFromUser(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String usersForWhomStoriesAreHidden = userSettings.get().getHideStoryFrom();
            String[] storyHiddenList = usersForWhomStoriesAreHidden.split(",");
            ArrayList<String> mutableStoryHiddenList = new ArrayList<>(Arrays.asList(storyHiddenList));
            if (mutableStoryHiddenList.contains(otherUserId.toString())) {
                return new Response(
                        "Stories have already been hidden from this user!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            mutableStoryHiddenList.add(otherUserId.toString());
            String storyHidden = String.join(",", mutableStoryHiddenList);
            settingsRepository.updateHideStoryFromUsers(userId, storyHidden);
            return new Response(
                    "Story will be hidden for "+otherUser.get().getUserName()+"!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response unhideStoryFromUser(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String usersForWhomStoriesAreHidden = userSettings.get().getHideStoryFrom();
            String[] storyHiddenList = usersForWhomStoriesAreHidden.split(",");
            ArrayList<String> mutableStoryHiddenList = new ArrayList<>(Arrays.asList(storyHiddenList));
            mutableStoryHiddenList.remove(otherUserId.toString());
            String storyHidden = String.join(",", mutableStoryHiddenList);
            settingsRepository.updateHideStoryFromUsers(userId, storyHidden);
            return new Response(
                    "Story will be shown for "+otherUser.get().getUserName()+"!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response blockCommentsFrom(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String blockCommentsFrom = userSettings.get().getBlockCommentsFrom();
            String[] blockCommentsFromList = blockCommentsFrom.split(",");
            ArrayList<String> mutableBlockCommentsFromList = new ArrayList<>(Arrays.asList(blockCommentsFromList));
            if (mutableBlockCommentsFromList.contains(otherUserId.toString())) {
                return new Response(
                        "Comments have already been blocked for this user!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            mutableBlockCommentsFromList.add(otherUserId.toString());
            String blockedComments = String.join(",", mutableBlockCommentsFromList);
            settingsRepository.updateBlockCommentsFrom(userId, blockedComments);
            return new Response(
                    "Commenting blocked for "+otherUser.get().getUserName()+"!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }
    public Response unblockCommentsFrom(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String blockCommentsFrom = userSettings.get().getBlockCommentsFrom();
            String[] blockCommentsFromList = blockCommentsFrom.split(",");
            ArrayList<String> mutableBlockCommentsFromList = new ArrayList<>(Arrays.asList(blockCommentsFromList));
            mutableBlockCommentsFromList.remove(otherUserId.toString());
            String blockedComments = String.join(",", mutableBlockCommentsFromList);
            settingsRepository.updateBlockCommentsFrom(userId, blockedComments);
            return new Response(
                    "Commenting allowed for "+otherUser.get().getUserName()+"!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response muteUser(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String mutedAccounts = userSettings.get().getMutedAccounts();
            String[] mutedAccountsList = mutedAccounts.split(",");
            ArrayList<String> mutableMutedAccounts = new ArrayList<>(Arrays.asList(mutedAccountsList));
            if (mutableMutedAccounts.contains(otherUserId.toString())) {
                return new Response(
                        "This user has already been muted!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            mutableMutedAccounts.add(otherUserId.toString());
            String mutedAccountsFinal = String.join(",", mutableMutedAccounts);
            settingsRepository.updateMutedAccounts(userId, mutedAccountsFinal);
            return new Response(
                    otherUser.get().getUserName()+" has been muted!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response unmuteUser(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String mutedAccounts = userSettings.get().getMutedAccounts();
            String[] mutedAccountsList = mutedAccounts.split(",");
            ArrayList<String> mutableMutedAccounts = new ArrayList<>(Arrays.asList(mutedAccountsList));
            mutableMutedAccounts.remove(otherUserId.toString());
            String mutedAccountsFinal = String.join(",", mutableMutedAccounts);
            settingsRepository.updateMutedAccounts(userId, mutedAccountsFinal);
            return new Response(
                    otherUser.get().getUserName()+" has been unmuted!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response restrictAccount(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String restrictedAccounts = userSettings.get().getRestrictedAccounts();
            String[] restrictedAccountsList = restrictedAccounts.split(",");
            ArrayList<String> mutableRestrictedAccounts = new ArrayList<>(Arrays.asList(restrictedAccountsList));
            if (mutableRestrictedAccounts.contains(otherUserId.toString())) {
                return new Response(
                        "This user has already been restricted!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            mutableRestrictedAccounts.add(otherUserId.toString());
            String updatedRestrictedAccounts = String.join(",", mutableRestrictedAccounts);
            settingsRepository.updateRestrictedAccounts(userId, updatedRestrictedAccounts);
            return new Response(
                    otherUser.get().getUserName()+" has been restricted!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response unrestrictAccount(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String restrictedAccounts = userSettings.get().getRestrictedAccounts();
            String[] restrictedAccountsList = restrictedAccounts.split(",");
            ArrayList<String> mutableRestrictedAccounts = new ArrayList<>(Arrays.asList(restrictedAccountsList));
            mutableRestrictedAccounts.remove(otherUserId.toString());
            String updatedRestrictedAccounts = String.join(",", mutableRestrictedAccounts);
            settingsRepository.updateRestrictedAccounts(userId, updatedRestrictedAccounts);
            return new Response(
                    otherUser.get().getUserName()+" has been unrestricted!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response closeFriendAdd(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String closeFriends = userSettings.get().getCloseFriends();
            String[] closeFriendsList = closeFriends.split(",");
            ArrayList<String> mutableCloseFriends = new ArrayList<>(Arrays.asList(closeFriendsList));
            if (mutableCloseFriends.contains(otherUserId.toString())) {
                return new Response(
                        "This user is already a close friend!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            mutableCloseFriends.add(otherUserId.toString());
            String updatedCloseFriends = String.join(",", mutableCloseFriends);
            settingsRepository.updateCloseFriends(userId, updatedCloseFriends);
            return new Response(
                    otherUser.get().getUserName()+" has been added to close friends!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response closeFriendRemove(Long userId, Long otherUserId) {
        if (userId == null || otherUserId == null) {
            return new Response(
                    "Both userId and otherUserId are required!",
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
        Optional<User> otherUser = userRepository.findById(otherUserId);
        if (otherUser.isEmpty()) {
            return new Response(
                    "No user found with the given otherUserId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            String closeFriends = userSettings.get().getCloseFriends();
            String[] closeFriendsList = closeFriends.split(",");
            ArrayList<String> mutableCloseFriends = new ArrayList<>(Arrays.asList(closeFriendsList));
            mutableCloseFriends.remove(otherUserId.toString());
            String updatedCloseFriends = String.join(",", mutableCloseFriends);
            settingsRepository.updateCloseFriends(userId, updatedCloseFriends);
            return new Response(
                    otherUser.get().getUserName()+" has been removed from close friends!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response addToLikedPosts(Long userId, String type, Long postId) {
        if (userId == null || postId == null || type == null) {
            return new Response(
                    "userId, type and postId are required!",
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
            Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
            if (userSettings.isPresent()) {
                String postsYouHaveLiked = userSettings.get().getPostsYouHaveLiked();
                String[] likedPostsList = postsYouHaveLiked.split(",");
                ArrayList<String> mutableLikedPosts = new ArrayList<>(Arrays.asList(likedPostsList));
                mutableLikedPosts.add("post - "+postId);
                String updatedLikedPosts = String.join(",", mutableLikedPosts);
                settingsRepository.updatePostsYouHaveLiked(userId, updatedLikedPosts);
                return new Response(
                        "Post has been successfully added to liked posts!",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        )
                );
            }
            return new Response(
                    "Settings not created for user!",
                    new ResponseStatus(
                            HttpStatus.NOT_IMPLEMENTED.value(),
                            HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                    )
            );
        }

        else {
            return new Response(
                    "type can only be post, vibe or mptv",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response removeFromLikedPosts(Long userId, String type, Long postId) {
        if (userId == null || postId == null || type == null) {
            return new Response(
                    "userId, type and postId are required!",
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
            Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
            if (userSettings.isPresent()) {
                String postsYouHaveLiked = userSettings.get().getPostsYouHaveLiked();
                String[] likedPostsList = postsYouHaveLiked.split(",");
                ArrayList<String> mutableLikedPosts = new ArrayList<>(Arrays.asList(likedPostsList));
                mutableLikedPosts.remove("post - "+postId);
                String updatedLikedPosts = String.join(",", mutableLikedPosts);
                settingsRepository.updatePostsYouHaveLiked(userId, updatedLikedPosts);
                return new Response(
                        "Post has been successfully removed from liked posts!",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        )
                );
            }
            return new Response(
                    "Settings not created for user!",
                    new ResponseStatus(
                            HttpStatus.NOT_IMPLEMENTED.value(),
                            HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                    )
            );
        }

        else {
            return new Response(
                    "type can only be post, vibe or mptv",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response updateWhoCanLikeYourPostsAndComments(Long userId, Integer likeCategory) {
        if (userId == null || likeCategory == null) {
            return new Response(
                    "Both userId and likeCategory are required!",
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
        if (likeCategory == 0) {
            settingsRepository.updateWhoCanLikeYourPostsAndComments(userId, likeCategory);
            return new Response(
                    "Like category updated to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (likeCategory == 1) {
            settingsRepository.updateWhoCanLikeYourPostsAndComments(userId, likeCategory);
            return new Response(
                    "Like category updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (likeCategory == 2) {
            settingsRepository.updateWhoCanLikeYourPostsAndComments(userId, likeCategory);
            return new Response(
                    "Like category updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "likeCategory has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response updateWhoCanLikeOnPostsYouAreTaggedIn(Long userId, Integer likeCategory) {
        if (userId == null || likeCategory == null) {
            return new Response(
                    "Both userId and likeCategory are required!",
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
        if (likeCategory == 0) {
            settingsRepository.updateWhoCanLikeOnPostsYouAreTaggedIn(userId, likeCategory);
            return new Response(
                    "Liking on your photos updated to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (likeCategory == 1) {
            settingsRepository.updateWhoCanLikeOnPostsYouAreTaggedIn(userId, likeCategory);
            return new Response(
                    "Liking on your photos updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (likeCategory == 2) {
            settingsRepository.updateWhoCanLikeOnPostsYouAreTaggedIn(userId, likeCategory);
            return new Response(
                    "Liking on your photos updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "likeCategory has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response taggedNotifications(Long userId, Integer whoCanTagMe) {
        if (userId == null || whoCanTagMe == null) {
            return new Response(
                    "Both userId and whoCanTagMe are required!",
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
        if (whoCanTagMe == 0) {
            settingsRepository.taggedNotifications(userId, whoCanTagMe);
            return new Response(
                    "Tagging you updated to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (whoCanTagMe == 1) {
            settingsRepository.taggedNotifications(userId, whoCanTagMe);
            return new Response(
                    "Tagging you updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (whoCanTagMe == 2) {
            settingsRepository.taggedNotifications(userId, whoCanTagMe);
            return new Response(
                    "Tagging you updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "whoCanTagMe has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response updateCommentNotifications(Long userId, Integer notificationType) {
        if (userId == null || notificationType == null) {
            return new Response(
                    "Both userId and notificationType are required!",
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
        if (notificationType == 0) {
            settingsRepository.updateCommentNotifications(userId, notificationType);
            return new Response(
                    "Comment notifications to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (notificationType == 1) {
            settingsRepository.updateCommentNotifications(userId, notificationType);
            return new Response(
                    "Comment notifications updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (notificationType == 2) {
            settingsRepository.updateCommentNotifications(userId, notificationType);
            return new Response(
                    "Comment notifications updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "notificationType has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response updateFirstPostAndStoriesNotifications(Long userId, Integer notificationType) {
        if (userId == null || notificationType == null) {
            return new Response(
                    "Both userId and notificationType are required!",
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
        if (notificationType == 0) {
            settingsRepository.updateFirstPostAndStoriesNotifications(userId, notificationType);
            return new Response(
                    "First post and stories notifications updated to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (notificationType == 1) {
            settingsRepository.updateFirstPostAndStoriesNotifications(userId, notificationType);
            return new Response(
                    "first post and stories notifications updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (notificationType == 2) {
            settingsRepository.updateFirstPostAndStoriesNotifications(userId, notificationType);
            return new Response(
                    "first post and stories notifications updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "notificationType has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response updateMentionInBioNotifications(Long userId, Integer notificationType) {
        if (userId == null || notificationType == null) {
            return new Response(
                    "Both userId and notificationType are required!",
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
        if (notificationType == 0) {
            settingsRepository.updateMentionInBioNotifications(userId, notificationType);
            return new Response(
                    "Mention in bio notifications updated to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (notificationType == 1) {
            settingsRepository.updateMentionInBioNotifications(userId, notificationType);
            return new Response(
                    "Mention in bio notifications updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (notificationType == 2) {
            settingsRepository.updateMentionInBioNotifications(userId, notificationType);
            return new Response(
                    "Mention in bio notifications updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "notificationType has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response updateVideoChatNotifications(Long userId, Integer notificationType) {
        if (userId == null || notificationType == null) {
            return new Response(
                    "Both userId and notificationType are required!",
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
        if (notificationType == 0) {
            settingsRepository.updateVideoChatNotifications(userId, notificationType);
            return new Response(
                    "Video chat notifications updated to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (notificationType == 1) {
            settingsRepository.updateVideoChatNotifications(userId, notificationType);
            return new Response(
                    "Video chat notifications updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (notificationType == 2) {
            settingsRepository.updateVideoChatNotifications(userId, notificationType);
            return new Response(
                    "Video chat notifications updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "notificationType has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response updateRoomsNotifications(Long userId, Integer notificationType) {
        if (userId == null || notificationType == null) {
            return new Response(
                    "Both userId and notificationType are required!",
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
        if (notificationType == 0) {
            settingsRepository.updateRoomsNotifications(userId, notificationType);
            return new Response(
                    "Rooms notifications updated to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (notificationType == 1) {
            settingsRepository.updateRoomsNotifications(userId, notificationType);
            return new Response(
                    "Rooms notifications updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (notificationType == 2) {
            settingsRepository.updateRoomsNotifications(userId, notificationType);
            return new Response(
                    "Rooms notifications updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "notificationType has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response updateAllowTagsFrom(Long userId, Integer tagType) {
        if (userId == null || tagType == null) {
            return new Response(
                    "Both userId and tagType are required!",
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
        if (tagType == 0) {
            settingsRepository.updateAllowTagsFrom(userId, tagType);
            return new Response(
                    "Tagging you updated to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (tagType == 1) {
            settingsRepository.updateAllowTagsFrom(userId, tagType);
            return new Response(
                    "Tagging you updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (tagType == 2) {
            settingsRepository.updateAllowTagsFrom(userId, tagType);
            return new Response(
                    "Tagging you updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "tagType has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response updateAllowMentionFrom(Long userId, Integer mentionType) {
        if (userId == null || mentionType == null) {
            return new Response(
                    "Both userId and mentionType are required!",
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
        if (mentionType == 0) {
            settingsRepository.updateAllowMentionFrom(userId, mentionType);
            return new Response(
                    "Mentioning you updated to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (mentionType == 1) {
            settingsRepository.updateAllowMentionFrom(userId, mentionType);
            return new Response(
                    "Mentioning you updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (mentionType == 2) {
            settingsRepository.updateAllowMentionFrom(userId, mentionType);
            return new Response(
                    "Mentioning you updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "mentionType has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response updateAllowMessageRepliesForStory(Long userId, Integer replyType) {
        if (userId == null || replyType == null) {
            return new Response(
                    "Both userId and replyType are required!",
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
        if (replyType == 0) {
            settingsRepository.updateAllowMessageRepliesForStory(userId, replyType);
            return new Response(
                    "Story replies updated to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (replyType == 1) {
            settingsRepository.updateAllowMessageRepliesForStory(userId, replyType);
            return new Response(
                    "Story replies updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (replyType == 2) {
            settingsRepository.updateAllowMessageRepliesForStory(userId, replyType);
            return new Response(
                    "Story replies updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "replyType has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response updateWhoCanMessageMe(Long userId, Integer whoCanMessageMe) {
        if (userId == null || whoCanMessageMe == null) {
            return new Response(
                    "Both userId and whoCanMessageMe are required!",
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
        if (whoCanMessageMe == 0) {
            settingsRepository.updateWhoCanMessageMe(userId, whoCanMessageMe);
            return new Response(
                    "Messaging you updated to all users!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (whoCanMessageMe == 1) {
            settingsRepository.updateWhoCanMessageMe(userId, whoCanMessageMe);
            return new Response(
                    "Messaging you updated to only people you follow!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else if (whoCanMessageMe == 2) {
            settingsRepository.updateWhoCanMessageMe(userId, whoCanMessageMe);
            return new Response(
                    "Messaging you updated to nobody!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        else {
            return new Response(
                    "whoCanMessageMe has to be 0, 1 or 2!!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response notifyCommentLikesAndPins(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getCommentLikesAndPins();
            settingsRepository.updateCommentLikesAndPins(userId, !currentSettings);
            return new Response(
                    "comment likes and pin settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response notifyAcceptedFollowRequests(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getAcceptedFollowRequests();
            settingsRepository.updateNotifyAcceptedFollowRequests(userId, !currentSettings);
            return new Response(
                    "accepted follow requests settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response notifyAccountSuggestions(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getAccountSuggestions();
            settingsRepository.notifyAccountSuggestions(userId, !currentSettings);
            return new Response(
                    "Account suggestions settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response notifyMessageRequests(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getMessageRequests();
            settingsRepository.notifyMessageRequests(userId, !currentSettings);
            return new Response(
                    "Message requests settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response notifyMessages(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getMessages();
            settingsRepository.notifyMessages(userId, !currentSettings);
            return new Response(
                    "Messages settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response notifyGroupRequests(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getGroupRequests();
            settingsRepository.notifyGroupRequests(userId, !currentSettings);
            return new Response(
                    "Group requests settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response notifyLiveVideos(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getLiveVideos();
            settingsRepository.notifyLiveVideos(userId, !currentSettings);
            return new Response(
                    "Live videos settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response notifyVideoUploads(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getVideoUploads();
            settingsRepository.notifyVideoUploads(userId, !currentSettings);
            return new Response(
                    "Video uploads settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response notifyVideoViewCounts(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getVideoViewCounts();
            settingsRepository.notifyVideoViewCounts(userId, !currentSettings);
            return new Response(
                    "Video view count settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response togglePrivateAccount(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getPrivateAccount();
            settingsRepository.togglePrivateAccount(userId, !currentSettings);
            return new Response(
                    "Account publicity settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response toggleHideOffensiveComments(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getHideOffensiveComments();
            settingsRepository.toggleHideOffensiveComments(userId, !currentSettings);
            return new Response(
                    "Offensive comments settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response toggleHideLikeAndViewCounts(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getHideLikeAndViewCounts();
            settingsRepository.toggleHideLikeAndViewCounts(userId, !currentSettings);
            return new Response(
                    "Like and view count settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response toggleSaveStoryToGallery(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getSaveStoryToGallery();
            settingsRepository.toggleSaveStoryToGallery(userId, !currentSettings);
            return new Response(
                    "Gallery storage settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response toggleSaveStoryToArchive(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getSaveStoryToArchive();
            settingsRepository.toggleSaveStoryToArchive(userId, !currentSettings);
            return new Response(
                    "Story archive settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response toggleAllowSharingToStory(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getAllowSharingToStory();
            settingsRepository.toggleAllowSharingToStory(userId, !currentSettings);
            return new Response(
                    "Sharing to story settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response toggleAllowSharingToMessages(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getAllowSharingToMessages();
            settingsRepository.toggleAllowSharingToMessages(userId, !currentSettings);
            return new Response(
                    "Sharing story to messages settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response toggleShowActivityStatus(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getShowActivityStatus();
            settingsRepository.toggleShowActivityStatus(userId, !currentSettings);
            return new Response(
                    "Activity status settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response toggleWhoCanAddYouToGroups(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
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
        Optional<Setting> userSettings = settingsRepository.getSettingsForUser(userId);
        if (userSettings.isPresent()) {
            Boolean currentSettings = userSettings.get().getWhoCanAddYouToGroups();
            settingsRepository.toggleWhoCanAddYouToGroups(userId, !currentSettings);
            return new Response(
                    "who can add you to groups settings changed!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Settings not created for user!",
                new ResponseStatus(
                        HttpStatus.NOT_IMPLEMENTED.value(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                )
        );
    }

    public Response fetchUserSettings(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given id!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<Setting> userSettings = settingsRepository.findById(userId);
        if (userSettings.isPresent()) {
            return new Response(
                    "Settings fetched successfully!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),
                    userSettings
            );
        }
        else {
            return new Response(
                    "No settings found for this user!!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
    }

    public Response fetchBlockedUsers(Long userId) {
        if (userId == null) {
            return new Response(
                    "UserId is required!",
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

        Optional<Setting> userSettings = settingsRepository.findById(userId);
        if (userSettings.isPresent()) {
            String users=userSettings.get().getBlockedAccounts();
            if (users==null|| users.equals("")){
                return new Response(
                        "No Blocked users",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        ),
                         (List<SearchResult>) new ArrayList<SearchResult>()
                );
            }
            users=users.substring(1);
            List<String> temp=  Arrays.asList(users.split(",", -1));

            List<Long> usersList=temp.stream().map(Long::parseLong).collect(Collectors.toList());
            List<SearchResult> popularUsers = new ArrayList<>();
                for (Long i:usersList) {
                    User user1 = userRepository.getById(i);
                        SearchResult result = new SearchResult();
                        result.setUserId(user1.getId().toString());
                        result.setUserName(user1.getUserName());
                        result.setFullName(user1.getFullName());
                        result.setProfileUrl(user1.getProfileUrl());
                        result.setFollowingOrNot(true);

                        popularUsers.add(result);

                }

            return new Response(
                    "Settings fetched successfully!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),
                    popularUsers
            );

        }
        return new Response(
                "Unable to fetch",
                new ResponseStatus(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                )
        );
    }


}
