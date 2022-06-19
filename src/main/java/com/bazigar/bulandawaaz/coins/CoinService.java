package com.bazigar.bulandawaaz.coins;

import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoinService {
    @Autowired
    private CoinTransactionHistoryRepository coinTransactionHistoryRepository;

    @Autowired
    private CoinPurchaseHistoryRepository coinPurchaseHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    public Response getCoinsForUser(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required and cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        List<CoinPurchaseHistory> coinsPurchased = coinPurchaseHistoryRepository
                .findAllByUser(userId);
        List<CoinTransactionHistory> coinsReceivedAsAwards = coinTransactionHistoryRepository.
                findAllByOtherUser(userId);
        Long totalCoins = 0L;
        for (CoinPurchaseHistory i: coinsPurchased) {
            totalCoins += i.getCoins();
        }
        for (CoinTransactionHistory j: coinsReceivedAsAwards) {
            totalCoins += j.getCoins();
        }
        return new Response(
                "Total coins obtained!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                totalCoins
        );
    }
}
