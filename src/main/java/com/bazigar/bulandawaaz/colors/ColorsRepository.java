package com.bazigar.bulandawaaz.colors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorsRepository extends JpaRepository<Colors, Long> {

    @Query(value = "select c from Colors c where c.colorCode = ?1")
    Optional<Colors> findColorsByColorCode(String colorCode);

}
