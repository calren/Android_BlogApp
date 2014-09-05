package com.caren.weebly;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class BlogPostsActivity extends Activity {

    ArrayList<BlogPost> aPosts;
    BlogPostAdapter bpAdapter;
    ListView lvBlogPosts;

    DataBaseHandler db;

    public final static int CREATE_POST_ACTIVITY_REQUEST_CODE = 5;
    public final static int EDIT_POST_ACTIVITY_REQUEST_CODE = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_posts);

        aPosts = new ArrayList<BlogPost>();

        db = new DataBaseHandler(this);

        lvBlogPosts = (ListView) findViewById(R.id.lvBlogPosts);
        bpAdapter = new BlogPostAdapter(this, aPosts);
        lvBlogPosts.setAdapter(bpAdapter);

        getListOfBlogPosts();

        setupListViewListener();
    }

    private void setupListViewListener() {

        // remove with long click?
//        lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId) {
//                items.remove(position);
//                preSorted.remove(((TextView)view).getText());
//                itemsAdapter.notifyDataSetChanged();
//                saveItems();
//                return true;
//            }
//        });

        // go into edit mode
        lvBlogPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(BlogPostsActivity.this, CreatePostActivity.class);
                i.putExtra("blog_post_id", aPosts.get(position).get_id());
                startActivityForResult(i, EDIT_POST_ACTIVITY_REQUEST_CODE);

            }
        });
    }

    public void onCreateBlogPost(View view) {
        Intent i = new Intent(BlogPostsActivity.this, CreatePostActivity.class);
        startActivityForResult(i, CREATE_POST_ACTIVITY_REQUEST_CODE);
    }

    public void getListOfBlogPosts() {
        aPosts.clear();

        List<BlogPost> blogPosts = db.getAllPosts();

        for (BlogPost bp : blogPosts) {
            BlogPost blogToAdd = new BlogPost(bp.get_title(), bp.get_summary(), bp.get_id());
            aPosts.add(blogToAdd);
        }

        bpAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( ((requestCode == CREATE_POST_ACTIVITY_REQUEST_CODE) || (requestCode == EDIT_POST_ACTIVITY_REQUEST_CODE)) && resultCode == RESULT_OK) {
            getListOfBlogPosts();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getListOfBlogPosts();

    }

}
