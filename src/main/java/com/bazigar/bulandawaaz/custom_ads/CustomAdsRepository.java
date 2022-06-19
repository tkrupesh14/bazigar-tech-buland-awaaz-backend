package com.bazigar.bulandawaaz.custom_ads;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CustomAdsRepository extends JpaRepository<CustomAd, Long> {

    @Transactional
    @Modifying
    @Query("update CustomAd c set c.remainingCost = ?3, c.adViews = ?2 where c.adId = ?1")
    void updateCustomAdOnView(Long customAdId, Long views, Double costRemaining);

}
