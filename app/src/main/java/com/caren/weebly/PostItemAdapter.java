package com.caren.weebly;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

public class PostItemAdapter extends ArrayAdapter<PostItem> {

    public PostItemAdapter(Context context, ArrayList<PostItem> items) {
        super(context, 0, items);
    }

    @Override
    public int getViewTypeCount() {
        // text, image, or video
        return PostItem.PostItemValues.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).value.ordinal();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PostItem item = getItem(position);
        int type = getItemViewType(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            // Get the data item type for this position
            type = getItemViewType(position);
            // Inflate XML layout based on the type
            convertView = getInflatedLayoutForType(type);
        }

        // Lookup view for data population
        switch (type) {
            case 0:
                TextView tvText = (TextView) convertView.findViewById(R.id.textView);
                tvText.setText(item.label);
                break;
            case 1:
                ImageView ivImage = (ImageView) convertView.findViewById(R.id.imageView);
                ivImage.setImageURI(Uri.parse(item.label));
                break;
            case 2:
                VideoView vvVideo = (VideoView) convertView.findViewById(R.id.videoView);
                vvVideo.setVideoURI(Uri.parse(item.label));
                break;

        }

        return convertView;
    }

    private View getInflatedLayoutForType(int type) {
        if (type == PostItem.PostItemValues.TEXT.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.blog_post_item_text, null);
        } else if (type == PostItem.PostItemValues.IMAGE.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.blog_post_item_image, null);
        } else if (type == PostItem.PostItemValues.VIDEO.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.blog_post_item_video, null);
        } else {
            return null;
        }
    }

}
