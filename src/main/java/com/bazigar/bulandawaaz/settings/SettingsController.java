package com.bazigar.bulandawaaz.settings;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buland-awaaz/api/User")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @PostMapping("/blockUser")
    public Response blockUser(Long userId, Long otherUserId) {
        return settingsService.blockUser(userId, otherUserId);
    }

    @PostMapping("/unblockUser")
    public Response unblockUser(Long userId, Long otherUserId) {
        return settingsService.unblockUser(userId, otherUserId);
    }

    @PostMapping("/fetchBlockedUsers")
    public Response fetchBlockedUsers(Long userId) {
        return settingsService.fetchBlockedUsers(userId);
    }

    @PostMapping("/hideStoryFrom")
    public Response hideStoryFrom(Long userId, Long otherUserId) {
        return settingsService.hideStoryFromUser(userId, otherUserId);
    }

    @PostMapping("/removeHideStoryFromUser")
    public Response removeHideStoryFrom(Long userId, Long otherUserId) {
        return settingsService.unhideStoryFromUser(userId, otherUserId);
    }

    @PostMapping("/blockCommentFrom")
    public Response blockCommentsFromUser(Long userId, Long otherUserId) {
        return settingsService.blockCommentsFrom(userId, otherUserId);
    }

    @PostMapping("/unblockCommentFrom")
    public Response unblockCommentsFromUser(Long userId, Long otherUserId) {
        return settingsService.unblockCommentsFrom(userId, otherUserId);
    }

    @PostMapping("/muteUser")
    public Response muteUser(Long userId, Long otherUserId) {
        return settingsService.muteUser(userId, otherUserId);
    }

    @PostMapping("/unmuteUser")
    public Response unmuteUser(Long userId, Long otherUserId) {
        return settingsService.unmuteUser(userId, otherUserId);
    }

    @PostMapping("/restrictUser")
    public Response restrictUser(Long userId, Long otherUserId) {
        return settingsService.restrictAccount(userId, otherUserId);
    }

    @PostMapping("/unrestrictUser")
    public Response unrestrictUser(Long userId, Long otherUserId) {
        return settingsService.unrestrictAccount(userId, otherUserId);
    }

    @PostMapping("/closeFriendAdd")
    public Response closeFriendAdd(Long userId, Long otherUserId) {
        return settingsService.closeFriendAdd(userId, otherUserId);
    }

    @PostMapping("/closeFriendRemove")
    public Response closeFriendRemove(Long userId, Long otherUserId) {
        return settingsService.closeFriendRemove(userId, otherUserId);
    }

    @PostMapping("/notifyLikesOnPosts")
    public Response notifyLikesOnPosts(Long userId, Integer likeCategory) {
        return settingsService.updateWhoCanLikeYourPostsAndComments(userId, likeCategory);
    }

    @PostMapping("/notifyLikesOnTaggedPosts")
    public Response notifyLikesOnTaggedPosts(Long userId, Integer likeCategory) {
        return settingsService.updateWhoCanLikeOnPostsYouAreTaggedIn(userId, likeCategory);
    }

    @PostMapping("/notifyWhenYouAreTagged")
    public Response notifyWhenYouAreTagged(Long userId, Integer whoCanTagMe) {
        return settingsService.taggedNotifications(userId, whoCanTagMe);
    }

    @PostMapping("/notifyWhenSomebodyCommentsOnYourPost")
    public Response notifyWhenSomebodyCommentsOnYourPost(Long userId, Integer notificationType) {
        return settingsService.updateCommentNotifications(userId, notificationType);
    }

    @PostMapping("/notifyFirstPostAndStories")
    public Response notifyFirstPostAndStories(Long userId, Integer notificationType) {
        return settingsService.updateFirstPostAndStoriesNotifications(userId, notificationType);
    }

    @PostMapping("/notifyMentionsInBio")
    public Response notifyMentionsInBio(Long userId, Integer notificationType) {
        return settingsService.updateMentionInBioNotifications(userId, notificationType);
    }

    @PostMapping("/notifyVideoChat")
    public Response notifyVideoChat(Long userId, Integer notificationType) {
        return settingsService.updateVideoChatNotifications(userId, notificationType);
    }

    @PostMapping("/notifyRooms")
    public Response notifyRooms(Long userId, Integer notificationType) {
        return settingsService.updateRoomsNotifications(userId, notificationType);
    }

    @PostMapping("/allowTagsFrom")
    public Response allowTagsFrom(Long userId, Integer tagType) {
        return settingsService.updateAllowTagsFrom(userId, tagType);
    }

    @PostMapping("/allowMentionsFrom")
    public Response allMentionsFrom(Long userId, Integer mentionType) {
        return settingsService.updateAllowMentionFrom(userId, mentionType);
    }

    @PostMapping("/allowMessageRepliesForStory")
    public Response allowMessageRepliesForStory(Long userId, Integer replyType) {
        return settingsService.updateAllowMessageRepliesForStory(userId, replyType);
    }

    @PostMapping("/whoCanMessageMe")
    public Response whoCanMessageMe(Long userId, Integer whoCanMessageMe) {
        return settingsService.updateWhoCanMessageMe(userId, whoCanMessageMe);
    }

    @PostMapping("/addToLikedPosts")
    public Response addToLikedPosts(Long userId, String type, Long postId) {
        return settingsService.addToLikedPosts(userId, type, postId);
    }

    @PostMapping("/removeFromLikedPosts")
    public Response removeFromLikedPosts(Long userId, String type, Long postId) {
        return settingsService.removeFromLikedPosts(userId, type, postId);
    }

    @PostMapping("/notifyCommentLikesAndPins")
    public Response notifyCommentLikesAndPins(Long userId) {
        return settingsService.notifyCommentLikesAndPins(userId);
    }

    @PostMapping("/notifyAcceptedFollowRequests")
    public Response notifyAcceptedFollowRequests(Long userId) {
        return settingsService.notifyAcceptedFollowRequests(userId);
    }

    @PostMapping("/notifyAccountSuggestions")
    public Response notifyAccountSuggestions(Long userId) {
        return settingsService.notifyAccountSuggestions(userId);
    }

    @PostMapping("/notifyMessageRequests")
    public Response notifyMessageRequests(Long userId) {
        return settingsService.notifyMessageRequests(userId);
    }

    @PostMapping("/notifyMessages")
    public Response notifyMessages(Long userId) {
        return settingsService.notifyMessages(userId);
    }

    @PostMapping("/notifyGroupRequests")
    public Response notifyGroupRequests(Long userId) {
        return settingsService.notifyGroupRequests(userId);
    }

    @PostMapping("/notifyLiveVideos")
    public Response notifyLiveVideos(Long userId) {
        return settingsService.notifyLiveVideos(userId);
    }

    @PostMapping("/notifyVideoUploads")
    public Response notifyVideoUploads(Long userId) {
        return settingsService.notifyVideoUploads(userId);
    }

    @PostMapping("/notifyVideoViewCounts")
    public Response notifyVideoViewCounts(Long userId) {
        return settingsService.notifyVideoViewCounts(userId);
    }

    @PostMapping("/togglePrivateAccount")
    public Response togglePrivateAccount(Long userId) {
        return settingsService.togglePrivateAccount(userId);
    }

    @PostMapping("/toggleHideOffensiveComments")
    public Response toggleHideOffensiveComments(Long userId) {
        return settingsService.toggleHideOffensiveComments(userId);
    }

    @PostMapping("/toggleHideLikeAndViewCounts")
    public Response toggleHideLikeAndViewCounts(Long userId) {
        return settingsService.toggleHideLikeAndViewCounts(userId);
    }

    @PostMapping("/toggleSaveStoryToGallery")
    public Response toggleSaveStoryToGallery(Long userId) {
        return settingsService.toggleSaveStoryToGallery(userId);
    }

    @PostMapping("/toggleSaveStoryToArchive")
    public Response toggleSaveStoryToArchive(Long userId) {
        return settingsService.toggleSaveStoryToArchive(userId);
    }

    @PostMapping("/toggleAllowSharingToStory")
    public Response toggleAllowSharingToStory(Long userId) {
        return settingsService.toggleAllowSharingToStory(userId);
    }

    @PostMapping("/toggleAllowSharingToMessages")
    public Response toggleAllowSharingToMessages(Long userId) {
        return settingsService.toggleAllowSharingToMessages(userId);
    }

    @PostMapping("/toggleShowActivityStatus")
    public Response toggleShowActivityStatus(Long userId) {
        return settingsService.toggleShowActivityStatus(userId);
    }


    @PostMapping("/toggleWhoCanAddYouToGroups")
    public Response toggleWhoCanAddYouToGroups(Long userId) {
        return settingsService.toggleWhoCanAddYouToGroups(userId);
    }

    @PostMapping("/fetchUserSettings")
    public Response fetchUserSettings(Long userId) {
        return settingsService.fetchUserSettings(userId);
    }
}
