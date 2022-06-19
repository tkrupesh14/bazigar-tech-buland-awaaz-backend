package com.bazigar.bulandawaaz.coins;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-post-api/index.php/api/User")
public class CoinsController {

    @Autowired
    private CoinPurchaseHistoryService coinPurchaseHistoryService;

    @Autowired
    private CoinTransactionHistoryService coinTransactionHistoryService;

    @Autowired
    private CoinService coinService;

    @PostMapping("/purchaseCoins")
    public Response purchaseCoins(Long userId, Long coinCount, String amountSpent) {
        return coinPurchaseHistoryService.purchaseCoins(userId, coinCount, amountSpent);
    }

    @PostMapping("/sendCoins")
    public Response sendCoins(CoinTransactionHelper helper) {
        return coinTransactionHistoryService.sendCoins(helper);
    }

    @PostMapping("/getCoinsForUser")
    public Response getMyTotalCoins(Long userId) {
        return coinService.getCoinsForUser(userId);
    }
}
