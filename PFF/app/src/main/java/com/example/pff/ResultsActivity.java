package com.example.pff;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pff.design.User;

import java.util.ArrayList;


public class ResultsActivity<color> extends AppCompatActivity {
//    public ArrayList<String> states;
//    public ArrayList<String> indicators;
    public ArrayList<Integer> states;
    public ArrayList<Integer> indicators;
    public User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        states = getIntent().getExtras().getIntegerArrayList("States");
        indicators = getIntent().getExtras().getIntegerArrayList("Indicators");
        activeUser = getIntent().getExtras().containsKey("activeUser") ? (User)getIntent().getExtras().getSerializable("activeUser") : null;

        LayoutInflater li = getLayoutInflater();
        View v1 = li.inflate(R.layout.activity_main, null);
        View v2 = li.inflate(R.layout.indicators, null);

        ((TextView)findViewById(R.id.textView6)).setText(((Button)v1.findViewById(states.get(0))).getText());
        ((TextView)findViewById(R.id.textView2)).setText(((CheckBox)v2.findViewById(indicators.get(0))).getText());

    }

    public void back(View view) {
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("States", states);
        bundle.putIntegerArrayList("Indicators", indicators);
        if(activeUser != null){
            bundle.putSerializable("activeUser", activeUser);
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}