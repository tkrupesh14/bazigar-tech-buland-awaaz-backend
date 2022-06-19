package com.bazigar.bulandawaaz.custom_ads;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/my-post-api/index.php/api/User")
public class CustomAdController {

    @Autowired
    private CustomAdService customAdService;

    @PostMapping("/createCustomAd")
    public Response createCustomAd(CustomAdHelper helper) throws IOException {
        return customAdService.createCustomAd(helper);
    }

    @PostMapping("/somebodyViewedYourAd")
    public Response somebodyViewedYourAd(Long customAdId) {
        return customAdService.somebodyViewedYourAd(customAdId);
    }

}
