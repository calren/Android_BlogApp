package com.caren.weebly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "blog_post_manager";

    private static final String TABLE_BLOGPOSTS = "blog_posts";
    private static final String TABLE_ITEMS = "blog_items";

    // Blog post Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_SUMMARY = "summary";

    // Blog post items table columns
    private static final String KEY_NUMOFITEMS = "num_of_items";
    private static final String KEY_BLOG_POST_ID = "blog_post_id";
    private static final String KEY_POST_TYPE = "post_type";
    private static final String KEY_POST_VALUE = "post_value";
    private static final String KEY_POST_NUM = "post_num";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_BLOGPOSTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT," + KEY_TITLE + " TEXT," +
                KEY_SUMMARY + " TEXT" + ")";

        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_BLOG_POST_ID + " INTEGER KEY," + KEY_POST_TYPE + " TEXT,"
                + KEY_POST_NUM + " TEXT," + KEY_POST_VALUE + " TEXT" + ")";

        db.execSQL(CREATE_POSTS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOGPOSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);

        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public long addPostItem(PostItem p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BLOG_POST_ID, p.getBlog_post_id());
        values.put(KEY_POST_TYPE, p.getPost_type().toString());
        values.put(KEY_POST_VALUE, p.getPost_value());
        values.put(KEY_POST_NUM, p.getPost_num());

        long rowId = db.insert(TABLE_ITEMS, null, values);
        db.close();
        return rowId;

    }

    /*
    gets all post items associated to a particular blog
     */
    public ArrayList<PostItem> getAllItems(long blog_post_id) {
        ArrayList<PostItem> items = new ArrayList<PostItem>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS + " WHERE blog_post_id = " + blog_post_id + " ORDER BY post_num ASC";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                PostItem p = new PostItem();
                p.setBlog_post_id(cursor.getLong(1));
                p.setPost_type(cursor.getString(2));
                p.setPost_num(cursor.getString(3));
                p.setPost_value(cursor.getString(4));

                items.add(p);

            } while (cursor.moveToNext());
        }

        return items;
    }

    public long addPost(BlogPost bp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, bp.get_date());

        // Inserting Row
        long rowId = db.insert(TABLE_BLOGPOSTS, null, values);
        db.close();
        return rowId;
    }

    public void addBlogPostTitle(long bpID, String title) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);

        db.update(TABLE_BLOGPOSTS, cv, "id "+"="+bpID, null);
        db.close();
    }

    public void addBlogPostSummary(long bpID, String summary) {

    }


    public BlogPost getPost(long id) {
        String selectQuery = "SELECT  * FROM " + TABLE_BLOGPOSTS + " WHERE id = " + id;
        BlogPost bp = new BlogPost();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            bp.set_date(cursor.getString(1));
            bp.set_title(cursor.getString(2));
            bp.set_summary(cursor.getString(3));
        }

        return bp;
    }

    public List<BlogPost> getAllPosts() {
        List<BlogPost> posts = new ArrayList<BlogPost>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BLOGPOSTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                BlogPost bp = new BlogPost();
                bp.set_id(Integer.parseInt(cursor.getString(0)));
                bp.set_date(cursor.getString(1));
                bp.set_title(cursor.getString(2));
                bp.set_summary(cursor.getString(3));

                posts.add(bp);

            } while (cursor.moveToNext());
        }

        return posts;
    }

}