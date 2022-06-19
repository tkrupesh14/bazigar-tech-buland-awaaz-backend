package com.bazigar.bulandawaaz.colors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GradientColorRepository extends JpaRepository<GradientColor, Long> {

    @Query(value = "select g from GradientColor g where g.colorCode1 = ?1 and g.colorCode2 = ?2")
    public Optional<GradientColor> findGradientColorByColorCode1AAndColorCode2(String colorCode1, String colorCode2);

}
