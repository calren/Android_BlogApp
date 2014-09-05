package com.caren.weebly;

import android.content.Context;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

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
        PostItem item = getItem(position);
        int type = getItemViewType(position);

        if (convertView == null) {
            type = getItemViewType(position);
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
                vvVideo.setMediaController(new MediaController(this.getContext()));

                MediaController mc = new MediaController(this.getContext());
                final ImageButton buttonStart = (ImageButton) convertView.findViewById(R.id.play_button);

                mc.setAnchorView(vvVideo);
                mc.setMediaPlayer(vvVideo);
                vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        buttonStart.setVisibility(View.VISIBLE);
                    }
                });
//                vvVideo.setMediaController(mc);
                vvVideo.setVideoURI(Uri.parse(item.getPost_value()));
                buttonStart.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        vvVideo.start();
                        buttonStart.setVisibility(View.INVISIBLE);
                    }
                });

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
