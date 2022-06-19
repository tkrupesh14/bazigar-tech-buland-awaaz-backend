package com.bazigar.bulandawaaz.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface SettingsRepository extends JpaRepository<Setting, Long> {

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.blockedAccounts = ?2 where s.userId = ?1")
    public void updateBlockedAccounts(Long userId, String blockedAccounts);

    @Query(value = "select s from Setting s where s.userId = ?1")
    Optional<Setting> getSettingsForUser(Long userId);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.hideStoryFrom = ?2 where s.userId = ?1")
    public void updateHideStoryFromUsers(Long userId, String hideStoryFromUsers);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.blockCommentsFrom = ?2 where s.userId = ?1")
    public void updateBlockCommentsFrom(Long userId, String blockedCommentsList);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.mutedAccounts = ?2 where s.userId = ?1")
    public void updateMutedAccounts(Long userId, String mutedAccountsList);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.restrictedAccounts = ?2 where s.userId = ?1")
    public void updateRestrictedAccounts(Long userId, String restrictedAccountsList);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.closeFriends = ?2 where s.userId = ?1")
    public void updateCloseFriends(Long userId, String closeFriendsList);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.likes = ?2 where s.userId = ?1")
    public void updateWhoCanLikeYourPostsAndComments(Long userId, Integer likeCategory);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.likesCommentsOnYourPhotos = ?2 where s.userId = ?1")
    public void updateWhoCanLikeOnPostsYouAreTaggedIn(Long userId, Integer likeCategory);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.tagMeInAPhoto = ?2 where s.userId = ?1")
    public void taggedNotifications(Long userId, Integer tagOrNot);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.comments = ?2 where s.userId = ?1")
    public void updateCommentNotifications(Long userId, Integer commentators);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.firstPostAndStories = ?2 where s.userId = ?1")
    public void updateFirstPostAndStoriesNotifications(Long userId, Integer notificationType);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.mentionInBio = ?2 where s.userId = ?1")
    public void updateMentionInBioNotifications(Long userId, Integer notificationType);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.videoChats = ?2 where s.userId = ?1")
    public void updateVideoChatNotifications(Long userId, Integer notificationType);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.rooms = ?2 where s.userId = ?1")
    public void updateRoomsNotifications(Long userId, Integer notificationType);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.allowTagsFrom = ?2 where s.userId = ?1")
    public void updateAllowTagsFrom(Long userId, Integer tagType);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.allowMentionFrom = ?2 where s.userId = ?1")
    public void updateAllowMentionFrom(Long userId, Integer mentionType);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.allowMessageReplies = ?2 where s.userId = ?1")
    public void updateAllowMessageRepliesForStory(Long userId, Integer replyType);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.whoCanMessageMe = ?2 where s.userId = ?1")
    public void updateWhoCanMessageMe(Long userId, Integer whoCanMessageMe);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.postsYouHaveLiked = ?2 where s.userId = ?1")
    public void updatePostsYouHaveLiked(Long userId, String postsYouHaveLiked);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.commentLikesAndPins = ?2 where s.userId = ?1")
    public void updateCommentLikesAndPins(Long userId, Boolean aValue);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.acceptedFollowRequests = ?2 where s.userId = ?1")
    public void updateNotifyAcceptedFollowRequests(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.accountSuggestions = ?2 where s.userId = ?1")
    public void notifyAccountSuggestions(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.messageRequests = ?2 where s.userId = ?1")
    public void notifyMessageRequests(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.messages = ?2 where s.userId = ?1")
    public void notifyMessages(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.groupRequests = ?2 where s.userId = ?1")
    public void notifyGroupRequests(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.liveVideos = ?2 where s.userId = ?1")
    public void notifyLiveVideos(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.videoUploads = ?2 where s.userId = ?1")
    public void notifyVideoUploads(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.videoViewCounts = ?2 where s.userId = ?1")
    public void notifyVideoViewCounts(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.privateAccount = ?2 where s.userId = ?1")
    public void togglePrivateAccount(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.hideOffensiveComments = ?2 where s.userId = ?1")
    public void toggleHideOffensiveComments(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.hideLikeAndViewCounts = ?2 where s.userId = ?1")
    public void toggleHideLikeAndViewCounts(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.saveStoryToGallery = ?2 where s.userId = ?1")
    public void toggleSaveStoryToGallery(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.saveStoryToArchive = ?2 where s.userId = ?1")
    public void toggleSaveStoryToArchive(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.allowSharingToStory = ?2 where s.userId = ?1")
    public void toggleAllowSharingToStory(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.allowSharingToMessages = ?2 where s.userId = ?1")
    public void toggleAllowSharingToMessages(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.showActivityStatus = ?2 where s.userId = ?1")
    public void toggleShowActivityStatus(Long userId, Boolean value);

    @Transactional
    @Modifying
    @Query(value = "update Setting s set s.whoCanAddYouToGroups = ?2 where s.userId = ?1")
    public void toggleWhoCanAddYouToGroups(Long userId, Boolean value);

}
