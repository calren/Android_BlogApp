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
public class BlogPostAdapter extends ArrayAdapter<BlogPostItem> {

    public BlogPostAdapter(Context context, ArrayList<BlogPostItem> users) {
        super(context, R.layout.blog_post_item, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BlogPostItem blogPost = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.blog_post_item, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvSummary = (TextView) convertView.findViewById(R.id.tvSummary);
        tvTitle.setText(blogPost.title);
        tvSummary.setText(blogPost.summary);

        return convertView;
    }


}
