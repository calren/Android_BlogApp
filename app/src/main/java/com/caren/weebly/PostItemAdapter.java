package com.caren.weebly;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
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
        return getItem(position).getPost_type().ordinal();

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
                tvText.setText(item.getPost_value());
                break;
            case 1:
                ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
                imageView.setImageURI(Uri.parse(item.getPost_value()));
                break;
            case 2:
                final VideoView vvVideo = (VideoView) convertView.findViewById(R.id.video_view);
                vvVideo.setVideoURI(Uri.parse(item.getPost_value()));
                vvVideo.setMediaController(new MediaController(this.getContext()));
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
