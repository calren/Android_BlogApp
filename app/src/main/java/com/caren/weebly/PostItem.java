package com.caren.weebly;

public class PostItem {


    public enum PostItemValues {
        TEXT, IMAGE, VIDEO
    }

    // blog post associated with this item
    public long blog_post_id;
    // type of post (video, image, or text)
    public PostItemValues post_type;
    // String of either the text, or URI of video/image
    public String post_value;
    // num in post (for ordering purposes)
    public String post_num;

    public PostItem() {

    }

    public PostItem(long id, String type, String value, String num) {
        super();
        this.blog_post_id = id;
        this.post_type = getPostItemType(type);
        this.post_value = value;
        this.post_num = num;
    }

    public long getBlog_post_id() {
        return blog_post_id;
    }

    public void setBlog_post_id(long blog_post_id) {
        this.blog_post_id = blog_post_id;
    }

    public String getPost_num() {
        return post_num;
    }

    public void setPost_num(String post_num) {
        this.post_num = post_num;
    }

    public String getPost_value() {
        return post_value;
    }

    public void setPost_value(String post_value) {
        this.post_value = post_value;
    }

    public PostItemValues getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = getPostItemType(post_type);
    }

    public PostItemValues getPostItemType(String s) {
        if (s.equals("TEXT")) {
            return PostItemValues.TEXT;
        } else if (s.equals("VIDEO")) {
            return PostItemValues.VIDEO;
        } else  {
            return PostItemValues.IMAGE;
        }
    }


}
