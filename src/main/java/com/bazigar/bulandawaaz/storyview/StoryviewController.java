package com.bazigar.bulandawaaz.storyview;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/my-post-api/index.php/api/User")
public class StoryviewController {

    @Autowired
    private StoryviewService storyviewService;

    @PostMapping("/storyview")
    public Response storyViewClicked(StoryviewHelper helper) {
        return storyviewService.storyviewClicked(helper);
    }

    @PostMapping("storyviewedBy")
    public Response storyViewedBy(Long storyId) {
        return storyviewService.storyViewedBy(storyId);
    }

}
