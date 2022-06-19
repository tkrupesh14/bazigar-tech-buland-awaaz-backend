package com.bazigar.bulandawaaz.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "my-post-api/index.php/api/User")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping(path = "/getLoginActivity")
    public List<LocationWithUserId> getLocationsByUserId(Long userId) {
        return locationService.getLocationsByUserId(userId);
    }


}
