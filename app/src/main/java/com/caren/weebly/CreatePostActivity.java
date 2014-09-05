package com.caren.weebly;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreatePostActivity extends Activity {

    private final int REQUEST_CODE = 20;
    public final static int WRITE_TEXT_ACTIVITY_REQUEST_CODE = 34;
    public final static int EDIT_TEXT_ACTIVITY_REQUEST_CODE = 35;
    public final static int EDIT_VIDEO_ACTIVITY_REQUEST_CODE = 100;
    public final static int VIDEO_ACTIVITY_REQUEST_CODE = 101;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public final static int PICK_PHOTO_ACTIVITY_REQUEST_CODE = 1035;
    public final static int EDIT_IMAGE_ACTIVITY_REQUEST_CODE = 1036;
    public int photoCount = 1;
    public String lastPhotoEdit = "";

    private boolean editing = false;

    DragSortListView lvPostItems;
    PostItemAdapter adapterPosts;
    ArrayList<PostItem> aPostItems;

    DataBaseHandler db;
    EditText etTitle;

    long blog_post_id = -1;
    int num_of_items = 0;
    int lastPositionEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        db = new DataBaseHandler(this);

        lvPostItems = (DragSortListView) findViewById(R.id.lvPostItems);
        lvPostItems.setDragEnabled(true);
        etTitle = (EditText) findViewById(R.id.etPostTitle);
        aPostItems = new ArrayList<PostItem>();

        // if editing post, populate data
        if (getIntent().getLongExtra("blog_post_id", 0) != 0) {
            editing = true;
            blog_post_id = getIntent().getLongExtra("blog_post_id", 0);
            BlogPost bp = db.getPost(blog_post_id);

            String title = bp.get_title();

            etTitle.setText(title);
            etTitle.setSelection(etTitle.getText().length());

            aPostItems = db.getAllItems(blog_post_id);

            for (PostItem p : aPostItems) {
                System.out.println("item: " + p.getPost_value());
            }
        }

        // create a blog post entry in the data table if this is new
        if (blog_post_id < 0) {
            String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            BlogPost bp = new BlogPost(date);
            blog_post_id = db.addPost(bp);
        }

        setupDragDopListView();

        // Attach the adapter
        adapterPosts = new PostItemAdapter(this, aPostItems);
        lvPostItems.setAdapter(adapterPosts);

        setupListViewListener();
    }

    public void setupDragDopListView() {
        lvPostItems.setAdapter(adapterPosts);
        lvPostItems.setDropListener(onDrop);
        lvPostItems.setRemoveListener(onRemove);

        DragSortController controller = new DragSortController(lvPostItems);
        controller.setRemoveEnabled(false);
        controller.setSortEnabled(true);
        controller.setDragInitMode(1);

        lvPostItems.setFloatViewManager(controller);
        lvPostItems.setOnTouchListener(controller);
        lvPostItems.setDragEnabled(true);

    }

    private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
        @Override
        public void drop(int from, int to) {
            if (from != to) {
                PostItem item = adapterPosts.getItem(from);
                adapterPosts.remove(item);
                adapterPosts.insert(item, to);
            }
        }
    };

    private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
        @Override
        public void remove(int which) {
            adapterPosts.remove(adapterPosts.getItem(which));
        }
    };

    public void goToEditText(View view) {
        Intent i = new Intent(CreatePostActivity.this, EditTextActivity.class);
        startActivityForResult(i, WRITE_TEXT_ACTIVITY_REQUEST_CODE);
    }

     public void addPicture(View view) {

        AlertDialog.Builder getImageFrom = new AlertDialog.Builder(CreatePostActivity.this);
        getImageFrom.setTitle("Select:");
        final CharSequence[] opsChars = {"Take a picture", "Open Gallery"};
        getImageFrom.setItems(opsChars, new android.content.DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Utils.getPhotoFileUri("photo" + photoCount + ".jpg")); // set the image file name
                    // Start the image capture intent to take photo
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_PHOTO_ACTIVITY_REQUEST_CODE);
                }
                dialog.dismiss();
            }
        });

        getImageFrom.show();
    }

    public void addVideo (View view) {
        AlertDialog.Builder getVideoFrom = new AlertDialog.Builder(CreatePostActivity.this);
        getVideoFrom.setTitle("Select:");
        final CharSequence[] opsChars = {"Take a video", "Open Gallery"};
        getVideoFrom.setItems(opsChars, new android.content.DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takeVideoIntent, VIDEO_ACTIVITY_REQUEST_CODE);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setType("video/*");
                    startActivityForResult(intent, VIDEO_ACTIVITY_REQUEST_CODE);
                }
                dialog.dismiss();
            }
        });

        getVideoFrom.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PostItem postItem;

        // picture taken
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri takenPhotoUri = Utils.getPhotoFileUri("photo" + photoCount + ".jpg");
            photoCount++;
            postItem = new PostItem(blog_post_id, "IMAGE", takenPhotoUri.toString(), String.valueOf(num_of_items++));
            aPostItems.add(postItem);
            adapterPosts.notifyDataSetChanged();

        }

        // picture chosen
        if (requestCode == PICK_PHOTO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri photoUri = data.getData();
                postItem = new PostItem(blog_post_id, "IMAGE", photoUri.toString(), String.valueOf(num_of_items++));
                aPostItems.add(postItem);
                adapterPosts.notifyDataSetChanged();
            }
        }

        // picture changed
        if (requestCode == EDIT_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri takenPhotoUri = Utils.getPhotoFileUri(lastPhotoEdit);
            aPostItems.get(lastPositionEdit).setPost_value(takenPhotoUri.toString());
            adapterPosts.notifyDataSetChanged();
        }

        // video taken
        if (resultCode == RESULT_OK && requestCode == VIDEO_ACTIVITY_REQUEST_CODE) {
            if (data != null) {
                Uri videoUri = data.getData();
                System.out.println("video uri before saving is: " + videoUri);
                postItem = new PostItem(blog_post_id, "VIDEO", videoUri.toString(), String.valueOf(num_of_items++));
                aPostItems.add(postItem);
                adapterPosts.notifyDataSetChanged();
            }
        }

        // video changed
        if (resultCode == RESULT_OK && requestCode == EDIT_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (data != null) {
                Uri videoUri = data.getData();
                aPostItems.get(lastPositionEdit).setPost_value(videoUri.toString());
                adapterPosts.notifyDataSetChanged();
            }
        }

        // text field entered
        if (resultCode == RESULT_OK && requestCode == WRITE_TEXT_ACTIVITY_REQUEST_CODE) {
            String text = data.getExtras().getString("new_item");
            postItem = new PostItem(blog_post_id, "TEXT", text, String.valueOf(num_of_items++));
            aPostItems.add(postItem);
            adapterPosts.notifyDataSetChanged();
        }

        // text field changed
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_ACTIVITY_REQUEST_CODE) {
            String text = data.getExtras().getString("new_item");
            int position = data.getExtras().getInt("position");
            aPostItems.get(position).setPost_value(text);
            adapterPosts.notifyDataSetChanged();
        }
    }

    public void done() {
        Intent data = new Intent();
        String title = etTitle.getText().toString();

        db.addBlogPostTitle(blog_post_id, title);

        createSummary();

        if (editing) {
            db.deleteAllItems(blog_post_id);
        }

        saveAllPostItems();

        setResult(RESULT_OK, data);
        finish();
    }

    public void saveAllPostItems() {
        int lastPositionSeen = 0;

        for (PostItem p : aPostItems) {
            p.setPost_num(Integer.toString(lastPositionSeen));
            db.addPostItem(p);
            lastPositionSeen++;
        }
    }

    // create a summary for the post based on the first item
    public void createSummary() {
        String summary = "";

        if (aPostItems.size() > 0) {
            summary = aPostItems.get(0).getPost_value();
        }

        if (summary.contains("://media")) {
            if (summary.contains("video")) {
                summary = "Video Item";
            } else {
                summary = "Image Item";
            }
        }

        summary = summary.substring(0, Math.min(summary.length(), 50));
        db.addBlogPostSummary(blog_post_id, summary);
    }

    private void setupListViewListener() {

        // edit or delete
        lvPostItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final PostItem.PostItemValues type = aPostItems.get(position).getPost_type();

                AlertDialog.Builder alert_options = new AlertDialog.Builder(CreatePostActivity.this);
                alert_options.setTitle("What would you like to do?");
                final CharSequence[] opsChars = {"Edit", "Delete"};
                alert_options.setItems(opsChars, new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            lastPositionEdit = position;
                            switch (type) {
                                case TEXT:
                                    Intent i = new Intent(CreatePostActivity.this, EditTextActivity.class);
                                    i.putExtra("position", position);
                                    i.putExtra("old_text", aPostItems.get(position).getPost_value());
                                    startActivityForResult(i, EDIT_TEXT_ACTIVITY_REQUEST_CODE);
                                    break;
                                case IMAGE:
                                    AlertDialog.Builder getImageFrom = new AlertDialog.Builder(CreatePostActivity.this);
                                    getImageFrom.setTitle("Select:");
                                    final CharSequence[] opsChars = {"Take a picture", "Open Gallery"};
                                    getImageFrom.setItems(opsChars, new android.content.DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(which == 0){
                                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                lastPhotoEdit = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + ".jpg";
                                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Utils.getPhotoFileUri(lastPhotoEdit));
                                                startActivityForResult(intent, EDIT_IMAGE_ACTIVITY_REQUEST_CODE);
                                            } else {
                                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                startActivityForResult(intent, EDIT_IMAGE_ACTIVITY_REQUEST_CODE);
                                            }
                                        }
                                    });
                                    getImageFrom.show();
                                    break;
                                case VIDEO:
                                    AlertDialog.Builder getVideoFrom = new AlertDialog.Builder(CreatePostActivity.this);
                                    getVideoFrom.setTitle("Select:");
                                    final CharSequence[] ops = {"Take a video", "Open Gallery"};
                                    getVideoFrom.setItems(ops, new android.content.DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(which == 0){
                                                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                                                    startActivityForResult(takeVideoIntent, EDIT_VIDEO_ACTIVITY_REQUEST_CODE);
                                                }
                                            } else {
                                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                                intent.setType("video/*");
                                                startActivityForResult(intent, EDIT_VIDEO_ACTIVITY_REQUEST_CODE);
                                            }
                                            dialog.dismiss();
                                        }
                                    });

                                    getVideoFrom.show();
                                    break;
                            }

                        } else {
                            // delete
                            aPostItems.remove(position);
                            adapterPosts.notifyDataSetChanged();
                        }
                    }
                });

                alert_options.show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            done();
        }
        return super.onOptionsItemSelected(item);
    }
}
