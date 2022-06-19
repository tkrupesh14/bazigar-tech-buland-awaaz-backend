package com.bazigar.bulandawaaz.location_data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationDataRepository
        extends JpaRepository<LocationData, Long>{


    @Query("select DISTINCT s.state from LocationData s ORDER BY s.state asc")
    Page<String> fetchState( Pageable page);

    @Query("select s.district from LocationData s where s.state = ?1")
    Page<String> fetchDistrict(String value, Pageable page);

//    @Query("select s.subDistrict from LocationData s where s.district = ?1")
//    Page<String> fetchSubDistrict(String value, Pageable page);
//
//    @Query("select s.village from LocationData s where s.subDistrict = ?1")
//    Page<String> fetchVillage(String value, Pageable page);
}
