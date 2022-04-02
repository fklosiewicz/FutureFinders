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

import com.example.pff.design.User;

import java.util.ArrayList;

public class IndicatorActivity<color> extends AppCompatActivity {

    public ArrayList<String> states;
    public ArrayList<Integer> indicators;
    public User activeUser;//so that the user can remain logged in when going back
    public final int IND_MEMBER = 5; // The cap number of indicators.
    public int question;
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
    private Button question1;
    private Button question2;
    private Button question3;
    private Button question4;
    private Button question5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicators);

        states = getIntent().getExtras().getStringArrayList("States");
        indicators = getIntent().getExtras().getIntegerArrayList("Indicators");
        activeUser = (User)getIntent().getExtras().getSerializable("activeUser");
        for(int id : indicators) {
            CheckBox c = findViewById(id);
            c.setChecked(true);
        }

        question1 = (Button) findViewById(R.id.question1);
        question2 = (Button) findViewById(R.id.question2);
        question3 = (Button) findViewById(R.id.question3);
        question4 = (Button) findViewById(R.id.question4);
        question5 = (Button) findViewById(R.id.question5);

        question1.setOnClickListener(view -> {
            question=1;
            OpenExplanations();
        });

        question2.setOnClickListener(view -> {
            question=2;
            OpenExplanations();
        });

        question3.setOnClickListener(view -> {
            question=3;
            OpenExplanations();
        });

        question4.setOnClickListener(view -> {
            question=4;
            OpenExplanations();
        });

        question5.setOnClickListener(view -> {
            question=5;
            OpenExplanations();
        });
    }

    // for later: add cap to number of indicators that can be added to list based on if logged in or not
    public void checked(View view) {
        CheckBox c = (CheckBox)view;
        if(!indicators.contains(c.getId())) {
            if(!(indicators.size()<IND_MEMBER)){
                //indicators.remove((Integer)c.getId()); This doesn't do anything because this indicator is not present in the list
                c.setChecked(false);
                AlertDialog.Builder cap_reached = new AlertDialog.Builder(this);
                cap_reached.setMessage("Maximum " + IND_MEMBER + " indicators for members to choose.\n").setPositiveButton("Okay", null);
                cap_reached.show();
                System.out.println(indicators);
                return;
            }
            indicators.add(c.getId());
        }
        else {
            indicators.remove((Integer)c.getId());
            System.out.println(indicators);
        }
    }

    public void results(View view) {
        if(indicators.isEmpty()) {
            AlertDialog.Builder noStates = new AlertDialog.Builder(view.getContext());
            noStates.setMessage("Please select at least one indicator").setPositiveButton("Okay", null);
            noStates.show();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("States", states);
            bundle.putIntegerArrayList("Indicators", indicators);
            bundle.putSerializable("activeUser", activeUser);
            Intent intent = new Intent(IndicatorActivity.this, ResultsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void onBackPressed(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("States", states);
        bundle.putIntegerArrayList("Indicators", indicators);
        bundle.putSerializable("activeUser", activeUser);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void OpenExplanations(){
        Bundle bundle = new Bundle();
//        bundle.putStringArrayList("States", states);
//        bundle.putIntegerArrayList("Indicators", indicators);
        bundle.putSerializable("activeUser", activeUser);
        bundle.putInt("question",question);
        Intent intent = new Intent(IndicatorActivity.this, ExplanationActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}