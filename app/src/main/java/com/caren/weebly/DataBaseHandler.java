package com.caren.weebly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

// tutorial page: http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

public class DataBaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "blog_post_manager";

    private static final String TABLE_BLOGPOSTS = "blog_posts";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_SUMMARY = "summary";
    private static final String KEY_ITEMS = "items";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_BLOGPOSTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_DATE + " TEXT," + KEY_SUMMARY + " TEXT," + KEY_ITEMS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOGPOSTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Add blog post
    public void addPost(BlogPost bp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, bp.get_title());
        values.put(KEY_DATE, bp.get_date());
        values.put(KEY_SUMMARY, bp.get_summary());
//        values.put(KEY_ITEMS, bp.get_items());

        // Inserting Row
        db.insert(TABLE_BLOGPOSTS, null, values);
        db.close(); // Closing database connection
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
                bp.set_title(cursor.getString(1));
                bp.set_date(cursor.getString(2));
                bp.set_summary(cursor.getString(3));

                posts.add(bp);

            } while (cursor.moveToNext());
        }

        return posts;
    }

}