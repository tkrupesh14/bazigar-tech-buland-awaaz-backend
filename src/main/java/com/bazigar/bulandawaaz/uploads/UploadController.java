package com.bazigar.bulandawaaz.uploads;

import com.bazigar.bulandawaaz.uploads.posts.PostUploadService;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.WebhookObject;
import com.bazigar.bulandawaaz.uploads.blog.BlogService;
import com.bazigar.bulandawaaz.uploads.story.StoryUploadService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


@RestController
@RequestMapping("/buland-awaaz/api/User")
public class UploadController {

    @Autowired
    private PostUploadService postUploadService;



    @Autowired
    private StoryUploadService storyUploadService;

    @Autowired
    private BlogService blogService;

    @PostMapping(path = "/post_upload")
    private Response uploadPost(UploadMedia uploadMedia) throws IOException, JSONException {
        return postUploadService.uploadMedia(uploadMedia);
    }


    @PostMapping(path = "/video_status_webhook")
    private String videoUploadStatus(@RequestBody WebhookObject webhookObject) {
        return webhookObject.toString();
    }



    @PostMapping(path = "/story_upload")
    private Response uploadStory(UploadMedia uploadMedia) throws IOException, JSONException {
        return storyUploadService.uploadMedia(uploadMedia);
    }

    @PostMapping(path = "/fetch_posts")
    private Object fetchPosts(Long userId,Long postId, Integer pageNo) {
        return postUploadService.fetchDefaultPosts(userId,postId, pageNo);
    }
    @PostMapping(path = "/fetch_post_by_id")
    private Object fetchPostById(Long userId,Long postId) {
        return postUploadService.fetchPostById(userId,postId);
    }

    @PostMapping(path = "/fetch_posts_by_hashtag")
    private Object fetchPostsByHashtag(Long userId,String hashtag, Integer pageNo) {
        return postUploadService.fetchPostsByHashtag(userId,hashtag, pageNo);
    }
    @PostMapping(path = "/fetch_posts_by_category")
    private Object fetchPostsByCategory(Long userId,String category) {
        return postUploadService.fetchPostsByCategory(userId,category);
    }

    @PostMapping(path = "/fetch_posts_by_mention")
    private Object fetchPostsByMention(Long userId, Integer pageNo) {
        return postUploadService.fetchPostsByMention(userId, pageNo);
    }
    @PostMapping(path = "/fetch_saved_posts")
    private Object fetchSavedPosts(Long userId, Integer pageNo) {
        return postUploadService.fetchSavedPosts(userId, pageNo);
    }


    @PostMapping(path = "fetch_stories")
    private Object fetchStories(Long userId, Integer pageNo) {
        return storyUploadService.fetchStories(userId, pageNo);
    }

    @PostMapping("/fetchUserUploadedPosts")
    private Response fetchUserUploadedPosts(Long userId, Integer pageNo) {
        return postUploadService.fetchUserUploadedPosts(userId, pageNo);
    }


    @PostMapping("/updatePostViewCount")
    private Response updatePostViewCount(Long postId) {
        return postUploadService.updateViewCount(postId);
    }

    @PostMapping("/fetchPopularPosts")
    private Object fetchPopularPosts(Long userId,String city,String state,Integer pageNo) {
        return postUploadService.fetchPopularPosts(userId,city, state,pageNo);
    }


    @PostMapping("/deleteYourPost")
    private Response deleteYourPost(Long userId, Long postId) {
        return postUploadService.deleteYourPost(userId, postId);
    }


    @PostMapping("/deleteYourStory")
    private Response deleteYourStory(Long userId, Long storyId) {
        return storyUploadService.deleteYourStory(userId, storyId);
    }


    @PostMapping("/editPostCaption")
    private Response editPostCaption(Long postId, Long userId, String caption) {
        return postUploadService.editPostCaption(postId, userId, caption);
    }



    @PostMapping("/editStoryCaption")
    private Response editStoryCaption(Long storyId, Long userId, String caption) {
        return storyUploadService.editStoryCaption(storyId, userId, caption);
    }


    @PostMapping("/blog_upload")
    private Response uploadBlog(Long userId, String content, String location) {
        return blogService.uploadBlog(userId, content, location);
    }

    @PostMapping("/get_all_blogs")
    private Response getAllBlogs(Long userId) {
        return blogService.getAllBlogs(userId);
    }
}
