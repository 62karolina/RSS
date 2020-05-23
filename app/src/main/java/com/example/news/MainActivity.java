package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        open = findViewById(R.id.open);

        open.setOnClickListener(this);

    }

    public void onCheckBoxClicked(View v){

        switch (v.getId()){
            case R.id.checkBoxNaviny:

        }
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, NewsActivity.class);

        CheckBox isChecked = (CheckBox) v;

        boolean checked = isChecked.isChecked();
        switch (v.getId()){
            case R.id.checkBoxNaviny:
                i.putExtra("naviny", checked); break;
            case R.id.checkBoxMinsknews:
                i.putExtra("minsk", checked); break;
            case R.id.checkBoxSputnik:
                i.putExtra("sputnik", checked); break;
        }
        startActivity(i);
    }
}
