package com.bazigar.bulandawaaz.music;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Music {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String audio = "";
    private Long duration = 0L;
    private String name = "";
    private String description = "";
    private String thumbnail = "";
    private String artist = "";
    private Long createdAt;

    public Music(String audio, Long duration, String name, String description, String thumbnail, String artist, Long createdAt) {
        this.audio = audio;
        this.duration = duration;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.artist = artist;
        this.createdAt = createdAt;
    }

    public Music() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", audio='" + audio + '\'' +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", artist='" + artist + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
