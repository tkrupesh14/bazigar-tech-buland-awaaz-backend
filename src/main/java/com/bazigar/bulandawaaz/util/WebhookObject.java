package com.bazigar.bulandawaaz.util;

public class WebhookObject {

    private Long VideoLibraryId;
    private String VideoGuid;
    private Integer Status;

    public WebhookObject() {
    }

    public WebhookObject(Long videoLibraryId, String videoGuid, Integer status) {
        VideoLibraryId = videoLibraryId;
        VideoGuid = videoGuid;
        Status = status;
    }

    public Long getVideoLibraryId() {
        return VideoLibraryId;
    }

    public void setVideoLibraryId(Long videoLibraryId) {
        VideoLibraryId = videoLibraryId;
    }

    public String getVideoGuid() {
        return VideoGuid;
    }

    public void setVideoGuid(String videoGuid) {
        VideoGuid = videoGuid;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "WebhookObject{" +
                "VideoLibraryId=" + VideoLibraryId +
                ", VideoGuid='" + VideoGuid + '\'' +
                ", Status=" + Status +
                '}';
    }
}
