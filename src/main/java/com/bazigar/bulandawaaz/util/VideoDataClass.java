package com.bazigar.bulandawaaz.util;

public class VideoDataClass {

    private String videoTitle;
    private VideoCreatedResponse videoCreatedResponse;

    public VideoDataClass() {
    }

    public VideoDataClass(String videoTitle, VideoCreatedResponse videoCreatedResponse) {
        this.videoTitle = videoTitle;
        this.videoCreatedResponse = videoCreatedResponse;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public VideoCreatedResponse getVideoCreatedResponse() {
        return videoCreatedResponse;
    }

    public void setVideoCreatedResponse(VideoCreatedResponse videoCreatedResponse) {
        this.videoCreatedResponse = videoCreatedResponse;
    }

    @Override
    public String toString() {
        return "VideoDataClass{" +
                "videoTitle='" + videoTitle + '\'' +
                ", videoCreatedResponse=" + videoCreatedResponse +
                '}';
    }
}
