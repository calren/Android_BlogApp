package com.caren.weebly;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.caren.weebly.R;

import java.io.File;
import java.util.ArrayList;

public class CreatePostActivity extends Activity {

    private final int REQUEST_CODE = 20;
    private static final int SELECT_PICTURE = 1;
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public final static int WRITE_TEXT_ACTIVITY_REQUEST_CODE = 34;
    public String photoFileName = "photo.jpg";
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
        startActivityForResult(i, WRITE_TEXT_ACTIVITY_REQUEST_CODE);
    }

    // TODO: this method seems clunky
    public void addPicture(View view) {

        AlertDialog.Builder getImageFrom = new AlertDialog.Builder(CreatePostActivity.this);
        getImageFrom.setTitle("Select:");
        final CharSequence[] opsChars = {"Take a picture", "Open Gallery"};
        getImageFrom.setItems(opsChars, new android.content.DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name
                    // Start the image capture intent to take photo
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }else if(which == 1){

                }
                dialog.dismiss();
            }
        });

        getImageFrom.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // picture taken
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri takenPhotoUri = getPhotoFileUri(photoFileName);
            // by this point we have the camera photo on disk
            Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
            System.out.println("picture taken : " + takenImage);
        }

        // text fields entered
        if (resultCode == RESULT_OK && requestCode == WRITE_TEXT_ACTIVITY_REQUEST_CODE) {
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

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
    }
}
