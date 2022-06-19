package com.bazigar.bulandawaaz.settings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Setting {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long userId;
    // Notifications
    private Boolean pauseAll = false;
    // Notifications - Post stories and comments
    private Integer likes = 0;  // 0 - all, 1-people I follow, 2 - off
    private Integer likesCommentsOnYourPhotos = 0; // 0 - all, 1-people I follow, 2 - off
    private Integer tagMeInAPhoto = 0; // 0 - all, 1-people I follow, 2 - off
    private Integer comments = 0; // 0 - all, 1-people I follow, 2 - off
    private Boolean commentLikesAndPins = true;
    private Integer firstPostAndStories = 0;    // 0 - all, 1-people I follow, 2 - off
    // Notifications - following and followers
    private Boolean acceptedFollowRequests = true;
    private Boolean accountSuggestions = true;
    private Integer mentionInBio = 0;   // 0 - all, 1-people I follow, 2 - off
    // Notifications - message and call
    private Boolean messageRequests = true;
    private Boolean messages = true;
    private Boolean groupRequests = true;
    private Integer videoChats = 0; // 0 - all, 1-people I follow, 2 - off
    private Integer rooms = 0;  // 0 - all, 1-people I follow, 2 - off
    // Notifications - Live and video
    private Boolean liveVideos = true;
    private Boolean videoUploads = true;
    private Boolean videoViewCounts = true;
    // Privacy
    private Boolean privateAccount = false;
    // Privacy  - Interactions - Hidden words - offensive words and phrases
    private Boolean hideOffensiveComments = false;
    // Privacy - Comments
    private String blockCommentsFrom = "";
    // Privacy - posts
    private Boolean hideLikeAndViewCounts = false;
    private Integer allowTagsFrom = 0;  // 0 - all, 1-people I follow, 2 - off
    // Privacy - mentions
    private Integer allowMentionFrom = 0;   // 0 - all, 1-people I follow, 2 - off
    // Privacy - story
    private String hideStoryFrom = "";
    private String closeFriends = "";
    private Integer allowMessageReplies = 0; // 0 - all, 1-people I follow, 2 - off
    private Boolean saveStoryToGallery = false;
    private Boolean saveStoryToArchive = true;
    private Boolean allowSharingToStory = true;
    private Boolean allowSharingToMessages = true;
    // Privacy - activity status
    private Boolean showActivityStatus = true;
    // Privacy - message control
    private Integer whoCanMessageMe = 0;    // 0 - all, 1-people I follow, 2 - off
    private Boolean whoCanAddYouToGroups = true;    // true - everyone, false - people I follow
    // Privacy - connections
    private String restrictedAccounts = "";
    private String blockedAccounts = "";
    private String mutedAccounts = "";
    // Account
    private String postsYouHaveLiked = "";
    private String recentlyDeleted = "";

    public Setting() {
    }

    public Setting(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getPauseAll() {
        return pauseAll;
    }

    public void setPauseAll(Boolean pauseAll) {
        this.pauseAll = pauseAll;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getLikesCommentsOnYourPhotos() {
        return likesCommentsOnYourPhotos;
    }

    public void setLikesCommentsOnYourPhotos(Integer likesCommentsOnYourPhotos) {
        this.likesCommentsOnYourPhotos = likesCommentsOnYourPhotos;
    }

    public Integer getTaggingOnMyPhoto() {
        return tagMeInAPhoto;
    }

    public void setTaggingOnMyPhoto(Integer taggingOnMyPhoto) {
        this.tagMeInAPhoto = taggingOnMyPhoto;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Boolean getCommentLikesAndPins() {
        return commentLikesAndPins;
    }

    public void setCommentLikesAndPins(Boolean commentLikesAndPins) {
        this.commentLikesAndPins = commentLikesAndPins;
    }

    public Integer getFirstPostAndStories() {
        return firstPostAndStories;
    }

    public void setFirstPostAndStories(Integer firstPostAndStories) {
        this.firstPostAndStories = firstPostAndStories;
    }

    public Boolean getAcceptedFollowRequests() {
        return acceptedFollowRequests;
    }

    public void setAcceptedFollowRequests(Boolean acceptedFollowRequests) {
        this.acceptedFollowRequests = acceptedFollowRequests;
    }

    public Boolean getAccountSuggestions() {
        return accountSuggestions;
    }

    public void setAccountSuggestions(Boolean accountSuggestions) {
        this.accountSuggestions = accountSuggestions;
    }

    public Integer getMentionInBio() {
        return mentionInBio;
    }

    public void setMentionInBio(Integer mentionInBio) {
        this.mentionInBio = mentionInBio;
    }

    public Boolean getMessageRequests() {
        return messageRequests;
    }

    public void setMessageRequests(Boolean messageRequests) {
        this.messageRequests = messageRequests;
    }

    public Boolean getMessages() {
        return messages;
    }

    public void setMessages(Boolean messages) {
        this.messages = messages;
    }

    public Boolean getGroupRequests() {
        return groupRequests;
    }

    public void setGroupRequests(Boolean groupRequests) {
        this.groupRequests = groupRequests;
    }

    public Integer getVideoChats() {
        return videoChats;
    }

    public void setVideoChats(Integer videoChats) {
        this.videoChats = videoChats;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Boolean getLiveVideos() {
        return liveVideos;
    }

    public void setLiveVideos(Boolean liveVideos) {
        this.liveVideos = liveVideos;
    }

    public Boolean getVideoUploads() {
        return videoUploads;
    }

    public void setVideoUploads(Boolean videoUploads) {
        this.videoUploads = videoUploads;
    }

    public Boolean getVideoViewCounts() {
        return videoViewCounts;
    }

    public void setVideoViewCounts(Boolean videoViewCounts) {
        this.videoViewCounts = videoViewCounts;
    }

    public Boolean getPrivateAccount() {
        return privateAccount;
    }

    public void setPrivateAccount(Boolean privateAccount) {
        this.privateAccount = privateAccount;
    }

    public Boolean getHideOffensiveComments() {
        return hideOffensiveComments;
    }

    public void setHideOffensiveComments(Boolean hideOffensiveComments) {
        this.hideOffensiveComments = hideOffensiveComments;
    }

    public String getBlockCommentsFrom() {
        return blockCommentsFrom;
    }

    public void setBlockCommentsFrom(String blockCommentsFrom) {
        this.blockCommentsFrom = blockCommentsFrom;
    }

    public Boolean getHideLikeAndViewCounts() {
        return hideLikeAndViewCounts;
    }

    public void setHideLikeAndViewCounts(Boolean hideLikeAndViewCounts) {
        this.hideLikeAndViewCounts = hideLikeAndViewCounts;
    }

    public Integer getAllowTagsFrom() {
        return allowTagsFrom;
    }

    public void setAllowTagsFrom(Integer allowTagsFrom) {
        this.allowTagsFrom = allowTagsFrom;
    }

    public Integer getAllowMentionFrom() {
        return allowMentionFrom;
    }

    public void setAllowMentionFrom(Integer allowMentionFrom) {
        this.allowMentionFrom = allowMentionFrom;
    }

    public String getHideStoryFrom() {
        return hideStoryFrom;
    }

    public void setHideStoryFrom(String hideStoryFrom) {
        this.hideStoryFrom = hideStoryFrom;
    }

    public String getCloseFriends() {
        return closeFriends;
    }

    public void setCloseFriends(String closeFriends) {
        this.closeFriends = closeFriends;
    }

    public Integer getAllowMessageReplies() {
        return allowMessageReplies;
    }

    public void setAllowMessageReplies(Integer allowMessageReplies) {
        this.allowMessageReplies = allowMessageReplies;
    }

    public Boolean getSaveStoryToGallery() {
        return saveStoryToGallery;
    }

    public void setSaveStoryToGallery(Boolean saveStoryToGallery) {
        this.saveStoryToGallery = saveStoryToGallery;
    }

    public Boolean getSaveStoryToArchive() {
        return saveStoryToArchive;
    }

    public void setSaveStoryToArchive(Boolean saveStoryToArchive) {
        this.saveStoryToArchive = saveStoryToArchive;
    }

    public Boolean getAllowSharingToStory() {
        return allowSharingToStory;
    }

    public void setAllowSharingToStory(Boolean allowSharingToStory) {
        this.allowSharingToStory = allowSharingToStory;
    }

    public Boolean getAllowSharingToMessages() {
        return allowSharingToMessages;
    }

    public void setAllowSharingToMessages(Boolean allowSharingToMessages) {
        this.allowSharingToMessages = allowSharingToMessages;
    }

    public Boolean getShowActivityStatus() {
        return showActivityStatus;
    }

    public void setShowActivityStatus(Boolean showActivityStatus) {
        this.showActivityStatus = showActivityStatus;
    }

    public Integer getWhoCanMessageMe() {
        return whoCanMessageMe;
    }

    public void setWhoCanMessageMe(Integer whoCanMessageMe) {
        this.whoCanMessageMe = whoCanMessageMe;
    }

    public Boolean getWhoCanAddYouToGroups() {
        return whoCanAddYouToGroups;
    }

    public void setWhoCanAddYouToGroups(Boolean whoCanAddYouToGroups) {
        this.whoCanAddYouToGroups = whoCanAddYouToGroups;
    }

    public String getRestrictedAccounts() {
        return restrictedAccounts;
    }

    public void setRestrictedAccounts(String restrictedAccounts) {
        this.restrictedAccounts = restrictedAccounts;
    }

    public String getBlockedAccounts() {
        return blockedAccounts;
    }

    public void setBlockedAccounts(String blockedAccounts) {
        this.blockedAccounts = blockedAccounts;
    }

    public String getMutedAccounts() {
        return mutedAccounts;
    }

    public void setMutedAccounts(String mutedAccounts) {
        this.mutedAccounts = mutedAccounts;
    }

    public String getPostsYouHaveLiked() {
        return postsYouHaveLiked;
    }

    public void setPostsYouHaveLiked(String postsYouHaveLiked) {
        this.postsYouHaveLiked = postsYouHaveLiked;
    }

    public String getRecentlyDeleted() {
        return recentlyDeleted;
    }

    public void setRecentlyDeleted(String recentlyDeleted) {
        this.recentlyDeleted = recentlyDeleted;
    }

    @Override
    public String toString() {
        return "Settings for "+userId;
    }
}
