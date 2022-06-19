package com.bazigar.bulandawaaz.location;

import com.bazigar.bulandawaaz.User.User;

import javax.persistence.*;

@Entity
public class Location {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

//    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    private User user;
    private String latitude = "";
    private String longitude = "";
    private String ip = "";
    private String country = "";
    private String deviceName = "";
    private String address = "";
    private String city = "";
    private Long createdAt;

    public Location() {
    }

    public Location(User user) {
        this.user = user;
    }

    public Location(User user, String latitude, String longitude,
                    String ip, String country, String deviceName,
                    String address, String city, Long createdAt) {
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ip = ip;
        this.country = country;
        this.deviceName = deviceName;
        this.address = address;
        this.city = city;
        this.createdAt = createdAt;
    }

    public Location(String latitude, String longitude,
                    String ip, String country, String deviceName,
                    String address, String city, Long createdAt) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.ip = ip;
        this.country = country;
        this.deviceName = deviceName;
        this.address = address;
        this.city = city;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", user=" + user +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", ip='" + ip + '\'' +
                ", country='" + country + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
