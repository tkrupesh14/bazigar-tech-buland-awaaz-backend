package com.bazigar.bulandawaaz.coins;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinPurchaseHistoryRepository extends JpaRepository<CoinPurchaseHistory, Long> {

    @Query("select c from CoinPurchaseHistory c where c.user.id = ?1")
    List<CoinPurchaseHistory> findAllByUser(Long userId);

}
