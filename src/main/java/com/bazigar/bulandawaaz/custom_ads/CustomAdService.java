package com.bazigar.bulandawaaz.custom_ads;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.util.BunnyCDN;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomAdService {

    @Autowired
    private CustomAdsRepository customAdsRepository;

    @Autowired
    private UserRepository userRepository;

    public Response createCustomAd(CustomAdHelper helper) throws IOException {
        if (helper.getAd() == null || helper.getUserId() == null) {
            return new Response(
                    "ad and userId are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(helper.getUserId());
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        BunnyCDN bunnyCDN = new BunnyCDN();
        String adFileLocation = bunnyCDN.uploadAd(helper.getAd());
        if (Objects.equals(adFileLocation, "Failed to upload adFile!")) {
            return new Response(
                    "Ad upload failed!",
                    new ResponseStatus(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED.getReasonPhrase()
                    )
            );
        }
        CustomAd ad = new CustomAd(
                adFileLocation,
                helper.getAdCost(),
                user.get(),
                helper.getAdDescription(),
                helper.getAdType(),
                helper.getMediaType(),
                System.currentTimeMillis()
        );
        ad.setRemainingCost(helper.getAdCost());
        customAdsRepository.save(ad);
        return new Response(
                "Ad successfully created!",
                new ResponseStatus(
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.getReasonPhrase()
                ),
                ad.getAdId()
        );
    }

    public Response somebodyViewedYourAd(Long customAdId) {
        if (customAdId == null) {
            return new Response(
                    "customAdId is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<CustomAd> customAd = customAdsRepository.findById(customAdId);
        if (customAd.isEmpty()) {
            return new Response(
                    "No ad found with the given customAdId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Long currentViews = customAd.get().getAdViews();
        Double remainingCost = customAd.get().getRemainingCost();
        if (remainingCost < 5.0) {
            customAdsRepository.delete(customAd.get());
        }
        customAdsRepository.updateCustomAdOnView(
                customAdId,
                currentViews + 1,
                remainingCost - 5.0
        );
        return new Response(
                "Ad viewed successfully!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }
}
