package com.bazigar.bulandawaaz.util;

import com.bazigar.bulandawaaz.archiveAndSave.ArchiveService;
import com.bazigar.bulandawaaz.archiveAndSave.ArchiveHelper;
import com.bazigar.bulandawaaz.uploads.story.Story;
import com.bazigar.bulandawaaz.uploads.story.StoryRepository;
import org.springframework.stereotype.Component;


@Component
public class StoryDeleter implements Runnable{

    private ArchiveService archiveService;
    private StoryRepository storyRepository;

    public StoryDeleter(StoryRepository storyRepository, ArchiveService archiveService) {
        this.storyRepository = storyRepository;
        this.archiveService = archiveService;
    }

    @Override
    public void run() {
        for (Story i: storyRepository.findAll()) {
            long milliSeconds= i.getCreatedAt();
            // If the story has been there for 24 hours then archive it
            if (System.currentTimeMillis() - milliSeconds >= 86400000) {
                System.out.println("Story with id "+i.getStoryId()+" has been archived!");
                ArchiveHelper helper = new ArchiveHelper(
                    i.getUser().getId(),
                    4,
                    i.getStoryId(),
                    i.getStoryUrl()
                );
                archiveService.archivePost(helper);
                storyRepository.delete(i);
            }
        }
    }
}
