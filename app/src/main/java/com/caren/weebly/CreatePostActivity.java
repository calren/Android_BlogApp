package com.caren.weebly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.caren.weebly.R;

import java.util.ArrayList;

public class CreatePostActivity extends Activity {

    private final int REQUEST_CODE = 20;
    ListView lvPostItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        lvPostItems = (ListView) findViewById(R.id.lvPostItems);
        ArrayList<PostItem> aPostItems = new ArrayList<PostItem>();
        // Populate colors into the array
        aPostItems.add(new PostItem("Blue", PostItem.PostItemValues.TEXT));
        aPostItems.add(new PostItem("Green", PostItem.PostItemValues.TEXT));
        // Attach the adapter
        PostItemAdapter adapterColors = new PostItemAdapter(this, aPostItems);
        lvPostItems.setAdapter(adapterColors);

    }

    public void goToEditText(View view) {
        Intent i = new Intent(CreatePostActivity.this, EditTextActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String text = data.getExtras().getString("new_item");

            System.out.println("text entered was: " + text);

            // insert in correct place
//            preSorted.remove(name);
//            preSorted.put(name, priority);
//            items.clear();
//            for(String item: preSorted.keySet()){
//                items.add(item);
//            }
//            saveItems();
//            itemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_post, menu);
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
}
