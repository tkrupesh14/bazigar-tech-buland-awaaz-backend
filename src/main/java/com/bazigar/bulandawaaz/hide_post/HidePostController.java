package com.bazigar.bulandawaaz.hide_post;

import com.bazigar.bulandawaaz.util.Response;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/buland-awaaz/api/User")
public class HidePostController {

    @Autowired
    private HidePostService hidePostService;

    @PostMapping(path = "/hidePost")
    private Response hidePost(HidePost report) throws IOException, JSONException {
        return hidePostService.hidePost(report);
    }
    @PostMapping(path = "/fetchHiddenPosts")
    private Response fetchHiddenPosts(Long userId) throws IOException, JSONException {
        return hidePostService.fetchHiddenPost(userId);
    }

}
