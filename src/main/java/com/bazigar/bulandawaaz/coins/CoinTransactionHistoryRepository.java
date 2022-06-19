package com.bazigar.bulandawaaz.coins;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinTransactionHistoryRepository extends JpaRepository<CoinTransactionHistory, Long> {

    @Query("select c from CoinTransactionHistory c where c.otherUser.id = ?1")
    List<CoinTransactionHistory> findAllByOtherUser(Long otherUserId);

}
