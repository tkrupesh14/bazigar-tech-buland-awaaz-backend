package com.bazigar.bulandawaaz.archiveAndSave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveRepository extends JpaRepository<Archivepost, Long> {

    @Query(value = "select a from Archivepost a where a.user.id = ?1")
    public List<Archivepost> findByUserId(Long userId);

}
