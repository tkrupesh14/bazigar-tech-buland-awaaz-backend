package com.bazigar.bulandawaaz.util;

public class Collection {

    private Integer videoLibraryId;
    private String guid;
    private String name;
    private Long videoCount;
    private String previewVideoIds;

    public Collection() {
    }

    public Collection(Integer videoLibraryId, String guid, String name, Long videoCount, String previewVideoIds) {
        this.videoLibraryId = videoLibraryId;
        this.guid = guid;
        this.name = name;
        this.videoCount = videoCount;
        this.previewVideoIds = previewVideoIds;
    }

    public Integer getVideoLibraryId() {
        return videoLibraryId;
    }

    public void setVideoLibraryId(Integer videoLibraryId) {
        this.videoLibraryId = videoLibraryId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Long videoCount) {
        this.videoCount = videoCount;
    }

    public String getPreviewVideoIds() {
        return previewVideoIds;
    }

    public void setPreviewVideoIds(String previewVideoIds) {
        this.previewVideoIds = previewVideoIds;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "videoLibraryId=" + videoLibraryId +
                ", guid='" + guid + '\'' +
                ", name='" + name + '\'' +
                ", videoCount=" + videoCount +
                ", previewVideoIds='" + previewVideoIds + '\'' +
                '}';
    }
}
