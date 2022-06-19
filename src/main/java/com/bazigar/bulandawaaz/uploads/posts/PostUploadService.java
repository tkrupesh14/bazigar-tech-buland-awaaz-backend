package com.bazigar.bulandawaaz.uploads.posts;

import com.bazigar.bulandawaaz.following.FollowingRepository;
import com.bazigar.bulandawaaz.hide_post.HidePost;
import com.bazigar.bulandawaaz.hide_post.HidePostRepository;
import com.bazigar.bulandawaaz.search_users.SearchResult;
import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.archiveAndSave.SavedRepository;
import com.bazigar.bulandawaaz.archiveAndSave.Savedposts;
import com.bazigar.bulandawaaz.fcmpushnotifications.model.PushNotificationRequest;
import com.bazigar.bulandawaaz.fcmpushnotifications.service.PushNotificationService;
import com.bazigar.bulandawaaz.following.Following;
import com.bazigar.bulandawaaz.hashtags.HashTagRepository;
import com.bazigar.bulandawaaz.hashtags.HashTagService;
import com.bazigar.bulandawaaz.likes.posts.PostLikesService;
import com.bazigar.bulandawaaz.mentions.Mention;
import com.bazigar.bulandawaaz.mentions.MentionHelper;
import com.bazigar.bulandawaaz.mentions.MentionRepository;
import com.bazigar.bulandawaaz.mentions.MentionService;
import com.bazigar.bulandawaaz.settings.SettingsRepository;
import com.bazigar.bulandawaaz.settings.SettingsService;
import com.bazigar.bulandawaaz.tags.TagRepository;
import com.bazigar.bulandawaaz.uploads.UploadMedia;
import com.bazigar.bulandawaaz.util.BunnyCDN;
import com.bazigar.bulandawaaz.util.PagedObject;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
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
public class PostUploadService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MentionRepository mentionRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private HashTagRepository hashTagRepository;

    @Autowired
    private PostLikesService postLikesService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SavedRepository savedRepository;

    @Autowired
    private MentionService mentionService;

    @Autowired
    private HashTagService hashTagService;
    @Autowired
    private SettingsService settingsService;

    @Autowired
    private HidePostRepository hidePostService;

    @Autowired
    private PushNotificationService pushNotificationService;

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
        String fileType = bunny.getFileType("USER", media);
        Object value = bunny.uploadFile(media.getOriginalFilename(),
                "post", uploadMedia.getUserId().toString(), "USER", media);
        if (value == "Failed to create video for uploading" ||
                value == "Failed to upload image") {
            return new Response(
                    value.toString(),
                    new ResponseStatus(
                            HttpStatus.NOT_IMPLEMENTED.value(),
                            HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
                    )
            );
        } else {
            if (value instanceof String) {
                Timestamp timer = Timestamp.from(Instant.now());
                Post post = new Post(
                        value.toString(),
                        user.get()
                );
                post.setCreatedAt(timer.getTime());
                post.setType("image");
                post.setCaption(uploadMedia.getCaption().substring(1,uploadMedia.getCaption().length()-1));
                post.setLocation(uploadMedia.getLocation());
                post.setCity( uploadMedia.getState().substring(1,uploadMedia.getState().length()-1));
                post.setState(uploadMedia.getCity().substring(1,uploadMedia.getCity().length()-1) );
                post.setCategory(uploadMedia.getCategory().substring(1,uploadMedia.getCategory().length()-1));
                postRepository.save(post);
                Pattern p = Pattern.compile("#\\w+");
                for (String i : uploadMedia.getCaption().split("\\s")) {
                    if (p.matcher(i).find()) {
                        hashTagService.addOrUpdateHashtag(i, post.getPostId(), "post");
                    }
                }
                Pattern mentionPattern = Pattern.compile("@\\w+");
                for (String j : uploadMedia.getCaption().split("\\s")) {
                    if (mentionPattern.matcher(j).find()) {
                        String mentionedUserName = j.substring(1);
                        Optional<User> mentionedUser = userRepository.findUserByUserName(mentionedUserName);
                        if (mentionedUser.isPresent()) {
                            MentionHelper helper = new MentionHelper(
                                    uploadMedia.getUserId(),
                                    mentionedUser.get().getId(),
                                    post.getPostId(),
                                    "post"
                            );
                            mentionService.addMention(helper);
                        }
                    }
                }
                List<User> userList=userRepository.findOtherUserByLocation(user.get().getCity(),user.get().getId());
                ArrayList<String> tokens= new ArrayList<>();
                for (User i:userList){
                    tokens.add(i.getFirebaseToken());
                }
                PushNotificationRequest request=new PushNotificationRequest(
                       "",
                        uploadMedia.getCaption(),
                        uploadMedia.getCategory()
                        ,"",
                        post.getPostUrl(),
                        post.getPostId(),
                        user.get().getProfileUrl(),
                        post.getType());
                request.setUserId(uploadMedia.getUserId());
                if (!tokens.isEmpty()&& !(tokens.get(0) ==""))
                    try {
                        pushNotificationService.sendNotificationToMultipleUsers(request, tokens);
                    }catch (Exception e){
                        e.printStackTrace();
                }




                return new Response(
                        "Image uploaded successfully!",
                        new ResponseStatus(
                                HttpStatus.CREATED.value(),
                                HttpStatus.CREATED.getReasonPhrase()
                        ),
                        value
                );
            } else {
                Timestamp timer = Timestamp.from(Instant.now());
                List<String> videoUrls = (List<String>) value;
                Post post = new Post(
                        videoUrls.get(0),
                        videoUrls.get(3),
                        "video",
                        user.get(),
                        uploadMedia.getCaption(),
                        uploadMedia.getLocation(),
                        videoUrls.get(1),
                        videoUrls.get(2),
                        videoUrls.get(4),
                        "uploading",
                        0,
                        0L,
                        0L,
                        uploadMedia.getCity(),
                        uploadMedia.getState(),
                        uploadMedia.getCategory()

                );
                post.setCreatedAt(timer.getTime());
                postRepository.save(post);
                Pattern p = Pattern.compile("#\\w+");
                for (String i : uploadMedia.getCaption().split("\\s")) {
                    if (p.matcher(i).find()) {
                        hashTagService.addOrUpdateHashtag(i, post.getPostId(), "post");
                    }
                }
                return new Response(
                        "Video uploaded successfully!",
                        new ResponseStatus(
                                HttpStatus.CREATED.value(),
                                HttpStatus.CREATED.getReasonPhrase()
                        ),
                        value
                );
            }
        }
