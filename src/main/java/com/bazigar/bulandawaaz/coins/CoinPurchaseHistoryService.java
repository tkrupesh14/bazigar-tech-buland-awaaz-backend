package com.bazigar.bulandawaaz.coins;

import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoinPurchaseHistoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoinPurchaseHistoryRepository coinPurchaseHistoryRepository;

    public Response purchaseCoins(Long userId, Long coinCount, String amountSpent) {
        if (userId == null || coinCount == null || amountSpent == null) {
            return new Response(
                    "userId, coinCount and amountSpent are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return new Response(
                    "No user found with the given id!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        CoinPurchaseHistory coinPurchaseHistory = new CoinPurchaseHistory(
                userOptional.get(),
                coinCount,
                amountSpent,
                System.currentTimeMillis()
        );
        coinPurchaseHistoryRepository.save(coinPurchaseHistory);
        return new Response(
                "Coins purchased successfully!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }
}
