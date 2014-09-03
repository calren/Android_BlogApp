package com.caren.weebly;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class BlogPostsActivity extends Activity {

    ArrayList<BlogPostItem> arrayOfPosts;
    BlogPostAdapter bpAdapter;
    ListView lvBlogPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_posts);

        arrayOfPosts = new ArrayList<BlogPostItem>();
        bpAdapter = new BlogPostAdapter(this, arrayOfPosts);
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

    //TODO
    public void onCreateBlogPost() {

    }

}
