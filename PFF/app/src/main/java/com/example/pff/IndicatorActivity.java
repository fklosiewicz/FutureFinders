package com.example.pff;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class IndicatorActivity<color> extends AppCompatActivity {

    public ArrayList<String> states;
    public ArrayList<String> indicators;
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
        indicators = getIntent().getExtras().getStringArrayList("Indicators");

    }

    // for later: add cap to number of indicators that can be added to list based on if logged in or not
    public void checked(View view) {
        CheckBox c = (CheckBox)view;
        if(c.isChecked()) {
            indicators.add((String)c.getText());
        }
        else {
            indicators.remove(c.getText());
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
            Intent intent = new Intent(this, ResultsActivity.class);
            startActivity(intent);
        }
    }

    public void backToStates(View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("States", states);
        bundle.putStringArrayList("Indicators", indicators);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}