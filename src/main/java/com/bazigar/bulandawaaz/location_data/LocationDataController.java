package com.bazigar.bulandawaaz.location_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "buland-awaaz/api/User")
public class LocationDataController {

    @Autowired
    private LocationDataService locationService;

    @PostMapping(path = "/setDataToDb")
    public void setDataToDb() throws IOException {
         locationService.addDataToDb();
    }

    @PostMapping(path = "/fetchLocationByType")
    public Object fetchLocationByType(String type, String value, Integer pageNo) throws IOException {
       return locationService.fetchLocationByType(type,value,pageNo);
    }


}
