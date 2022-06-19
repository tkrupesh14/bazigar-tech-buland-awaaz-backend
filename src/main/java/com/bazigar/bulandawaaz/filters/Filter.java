package com.bazigar.bulandawaaz.filters;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Filter {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String filterName;
    private String filterIcon;
    private String filterLUT;

    public Filter() {
    }

    public Filter(String filterName, String filterIcon, String filterLUT) {
        this.filterName = filterName;
        this.filterIcon = filterIcon;
        this.filterLUT = filterLUT;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterIcon() {
        return filterIcon;
    }

    public void setFilterIcon(String filterIcon) {
        this.filterIcon = filterIcon;
    }

    public String getFilterLUT() {
        return filterLUT;
    }

    public void setFilterLUT(String filterLUT) {
        this.filterLUT = filterLUT;
    }
}
