package com.bazigar.bulandawaaz.hashtags;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/buland-awaaz/api/User")
public class HashTagController {

    @Autowired
    private HashTagService hashTagService;

    @PostMapping(value = "/hashtag")
    public Response addOrUpdateHashtag(String hashtag, Long postId, String type) {
        return hashTagService.addOrUpdateHashtag(hashtag, postId, type);
    }

    @PostMapping(value = "/hashtag_by_tag")
    public Response getHashtagByTag(String hashtag) {
        return hashTagService.getHashTagsByTag(hashtag);
    }

    @PostMapping(value = "/getHashTags")
    public Response getHashTags(String password,Integer pageNo) {
        return hashTagService.getHashTags(password,pageNo);
    }

}
