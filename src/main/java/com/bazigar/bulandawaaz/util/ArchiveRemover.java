package com.bazigar.bulandawaaz.util;

import com.bazigar.bulandawaaz.archiveAndSave.ArchiveRepository;
import com.bazigar.bulandawaaz.archiveAndSave.Archivepost;
import org.springframework.stereotype.Component;

@Component
public class ArchiveRemover implements Runnable{

    private final ArchiveRepository archiveRepository;

    public ArchiveRemover(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }

    @Override
    public void run() {
        for (Archivepost i: archiveRepository.findAll()) {
            // Delete the archived post after 30 days
            if (System.currentTimeMillis() - i.getCreatedAt() >= 2592000000L) {
                System.out.println("Archived post with id "+i.getPost_id()+" has been deleted!");
                archiveRepository.delete(i);
            }
        }
    }
}
