package com.bazigar.bulandawaaz.location_data;

import com.google.gson.Gson;
import com.bazigar.bulandawaaz.util.PagedObject;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class LocationDataService {

    @Autowired
    private LocationDataRepository locationRepository;

    @Autowired
    private ResourceLoader resourceLoader;


    public Object fetchLocationByType(String type,String value, Integer pageNo) {
        int totalpages = 0;
        if (type == null) {
            return new Response(
                    "Type is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (value == null) {
            return new Response(
                    "Query is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (pageNo == null) {
            return new Response(
                    "Please provide a page starting at 0!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }


        Pageable page = PageRequest.of(pageNo, 15);
        Page<String> locationPage=null;
        switch (type){
            case "state":{locationPage= locationRepository.fetchState(page);break;}
            case "district":{locationPage = locationRepository.fetchDistrict(value,page); break;}
//            case "subDistrict":{locationPage = locationRepository.fetchSubDistrict(value,page);}
//            case "village":{locationPage = locationRepository.fetchVillage(value,page);}
        }
        if (locationPage==null)
            return new Response(
                    "Unable to fetch location",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );


        totalpages = locationPage.getTotalPages();

        return new Response(
                "Location fetched successfully",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        locationPage.toList().toArray(),
                        pageNo,
                        totalpages
                )
        );


    }





    public void addDataToDb() throws IOException {

        File resource = new ClassPathResource("locationFile.json").getFile();
        BufferedReader br = new BufferedReader(new FileReader(resource));
        LocationObject sample = new Gson().fromJson(br, LocationObject.class);
        for (State locationObject: sample.states){
            for (String district:locationObject.districts){
                        LocationData entity=new LocationData(locationObject.state,district);
                            locationRepository.save(entity);
            }
        }

    }


}