//        if (Objects.equals(fileType, "image")) {
//            String imageUrl = bunny.uploadImageToStorage(media, "post", uploadMedia.getUserId().toString());
//            if (Objects.equals(imageUrl, "Failed to upload image!")) {
//                return new Response(
//                        imageUrl.toString(),
//                        new ResponseStatus(
//                                HttpStatus.NOT_IMPLEMENTED.value(),
//                                HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
//                        )
//                );
//            }
//            Timestamp timer = Timestamp.from(Instant.now());
//            Post post = new Post(
//                    imageUrl,
//                    user.get()
//            );
//            post.setCreatedAt(timer.getTime());
//            post.setType("image");
//            post.setCaption(uploadMedia.getCaption());
//            post.setLocation(uploadMedia.getLocation());
//            postRepository.save(post);
//            Pattern p = Pattern.compile("#\\w+");
//            for (String i : uploadMedia.getCaption().split("\\s")) {
//                if (p.matcher(i).find()) {
//                    hashTagService.addOrUpdateHashtag(i, post.getPostId(), "post");
//                }
//            }
//            return new Response(
//                    "Image uploaded successfully!",
//                    new ResponseStatus(
//                            HttpStatus.CREATED.value(),
//                            HttpStatus.CREATED.getReasonPhrase()
//                    ),
//                    imageUrl
//            );
//        }
//        else {
//            String videoUrl = bunny.uploadVideoToStorage(media, "post", uploadMedia.getUserId().toString());
//            if (videoUrl.equals("Failed to upload video!")) {
//                return new Response(
//                        videoUrl,
//                        new ResponseStatus(
//                                HttpStatus.NOT_IMPLEMENTED.value(),
//                                HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
//                        )
//                );
//            }
//            Timestamp timer = Timestamp.from(Instant.now());
//            Post post = new Post(
//                    videoUrl,
//                    "",
//                    "video",
//                    user.get(),
//                    uploadMedia.getCaption(),
//                    uploadMedia.getLocation(),
//                    videoUrl,
//                    "",
//                    "",
//                    "uploading",
//                    0,
//                    0L
//            );
//            post.setCreatedAt(timer.getTime());
//            postRepository.save(post);
//            Pattern p = Pattern.compile("#\\w+");
//            for (String i : uploadMedia.getCaption().split("\\s")) {
//                if (p.matcher(i).find()) {
//                    hashTagService.addOrUpdateHashtag(i, post.getPostId(), "post");
//                }
//            }
//            return new Response(
//                    "Video uploaded successfully!",
//                    new ResponseStatus(
//                            HttpStatus.CREATED.value(),
//                            HttpStatus.CREATED.getReasonPhrase()
//                    ),
//                    videoUrl
//            );
//        }
    }

