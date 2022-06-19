package com.bazigar.bulandawaaz.tags;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/my-post-api/index.php/api/User")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/tag")
    public Response tagUserOnPost(TagHelper helper) {
        return tagService.tagUserOnPost(helper);
    }

    @PostMapping("/getPostsWhereIAmTagged")
    public Response getPostsWhereIAmTagged(Long userId, Integer pageNo) {
        return tagService.getPostsWhereIAmTagged(userId, pageNo);
    }
}
