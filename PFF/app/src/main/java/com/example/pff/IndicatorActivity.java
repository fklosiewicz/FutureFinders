package com.example.pff;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class IndicatorActivity<color> extends AppCompatActivity {

    public ArrayList<String> states;
    public ArrayList<Integer> indicators;
    private ImageView imageView;
    private TableRow row1;
    private TableRow row2;
    private CheckBox wage;
    private CheckBox happy;
    private CheckBox tax_rate;
    private CheckBox tax_bracket;
    private CheckBox entertainment;
    private CheckBox healthcare;
    private CheckBox air_water;
    private CheckBox education;
    private CheckBox living_index;
    private CheckBox living_ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicators);

        states = getIntent().getExtras().getStringArrayList("States");
        indicators = getIntent().getExtras().getIntegerArrayList("Indicators");
        for(int id : indicators) {
            CheckBox c = findViewById(id);
            c.setChecked(true);
        }
    }

    // for later: add cap to number of indicators that can be added to list based on if logged in or not
    public void checked(View view) {
        CheckBox c = (CheckBox)view;
        if(c.isChecked()) {
            indicators.add(c.getId());
        }
        else {
            indicators.remove((Integer)c.getId());
        }
    }

    public void results(View view) {
        if(states.isEmpty()) {
            AlertDialog.Builder noStates = new AlertDialog.Builder(view.getContext());
            noStates.setMessage("Please select at least one state").setPositiveButton("Okay", null);
            noStates.show();
        }
        else if(indicators.isEmpty()) {
            AlertDialog.Builder noStates = new AlertDialog.Builder(view.getContext());
            noStates.setMessage("Please select at least one indicator").setPositiveButton("Okay", null);
            noStates.show();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("States", states);
            bundle.putIntegerArrayList("Indicators", indicators);
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void backToStates(View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("States", states);
        bundle.putIntegerArrayList("Indicators", indicators);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}