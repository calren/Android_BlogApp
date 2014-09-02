package com.caren.weebly;

public class PostItem {

    public enum PostItemValues {
        TEXT, IMAGE, VIDEO
    }

    public PostItemValues value;
    public String label;

    public PostItem(String label, PostItemValues value) {
        super();

        // either some text, or uri for image/video
        this.label = label;
        // TEXT, IMAGE, or VIDEO
        this.value = value;
    }


}
