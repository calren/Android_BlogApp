package com.caren.weebly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.caren.weebly.R;

public class EditTextActivity extends Activity {
    EditText etNewItem;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        etNewItem = (EditText) findViewById(R.id.etText);

        try {
            String oldText = getIntent().getStringExtra("old_text");
            position = getIntent().getIntExtra("position", -1);
            System.out.println("position is " + position);
            etNewItem.setText(oldText);
        } catch (Exception e) {
            // this is a new edit
        }

    }

    public void finishEditText(View view) {
        Intent data = new Intent();
        data.putExtra("new_item", etNewItem.getText().toString());
        if (position >=0) {
            data.putExtra("position", position);
        }
        setResult(RESULT_OK, data);
        finish();
    }

}
