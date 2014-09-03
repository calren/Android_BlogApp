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

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_SUMMARY = "summary";
    private static final String KEY_NUMOFITEMS = "num_of_items";
    private static final String KEY_ITEM1 = "item1";
    private static final String KEY_ITEM2 = "item2";
    private static final String KEY_ITEM3 = "item3";
    private static final String KEY_ITEM4 = "item4";
    private static final String KEY_ITEM5 = "item5";
    private static final String KEY_ITEM6 = "item6";
    private static final String KEY_ITEM7 = "item7";
    private static final String KEY_ITEM8 = "item8";
    private static final String KEY_ITEM9 = "item9";
    private static final String KEY_ITEM10 = "item10";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_BLOGPOSTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_DATE + " TEXT," + KEY_SUMMARY + " TEXT," + KEY_NUMOFITEMS + " TEXT," + KEY_ITEM1 + " TEXT," +
                KEY_ITEM2 + " TEXT,"+ KEY_ITEM3 + " TEXT," + KEY_ITEM4 + " TEXT," +
                KEY_ITEM5 + " TEXT" + KEY_ITEM6 + " TEXT," + KEY_ITEM7 + " TEXT,"+ KEY_ITEM8 + " TEXT,"
                + KEY_ITEM9 + " TEXT," + KEY_ITEM10 + " TEXT" + ")";
        System.out.println("creating database table: " + CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOGPOSTS);

        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public long addPost(BlogPost bp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, bp.get_title());
        values.put(KEY_DATE, bp.get_date());
        values.put(KEY_SUMMARY, bp.get_summary());

        try {
            ArrayList<String> items = bp.get_items();
            values.put(KEY_NUMOFITEMS, items.size());

            for (int i = 1; 1 <= items.size(); i++) {
                values.put("item" + i, items.get(i-1));
            }
        } catch (Exception e) {
            // no items were in blog post
        }

        // Inserting Row
        long rowId = db.insert(TABLE_BLOGPOSTS, null, values);
        db.close();
        return rowId;
    }

    public BlogPost getPost(long id) {
        String selectQuery = "SELECT  * FROM " + TABLE_BLOGPOSTS + " WHERE id = " + id;
        BlogPost bp = new BlogPost();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            bp.set_title(cursor.getString(1));
            bp.set_date(cursor.getString(2));
            bp.set_summary(cursor.getString(3));

            // add blog post items
            int numOfItems = Integer.parseInt(cursor.getString(4));
            ArrayList<String> items = new ArrayList<String>();

            for (int i = 5; i <= numOfItems + 4; i++) {
                items.add(cursor.getString(i));
            }

            bp.set_items(items);

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
                bp.set_title(cursor.getString(1));
                bp.set_date(cursor.getString(2));
                bp.set_summary(cursor.getString(3));

                // TODO add items

                posts.add(bp);

            } while (cursor.moveToNext());
        }

        return posts;
    }

}