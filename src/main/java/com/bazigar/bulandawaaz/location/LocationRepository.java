package com.bazigar.bulandawaaz.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository
        extends JpaRepository<Location, Long>{

    public List<Location> findByUserId(Long userId);



}
