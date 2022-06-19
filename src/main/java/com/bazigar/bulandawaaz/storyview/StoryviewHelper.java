package com.bazigar.bulandawaaz.storyview;

import org.springframework.lang.Nullable;

public class StoryviewHelper {

    private @Nullable
    Long storyId;
    private @Nullable Long userId;

    public StoryviewHelper(@Nullable Long storyId, @Nullable Long userId) {
        this.storyId = storyId;
        this.userId = userId;
    }

    @Nullable
    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(@Nullable Long storyId) {
        this.storyId = storyId;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }
}
