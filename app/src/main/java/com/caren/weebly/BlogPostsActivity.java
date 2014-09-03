package com.caren.weebly;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class BlogPostsActivity extends Activity {

    ArrayList<BlogPostItem> aPosts;
    BlogPostAdapter bpAdapter;
    ListView lvBlogPosts;

    DataBaseHandler db;

    public final static int CREATE_POST_ACTIVITY_REQUEST_CODE = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_posts);

        aPosts = new ArrayList<BlogPostItem>();

        db = new DataBaseHandler(this);

        List<BlogPost> blogPosts = db.getAllPosts();

        for (BlogPost bp : blogPosts) {
            BlogPostItem blogToAdd = new BlogPostItem(bp.get_title(), bp.get_summary());
            aPosts.add(blogToAdd);
        }

        bpAdapter = new BlogPostAdapter(this, aPosts);
        lvBlogPosts = (ListView) findViewById(R.id.lvBlogPosts);
        lvBlogPosts.setAdapter(bpAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.blog_posts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateBlogPost(View view) {
        Intent i = new Intent(BlogPostsActivity.this, CreatePostActivity.class);
        startActivityForResult(i, CREATE_POST_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        BlogPostItem blogPostItem;

        if (requestCode == CREATE_POST_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            blogPostItem = new BlogPostItem(data.getExtras().getString("title"), data.getExtras().getString("summary"));
            aPosts.add(blogPostItem);
            bpAdapter.notifyDataSetChanged();

        }
    }

}
