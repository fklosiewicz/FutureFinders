package com.example.pff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ResultsActivity<color> extends AppCompatActivity {

    public ArrayList<String> states;
    public ArrayList<String> indicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        states = getIntent().getExtras().getStringArrayList("States");
        indicators = getIntent().getExtras().getStringArrayList("Indicators");

        ((TextView)findViewById(R.id.textView2)).setText(indicators.get(0));
        ((TextView)findViewById(R.id.textView6)).setText(states.get(0));

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