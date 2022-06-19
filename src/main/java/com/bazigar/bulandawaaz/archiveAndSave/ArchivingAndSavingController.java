package com.bazigar.bulandawaaz.archiveAndSave;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/buland-awaaz/api/User")
public class ArchivingAndSavingController {

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private SavedpostsService savedpostsService;

    @PostMapping(path = "/archivePost")
    public Response archivePost(ArchiveHelper helper) {
        return archiveService.archivePost(helper);
    }

    @PostMapping(path = "/savePost")
    public Response savePost(ArchiveHelper helper) {
        return savedpostsService.savePost(helper);
    }

    @PostMapping("/archivedPosts")
    public Response fetchPostsArchivedByUser(Long userId) {
        return archiveService.fetchArchivedPosts(userId);
    }

    @PostMapping("/savedPosts")
    public Response fetchPostsSavedByUser(Long userId) {
        return savedpostsService.fetchSavedPosts(userId);
    }
}
