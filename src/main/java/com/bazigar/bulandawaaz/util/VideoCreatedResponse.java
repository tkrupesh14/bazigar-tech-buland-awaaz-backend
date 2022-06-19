package com.bazigar.bulandawaaz.util;

import org.springframework.lang.Nullable;

import java.util.Arrays;

public class VideoCreatedResponse {

    private Long videoLibraryId;
    private String guid;
    private String title;
    private String dateUploaded;
    private Long views;
    private Boolean isPublic;
    private Long length;
    private Long status;
    private Long framerate;
    private Integer width;
    private Integer height;
    private @Nullable Long availableResolutions;
    private Long thumbnailCount;
    private Long encodeProgress;
    private Long storageSize;
    private String[] captions;
    private Boolean hasMP4Fallback;
    private String collectionId;
    private String thumbnailFileName;

    public VideoCreatedResponse() {
    }

    public VideoCreatedResponse(Long videoLibraryId, String guid, String title,
                                String dateUploaded, Long views, Boolean isPublic,
                                Long length, Long status, Long framerate,
                                Integer width, Integer height,
                                @Nullable Long availableResolutions,
                                Long thumbnailCount, Long encodeProgress,
                                Long storageSize, String[] captions,
                                Boolean hasMP4Fallback, String collectionId,
                                String thumbnailFileName) {
        this.videoLibraryId = videoLibraryId;
        this.guid = guid;
        this.title = title;
        this.dateUploaded = dateUploaded;
        this.views = views;
        this.isPublic = isPublic;
        this.length = length;
        this.status = status;
        this.framerate = framerate;
        this.width = width;
        this.height = height;
        this.availableResolutions = availableResolutions;
        this.thumbnailCount = thumbnailCount;
        this.encodeProgress = encodeProgress;
        this.storageSize = storageSize;
        this.captions = captions;
        this.hasMP4Fallback = hasMP4Fallback;
        this.collectionId = collectionId;
        this.thumbnailFileName = thumbnailFileName;
    }

    public Long getVideoLibraryId() {
        return videoLibraryId;
    }

    public void setVideoLibraryId(Long videoLibraryId) {
        this.videoLibraryId = videoLibraryId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getFramerate() {
        return framerate;
    }

    public void setFramerate(Long framerate) {
        this.framerate = framerate;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Nullable
    public Long getAvailableResolutions() {
        return availableResolutions;
    }

    public void setAvailableResolutions(@Nullable Long availableResolutions) {
        this.availableResolutions = availableResolutions;
    }

    public Long getThumbnailCount() {
        return thumbnailCount;
    }

    public void setThumbnailCount(Long thumbnailCount) {
        this.thumbnailCount = thumbnailCount;
    }

    public Long getEncodeProgress() {
        return encodeProgress;
    }

    public void setEncodeProgress(Long encodeProgress) {
        this.encodeProgress = encodeProgress;
    }

    public Long getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(Long storageSize) {
        this.storageSize = storageSize;
    }

    public String[] getCaptions() {
        return captions;
    }

    public void setCaptions(String[] captions) {
        this.captions = captions;
    }

    public Boolean getHasMP4Fallback() {
        return hasMP4Fallback;
    }

    public void setHasMP4Fallback(Boolean hasMP4Fallback) {
        this.hasMP4Fallback = hasMP4Fallback;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getThumbnailFileName() {
        return thumbnailFileName;
    }

    public void setThumbnailFileName(String thumbnailFileName) {
        this.thumbnailFileName = thumbnailFileName;
    }

    @Override
    public String toString() {
        return "VideoCreatedResponse{" +
                "videoLibraryId=" + videoLibraryId +
                ", guid='" + guid + '\'' +
                ", title='" + title + '\'' +
                ", dateUploaded='" + dateUploaded + '\'' +
                ", views=" + views +
                ", isPublic=" + isPublic +
                ", length=" + length +
                ", status=" + status +
                ", framerate=" + framerate +
                ", width=" + width +
                ", height=" + height +
                ", availableResolutions=" + availableResolutions +
                ", thumbnailCount=" + thumbnailCount +
                ", encodeProgress=" + encodeProgress +
                ", storageSize=" + storageSize +
                ", captions=" + Arrays.toString(captions) +
                ", hasMP4Fallback=" + hasMP4Fallback +
                ", collectionId='" + collectionId + '\'' +
                ", thumbnailFileName='" + thumbnailFileName + '\'' +
                '}';
    }
}
