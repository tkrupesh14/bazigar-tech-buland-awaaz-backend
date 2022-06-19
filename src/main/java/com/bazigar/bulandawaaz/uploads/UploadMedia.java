package com.bazigar.bulandawaaz.uploads;

import org.springframework.web.multipart.MultipartFile;

public class UploadMedia {

    private Long userId;
    private String location;
    private String caption;
    private String city;
    private String state;
    private String category;

    private MultipartFile userFile;

    public UploadMedia() {
    }

    public UploadMedia(Long userId, String location, String caption,
                       MultipartFile userFile) {
        this.userId = userId;
        this.location = location;
        this.caption = caption;
        this.userFile = userFile;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public MultipartFile getUserFile() {
        return userFile;
    }

    public void setUserFile(MultipartFile userFile) {
        this.userFile = userFile;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UploadMedia{" +
                "userId=" + userId +
                ", location='" + location + '\'' +
                ", caption='" + caption + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", userFile=" + userFile +
                '}';
    }
}
