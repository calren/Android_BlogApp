package com.caren.weebly;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caren on 9/2/14.
 */
public class BlogPost {
    //private variables
    long _id;
    String _title;
    String _date;
    String _summary;
    ArrayList<String> _items;

    //items are either String text or URI of text

    public BlogPost() {

    }

    public BlogPost(String title, String summary, long id) {
        this._title = title;
        this._summary = summary;
        this._id = id;
    }

    public BlogPost(String title, String date, String summary) {
        this._title = title;
        this._date = date;
        this._summary = summary;
    }

    public BlogPost(String title, String date, String summary, ArrayList<String> items) {
        this._title = title;
        this._date = date;
        this._summary = summary;
        this._items = items;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_summary() {
        return _summary;
    }

    public void set_summary(String _summary) {
        this._summary = _summary;
    }

    public ArrayList<String> get_items() {
        return _items;
    }

    public void set_items(ArrayList<String> _items) {
        this._items = _items;
    }


}
