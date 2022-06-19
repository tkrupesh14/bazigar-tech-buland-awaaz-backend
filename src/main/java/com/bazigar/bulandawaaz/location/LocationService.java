package com.bazigar.bulandawaaz.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<LocationWithUserId> getLocationsByUserId(Long userId) {
        List<Location> locs = locationRepository.findByUserId(userId);
        List<LocationWithUserId> locations= new ArrayList<LocationWithUserId>();
        for (Location location : locs) {
            locations.add(new LocationWithUserId(
                    location.getId(),
                    location.getUser().getId(),
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getIp(),
                    location.getCountry(),
                    location.getDeviceName(),
                    location.getAddress(),
                    location.getCity(),
                    location.getCreatedAt()
            ));
        }
        return locations;
    }

}
