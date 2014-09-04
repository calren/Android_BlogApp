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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_text, menu);
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
