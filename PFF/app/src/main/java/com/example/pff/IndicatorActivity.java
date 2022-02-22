package com.example.pff;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class IndicatorActivity<color> extends AppCompatActivity {

    public ArrayList<String> states;
    public ArrayList<String> indicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicators);

        states = savedInstanceState.getStringArrayList("States");
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

}