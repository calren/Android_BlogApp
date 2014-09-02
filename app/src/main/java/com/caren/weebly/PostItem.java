package com.caren.weebly;

/**
 * Created by Caren on 9/2/14.
 */
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
        this.value = value;
    }


}
