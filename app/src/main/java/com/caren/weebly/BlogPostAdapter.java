package com.caren.weebly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Caren on 9/2/14.
 */
public class BlogPostAdapter extends ArrayAdapter<BlogPost> {

    public BlogPostAdapter(Context context, ArrayList<BlogPost> posts) {
        super(context, R.layout.blog_post_item, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BlogPost blogPost = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.blog_post_item, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvSummary = (TextView) convertView.findViewById(R.id.tvSummary);
        tvTitle.setText(blogPost.get_title());
        tvSummary.setText(blogPost.get_summary());

        return convertView;
    }


}
