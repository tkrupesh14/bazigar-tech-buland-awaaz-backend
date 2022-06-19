package com.bazigar.bulandawaaz.colors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GradientColor {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String colorName;
    private String colorCode1;
    private String colorCode2;

    public GradientColor(String colorName, String colorCode1, String colorCode2) {
        this.colorName = colorName;
        this.colorCode1 = colorCode1;
        this.colorCode2 = colorCode2;
    }

    public GradientColor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode1() {
        return colorCode1;
    }

    public void setColorCode1(String hexColor1) {
        this.colorCode1 = hexColor1;
    }

    public String getColorCode2() {
        return colorCode2;
    }

    public void setColorCode2(String hexColor2) {
        this.colorCode2 = hexColor2;
    }
}
