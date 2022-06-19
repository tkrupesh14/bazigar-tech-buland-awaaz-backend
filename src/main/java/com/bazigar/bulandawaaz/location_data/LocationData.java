package com.bazigar.bulandawaaz.location_data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class LocationData {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String state="";
    private String district="";


    public LocationData() {
    }

    public LocationData(String state, String district) {
        this.state = state;
        this.district = district;
    }

    public Long getId() {
        return id;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


}
//interface project