//    public Response fetchPosts(Long userId, Long otherUserId) {
//        if (userId == null || otherUserId == null) {
//            return new Response(
//                    "userId and otherUserId are required!",
//                    new ResponseStatus(
//                            HttpStatus.BAD_REQUEST.value(),
//                            HttpStatus.BAD_REQUEST.getReasonPhrase()
//                    )
//            );
//        }
//        Optional<User> user = userRepository.findById(userId);
//        if (user.isEmpty()) {
//            return new Response(
//                    "No user found with the given userId",
//                    new ResponseStatus(
//                            HttpStatus.NOT_FOUND.value(),
//                            HttpStatus.NOT_FOUND.getReasonPhrase()
//                    )
//            );
//        }
//        Optional<User> otherUser = userRepository.findById(otherUserId);
//        if (otherUser.isEmpty()) {
//            return new Response(
//                    "No user found with the given otherUserId",
//                    new ResponseStatus(
//                            HttpStatus.NOT_FOUND.value(),
//                            HttpStatus.NOT_FOUND.getReasonPhrase()
//                    )
//            );
//        }
//        Optional<Setting> setting = settingsRepository.getSettingsForUser(otherUserId);
//        if (setting.isPresent()) {
//            String blockedUsers = setting.get().getBlockedAccounts();
//            String[] blockedUserList = blockedUsers.split(",");
//            ArrayList<String> mutableBlockedUserList = new ArrayList<>(Arrays.asList(blockedUserList));
//            if (mutableBlockedUserList.contains(userId.toString())) {
//                return new Response(
//                        "You are blocked by " + otherUser.get().getUserName() + "!!",
//                        new ResponseStatus(
//                                HttpStatus.FORBIDDEN.value(),
//                                HttpStatus.FORBIDDEN.getReasonPhrase()
//                        )
//                );
//            }
//            List<Post> postsList = postRepository.findByUserId(otherUserId);
//            return new Response(
//                    "Posts fetched!",
//                    new ResponseStatus(
//                            HttpStatus.OK.value(),
//                            HttpStatus.OK.getReasonPhrase()
//                    ),
//                    postsList
//            );
//        }
//        return new Response(
//                "Settings error",
//                new ResponseStatus(
//                        HttpStatus.NOT_IMPLEMENTED.value(),
//                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase()
//                )
//        );
//    }


    public Object fetchDefaultPosts(Long userId,Long postId, Integer pageNo) {
        int totalpages = 0;
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
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<Post> firstPost=null;
        if (postId!=null)
         firstPost=postRepository.findPostById(postId);


        Pageable page = PageRequest.of(pageNo, 10);
        Page<Post> postByThisLocation = postRepository.findByLocation(user.get().getCity(), user.get().getState(), page);
        totalpages = postByThisLocation.getTotalPages();

        List<Following> followingUsersList = followingRepository.findUsersFollowedByUserId(userId);
        ArrayList<Post> followingUsersPosts = new ArrayList<>();
//        for (Following i : followingUsersList) {
//            // Get posts of each followed user
//            Pageable paged = PageRequest.of(pageNo, 5);
//            Page<Post> postsByThisUserPage = postRepository.findByUserId(i.getFollowingUserId(), paged);
//            totalpages = postsByThisUserPage.getTotalPages();
//            List<Post> postsByThisUser = postsByThisUserPage.toList();
//
////            for (Post p : postByThisLocation.toList()) {
////              //  postsByThisUser.remove(p);
////            }
//            // Add these posts to followingUsersPosts
//            followingUsersPosts.addAll(postsByThisUser);
//        }


        ArrayList<PrettyPost> posts = new ArrayList<>();
        if (firstPost!=null&&firstPost.isPresent()&&pageNo==1){
            followingUsersPosts.add(firstPost.get());
        }

        for (Post i : postByThisLocation.toList()) {
            boolean postSavedOrNot = false;
            Optional<Savedposts> savedpost = savedRepository.findByPostId(i.getPostId());
            if (savedpost.isPresent()) {
                postSavedOrNot = true;
            }
            List<String> mentions = mentionService.prettyMentions(i.getPostId(), "post");
            ArrayList<String> hashTagsForPost = new ArrayList<>();
            Pattern p = Pattern.compile("#\\w+");
            for (String cap : i.getCaption().split("\\s")) {
                if (p.matcher(cap).find()) {
                    hashTagsForPost.add(cap);
                }
            }
            posts.add(new PrettyPost(
                    i.getPostId(),
                    i.getPostUrl(),
                    i.getHlsUrl(),
                    i.getThumbUrl(),
                    i.getUser().getUserName(),
                    i.getUser().getProfileUrl(),
                    i.getLikeCount(),
                    i.getCommentCount(),
                    postSavedOrNot,
                    String.join(", ", mentions),
                    String.join(", ", hashTagsForPost),
                    i.getCaption(),
                    i.getCreatedAt(),
                    postLikesService.isPostLikedByUser(i.getPostId(), userId),
                    i.getType(),
                    i.getLocation(),
                    i.getUser().getId(),
                    false,
                    i.getViewCount(),
                    i.getCity(),
                    i.getState(),
                    i.getUser().getFullName(),
                    i.getUser().getReporter(),
                    i.getUser().getVerified(),
                    i.getCategory(),
                    i.getUser().getPopular()

            ));
        }


        for (Post i : followingUsersPosts) {
            boolean postSavedOrNot = false;
            Optional<Savedposts> savedpost = savedRepository.findByPostId(i.getPostId());
            if (savedpost.isPresent()) {
                postSavedOrNot = true;
            }
            List<String> mentions = mentionService.prettyMentions(i.getPostId(), "post");
            ArrayList<String> hashTagsForPost = new ArrayList<>();
            Pattern p = Pattern.compile("#\\w+");
            for (String cap : i.getCaption().split("\\s")) {
                if (p.matcher(cap).find()) {
                    hashTagsForPost.add(cap);
                }
            }
            posts.add(new PrettyPost(
                    i.getPostId(),
                    i.getPostUrl(),
                    i.getHlsUrl(),
                    i.getThumbUrl(),
                    i.getUser().getUserName(),
                    i.getUser().getProfileUrl(),
                    i.getLikeCount(),
                    i.getCommentCount(),
                    postSavedOrNot,
                    String.join(", ", mentions),
                    String.join(", ", hashTagsForPost),
                    i.getCaption(),
                    i.getCreatedAt(),
                    postLikesService.isPostLikedByUser(i.getPostId(), userId),
                    i.getType(),
                    i.getLocation(),
                    i.getUser().getId(),
                    true,
                    i.getViewCount(),
                    i.getCity(),
                    i.getState(),
                    i.getUser().getFullName(),
                    i.getUser().getReporter(),
                    i.getUser().getVerified(),
                    i.getCategory(),
                    i.getUser().getPopular()
            ));
        }
        if (posts.isEmpty()) {
            return new Response(
                    "It seems you have not followed anybody!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }

        posts=blockFilteredPosts(posts,userId);


        posts.add(new PrettyPost("GoogleAd"));
        posts.add(new PrettyPost("GoogleAd"));
        Collections.shuffle(posts);
        if (firstPost!=null&&firstPost.isPresent()&&pageNo==0)
        for (PrettyPost i:posts){
            if (Objects.equals(i.getPostId(), firstPost.get().getPostId())){
                ArrayList<PrettyPost> p=posts;
                ArrayList<PrettyPost> temp=new ArrayList<>();
                temp.add(i);
                p.remove(i);
                temp.addAll(p);
                posts=temp;
                break;

            }
        }



        return new Response(
                "Posts successfully fetched!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        posts.toArray(),
                        pageNo,
                        totalpages
                )
        );
    }

    public Object fetchPostsByHashtag(Long userId,String hashtag, Integer pageNo) {
        int totalpages = 0;
        if (userId == null) {
            return new Response(
                    "userId is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (hashtag == null) {
            return new Response(
                    "hashtag is required!",
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
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }


        Pageable page = PageRequest.of(pageNo, 15);
        Page<Post> postByHashtag = postRepository.findByHashtag(hashtag, page);
        totalpages = postByHashtag.getTotalPages();


        ArrayList<PrettyPost> posts = new ArrayList<>();

        for (Post i : postByHashtag.toList()) {
            boolean postSavedOrNot = false;
            Optional<Savedposts> savedpost = savedRepository.findByPostId(i.getPostId());
            if (savedpost.isPresent()) {
                postSavedOrNot = true;
            }
            List<String> mentions = mentionService.prettyMentions(i.getPostId(), "post");
            ArrayList<String> hashTagsForPost = new ArrayList<>();
            Pattern p = Pattern.compile("#\\w+");
            for (String cap : i.getCaption().split("\\s")) {
                if (p.matcher(cap).find()) {
                    hashTagsForPost.add(cap);
                }
            }
            posts.add(new PrettyPost(
                    i.getPostId(),
                    i.getPostUrl(),
                    i.getHlsUrl(),
                    i.getThumbUrl(),
                    i.getUser().getUserName(),
                    i.getUser().getProfileUrl(),
                    i.getLikeCount(),
                    i.getCommentCount(),
                    postSavedOrNot,
                    String.join(", ", mentions),
                    String.join(", ", hashTagsForPost),
                    i.getCaption(),
                    i.getCreatedAt(),
                    postLikesService.isPostLikedByUser(i.getPostId(), userId),
                    i.getType(),
                    i.getLocation(),
                    i.getUser().getId(),
                    false,
                    i.getViewCount(),
                    i.getCity(),
                    i.getState(),
                    i.getUser().getFullName(),
                    i.getUser().getReporter(),
                    i.getUser().getVerified(),
                    i.getCategory(),
                    i.getUser().getPopular()
            ));
        }

              if (posts.isEmpty()) {
            return new Response(
                    "No post found!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
        posts=blockFilteredPosts(posts,userId);
        Collections.shuffle(posts);

        return new Response(
                "Posts successfully fetched!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        posts.toArray(),
                        pageNo,
                        totalpages
                )
        );
    }



    public Object fetchPostsByMention(Long userId, Integer pageNo) {
        int totalpages = 0;
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
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }

        Pageable page = PageRequest.of(pageNo, 15);
        Page<Mention> mentionsPage = mentionRepository.findMentionByMentionedUserIdAndType(userId,"post", page);
        totalpages = mentionsPage.getTotalPages();

        List<Post> postByMention =new ArrayList<>();

        for (Mention i : mentionsPage.toList()){
            Optional<Post> post = postRepository.findById(i.getPostId());
            post.ifPresent(postByMention::add);
        }



        ArrayList<PrettyPost> posts = new ArrayList<>();

        for (Post i : postByMention) {
            boolean postSavedOrNot = false;
            Optional<Savedposts> savedpost = savedRepository.findByPostId(i.getPostId());
            if (savedpost.isPresent()) {
                postSavedOrNot = true;
            }
            List<String> mentions = mentionService.prettyMentions(i.getPostId(), "post");
            ArrayList<String> hashTagsForPost = new ArrayList<>();
            Pattern p = Pattern.compile("#\\w+");
            for (String cap : i.getCaption().split("\\s")) {
                if (p.matcher(cap).find()) {
                    hashTagsForPost.add(cap);
                }
            }
            posts.add(new PrettyPost(
                    i.getPostId(),
                    i.getPostUrl(),
                    i.getHlsUrl(),
                    i.getThumbUrl(),
                    i.getUser().getUserName(),
                    i.getUser().getProfileUrl(),
                    i.getLikeCount(),
                    i.getCommentCount(),
                    postSavedOrNot,
                    String.join(", ", mentions),
                    String.join(", ", hashTagsForPost),
                    i.getCaption(),
                    i.getCreatedAt(),
                    postLikesService.isPostLikedByUser(i.getPostId(), userId),
                    i.getType(),
                    i.getLocation(),
                    i.getUser().getId(),
                    false,
                    i.getViewCount(),
                    i.getCity(),
                    i.getState(),
                    i.getUser().getFullName(),
                    i.getUser().getReporter(),
                    i.getUser().getVerified(),
                    i.getCategory(),
                    i.getUser().getPopular()

            ));
        }

        if (posts.isEmpty()) {
            return new Response(
                    "No post found!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
     //   Collections.shuffle(posts);
        posts=blockFilteredPosts(posts,userId);
        return new Response(
                "Posts successfully fetched!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        posts.toArray(),
                        pageNo,
                        totalpages
                )
        );
    }

    public Object fetchSavedPosts(Long userId, Integer pageNo) {
        int totalpages = 0;
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
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }

        Pageable page = PageRequest.of(pageNo, 15);
        Page<Savedposts> savedpostsPage = savedRepository.findSavedByIdAndType(userId,1, page);
        totalpages = savedpostsPage.getTotalPages();

        List<Post> postByMention =new ArrayList<>();

        for (Savedposts i : savedpostsPage.toList()){
            Optional<Post> post = postRepository.findById(i.getPost_id());
            post.ifPresent(postByMention::add);
        }



        ArrayList<PrettyPost> posts = new ArrayList<>();

        for (Post i : postByMention) {
            boolean postSavedOrNot = false;
            Optional<Savedposts> savedpost = savedRepository.findByPostId(i.getPostId());
            if (savedpost.isPresent()) {
                postSavedOrNot = true;
            }
            List<String> mentions = mentionService.prettyMentions(i.getPostId(), "post");
            ArrayList<String> hashTagsForPost = new ArrayList<>();
            Pattern p = Pattern.compile("#\\w+");
            for (String cap : i.getCaption().split("\\s")) {
                if (p.matcher(cap).find()) {
                    hashTagsForPost.add(cap);
                }
            }
            posts.add(new PrettyPost(
                    i.getPostId(),
                    i.getPostUrl(),
                    i.getHlsUrl(),
                    i.getThumbUrl(),
                    i.getUser().getUserName(),
                    i.getUser().getProfileUrl(),
                    i.getLikeCount(),
                    i.getCommentCount(),
                    postSavedOrNot,
                    String.join(", ", mentions),
                    String.join(", ", hashTagsForPost),
                    i.getCaption(),
                    i.getCreatedAt(),
                    postLikesService.isPostLikedByUser(i.getPostId(), userId),
                    i.getType(),
                    i.getLocation(),
                    i.getUser().getId(),
                    false,
                    i.getViewCount(),
                    i.getCity(),
                    i.getState(),
                    i.getUser().getFullName(),
                    i.getUser().getReporter(),
                    i.getUser().getVerified(),
                    i.getCategory(),
                    i.getUser().getPopular()

            ));
        }

        if (posts.isEmpty()) {
            return new Response(
                    "No post found!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
     //   Collections.shuffle(posts);
        posts=blockFilteredPosts(posts,userId);
        return new Response(
                "Posts successfully fetched!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        posts.toArray(),
                        pageNo,
                        totalpages
                )
        );
    }




    public Object fetchPopularPosts(Long userId,String city, String state, Integer pageNo) {
        int totalpages = 0;
        if (city == null && state == null) {
            return new Response(
                    "city and state both cannot be empty!",
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

        Optional<User> user=userRepository.findById(userId);
        if (user.isPresent()) {
            city=user.get().getCity();
            state=user.get().getState();
        }
        Pageable page = PageRequest.of(pageNo, 15);
        Page<Post> popularPosts = postRepository.getTrendingPosts(city, state, page);
        totalpages = popularPosts.getTotalPages();
        totalpages = popularPosts.getTotalPages();

        ArrayList<PrettyPost> posts = new ArrayList<>();

        for (Post i : popularPosts.toList()) {
            boolean postSavedOrNot = false;
            Optional<Savedposts> savedpost = savedRepository.findByPostId(i.getPostId());
            if (savedpost.isPresent()) {
                postSavedOrNot = true;
            }
            List<String> mentions = mentionService.prettyMentions(i.getPostId(), "post");
            ArrayList<String> hashTagsForPost = new ArrayList<>();
            Pattern p = Pattern.compile("#\\w+");
            for (String cap : i.getCaption().split("\\s")) {
                if (p.matcher(cap).find()) {
                    hashTagsForPost.add(cap);
                }
            }
            posts.add(new PrettyPost(
                    i.getPostId(),
                    i.getPostUrl(),
                    i.getHlsUrl(),
                    i.getThumbUrl(),
                    i.getUser().getUserName(),
                    i.getUser().getProfileUrl(),
                    i.getLikeCount(),
                    i.getCommentCount(),
                    postSavedOrNot,
                    String.join(", ", mentions),
                    String.join(", ", hashTagsForPost),
                    i.getCaption(),
                    i.getCreatedAt(),
                    postLikesService.isPostLikedByUser(i.getPostId(), userId),
                    i.getType(),
                    i.getLocation(),
                    i.getUser().getId(),
                    false,
                    i.getViewCount(),
                    i.getCity(),
                    i.getState(),
                    i.getUser().getFullName(),
                    i.getUser().getReporter(),
                    i.getUser().getVerified(),
                    i.getCategory(),
                    i.getUser().getPopular()

            ));
        }


        posts=blockFilteredPosts(posts,userId);

        if (posts.isEmpty()) {
            return new Response(
                    "It seems you have not followed anybody!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Posts successfully fetched!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        posts.toArray(),
                        pageNo,
                        totalpages
                )
        );
    }





    public Response fetchUserUploadedPosts(Long userId, Integer pageNo) {
        if (userId == null) {
            return new Response(
                    "userId is required and cannot be null!",
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
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Pageable paged = PageRequest.of(pageNo,15);
        Page<Post> postOrdered = postRepository.findByUserId(userId, paged);
        List<Post> postList = postOrdered.toList();

        ArrayList<PrettyPost> postsByUser = new ArrayList<>();
        for (Post i : postList) {
            boolean postSavedOrNot = false;
            Optional<Savedposts> savedpost = savedRepository.findByPostId(i.getPostId());
            if (savedpost.isPresent()) {
                postSavedOrNot = true;
            }
            List<String> mentions = mentionService.prettyMentions(i.getPostId(), "post");
            ArrayList<String> hashTagsForPost = new ArrayList<>();
            Pattern p = Pattern.compile("#\\w+");
            for (String cap : i.getCaption().split("\\s")) {
                if (p.matcher(cap).find()) {
                    hashTagsForPost.add(cap);
                }
            }
            postsByUser.add(new PrettyPost(
                    i.getPostId(),
                    i.getPostUrl(),
                    i.getHlsUrl(),
                    i.getThumbUrl(),
                    i.getUser().getUserName(),
                    i.getUser().getProfileUrl(),
                    i.getLikeCount(),
                    i.getCommentCount(),
                    postSavedOrNot,
                    String.join(", ", mentions),
                    String.join(", ", hashTagsForPost),
                    i.getCaption(),
                    i.getCreatedAt(),
                    postLikesService.isPostLikedByUser(i.getPostId(), userId),
                    i.getType(),
                    i.getLocation(),
                    i.getUser().getId(),
                    false,
                    i.getViewCount(),
                    i.getCity(),
                    i.getState(),
                    i.getUser().getFullName(),
                    i.getUser().getReporter(),
                    i.getUser().getVerified(),
                    i.getCategory(),
                    i.getUser().getPopular()
            ));
        }
        return new Response(
                "Found " + postList.size() + " posts!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        postsByUser.toArray(),
                        pageNo,
                        postOrdered.getTotalPages()
                )
        );
    }

    public Response deleteYourPost(Long userId, Long postId) {
        if (userId == null || postId == null) {
            return new Response(
                    "userId and postId is required and cannot be null!",
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
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new Response(
                    "No post found with the given postId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        if (userId.equals(post.get().getUser().getId())) {
            postRepository.delete(post.get());
            return new Response(
                    "Post successfully deleted!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "You cannot delete somebody else's post!",
                new ResponseStatus(
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase()
                )
        );
    }

    public Response editPostCaption(Long postId, Long userId, String caption) {
        if (postId == null || userId == null) {
            return new Response(
                    "postId and userId are required!",
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
        if (Objects.equals(post.get().getUser().getId(), userId)) {
            Pattern p = Pattern.compile("#\\w+");
            for (String i : caption.split("\\s")) {
                if (p.matcher(i).find()) {
                    hashTagService.addOrUpdateHashtag(i, postId, "post");
                }
            }
            Pattern mentionPattern = Pattern.compile("@\\w+");
            for (String j: caption.split("\\s")) {
                if (mentionPattern.matcher(j).find()) {
                    String mentionedUserName = j.substring(1);
                    Optional<User> mentionedUser = userRepository.findUserByUserName(mentionedUserName);
                    if (mentionedUser.isPresent()) {
                        MentionHelper helper = new MentionHelper(
                                userId,
                                mentionedUser.get().getId(),
                                postId,
                                "post"
                        );
                        mentionService.addMention(helper);
                    }
                }
            }
            postRepository.updatePostCaption(postId, caption);
            return new Response(
                    "Post caption successfully edited!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "You can only edit your own post!",
                new ResponseStatus(
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase()
                )
        );
    }

    public int getPostCount(Long userId) {
        Pageable paged = PageRequest.of(0, 15);
        Page<Post> postPage = postRepository.findByUserId(userId, paged);
        return postPage.toList().size();
    }

    public Response updateViewCount(Long postId){
        postRepository.updateViewCount(postId,1L);
        return new Response(
                "Post viewed successfully",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Object fetchPostsByCategory(Long userId, String category) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (category == null) {
            return new Response(
                    "category is required!",
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



        List<Post> postArrayList= postRepository.findByCategory(category);

        ArrayList<PrettyPost> posts = new ArrayList<>();

        for (Post i : postArrayList) {
            boolean postSavedOrNot = false;
            Optional<Savedposts> savedpost = savedRepository.findByPostId(i.getPostId());
            if (savedpost.isPresent()) {
                postSavedOrNot = true;
            }
            List<String> mentions = mentionService.prettyMentions(i.getPostId(), "post");
            ArrayList<String> hashTagsForPost = new ArrayList<>();
            Pattern p = Pattern.compile("#\\w+");
            for (String cap : i.getCaption().split("\\s")) {
                if (p.matcher(cap).find()) {
                    hashTagsForPost.add(cap);
                }
            }
            posts.add(new PrettyPost(
                    i.getPostId(),
                    i.getPostUrl(),
                    i.getHlsUrl(),
                    i.getThumbUrl(),
                    i.getUser().getUserName(),
                    i.getUser().getProfileUrl(),
                    i.getLikeCount(),
                    i.getCommentCount(),
                    postSavedOrNot,
                    String.join(", ", mentions),
                    String.join(", ", hashTagsForPost),
                    i.getCaption(),
                    i.getCreatedAt(),
                    postLikesService.isPostLikedByUser(i.getPostId(), userId),
                    i.getType(),
                    i.getLocation(),
                    i.getUser().getId(),
                    false,
                    i.getViewCount(),
                    i.getCity(),
                    i.getState(),
                    i.getUser().getFullName(),
                    i.getUser().getReporter(),
                    i.getUser().getVerified(),
                    i.getCategory(),
                    i.getUser().getPopular()
            ));
        }

        if (posts.isEmpty()) {
            return new Response(
                    "No post found!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
        posts=blockFilteredPosts(posts,userId);
        Collections.shuffle(posts);
        return new Response(
                "Posts successfully fetched!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        posts.toArray(),
                        0,
                        1
                )
        );
    }

    public Object fetchPostById(Long userId, Long postId) {

        if (userId == null) {
            return new Response(
                    "userId is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (postId == null) {
            return new Response(
                    "postId is required!",
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



        Optional<Post> post= postRepository.findPostById(postId);
        if (!post.isPresent()) {
            return new Response(
                    "No post found with the given postId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        ArrayList<Post>postArrayList=new ArrayList();
        postArrayList.add(post.get());



        ArrayList<PrettyPost> posts = new ArrayList<>();

        for (Post i : postArrayList) {
            boolean postSavedOrNot = false;
            Optional<Savedposts> savedpost = savedRepository.findByPostId(i.getPostId());
            if (savedpost.isPresent()) {
                postSavedOrNot = true;
            }
            List<String> mentions = mentionService.prettyMentions(i.getPostId(), "post");
            ArrayList<String> hashTagsForPost = new ArrayList<>();
            Pattern p = Pattern.compile("#\\w+");
            for (String cap : i.getCaption().split("\\s")) {
                if (p.matcher(cap).find()) {
                    hashTagsForPost.add(cap);
                }
            }
            posts.add(new PrettyPost(
                    i.getPostId(),
                    i.getPostUrl(),
                    i.getHlsUrl(),
                    i.getThumbUrl(),
                    i.getUser().getUserName(),
                    i.getUser().getProfileUrl(),
                    i.getLikeCount(),
                    i.getCommentCount(),
                    postSavedOrNot,
                    String.join(", ", mentions),
                    String.join(", ", hashTagsForPost),
                    i.getCaption(),
                    i.getCreatedAt(),
                    postLikesService.isPostLikedByUser(i.getPostId(), userId),
                    i.getType(),
                    i.getLocation(),
                    i.getUser().getId(),
                    false,
                    i.getViewCount(),
                    i.getCity(),
                    i.getState(),
                    i.getUser().getFullName(),
                    i.getUser().getReporter(),
                    i.getUser().getVerified(),
                    i.getCategory(),
                    i.getUser().getPopular()
            ));
        }

        if (posts.isEmpty()) {
            return new Response(
                    "No post found!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Posts successfully fetched!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        posts.toArray(),
                        0,
                        1
                )
        );
    }

    ArrayList<PrettyPost> blockFilteredPosts(ArrayList<PrettyPost> posts,Long userId){
        Response blockedUsersResponse=settingsService.fetchBlockedUsers(userId);
        List<SearchResult>blockedUsers=(List<SearchResult>) blockedUsersResponse.getData();
        ArrayList<PrettyPost> tempList=new ArrayList();
        tempList.addAll(posts);
        for (SearchResult i:blockedUsers){
            for (PrettyPost p:posts){
                if (Objects.equals(i.getUserId(), p.getUserId().toString())){
                    tempList.remove(p);
                }
            }
        }
        List<HidePost>HiddenPosts=(List<HidePost>) hidePostService.findHiddenPosts(userId);
        ArrayList<PrettyPost> tempList2=new ArrayList();
        tempList2.addAll(tempList);
        for (HidePost i:HiddenPosts){
            for (PrettyPost p:tempList){
                if (Objects.equals(i.getPostId(), p.getPostId())){
                    tempList2.remove(p);
                }
            }
        }

        return tempList2;
    }



}








