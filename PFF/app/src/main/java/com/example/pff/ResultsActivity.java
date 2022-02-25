package com.example.pff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultsActivity<color> extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
    }

    public void back(View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("States", new ArrayList<String>());
        bundle.putStringArrayList("Indicators", new ArrayList<String>());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}