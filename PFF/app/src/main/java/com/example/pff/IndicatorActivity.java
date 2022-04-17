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

/**
 * This class is the indicator activity controller class.
 *
 * The class is responsible for the selection of different indicators after the selection of the states
 * already been made.
 *
 * There is a range of 10 different indicators that the member user may choose from.
 *
 * @author Filip Klosiewicz
 * @author Sofia Ozol
 * @author Max Pandolpho
 * @author Samantha Cheng
 * @author Zining Ou
 */

public class IndicatorActivity<color> extends AppCompatActivity {

    public ArrayList<Integer> states;
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


    /**
     * The onCreate populates the current activity with the active user, the user's selected states, and
     * if it is not the first search of the user, the previously selected indicators are saved and
     * remain checked.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final long startTime = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicators);

//        if(getIntent().getExtras()==null){
//            return;
//        }

        states = getIntent().getExtras().getIntegerArrayList("States");
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

        final long endTime = System.currentTimeMillis();
        System.out.println("TOTAL EXECUTION TIME: " + (endTime - startTime));
    }

    /**
     * The alert function that informs the user the maximum number of indicators to be selected is five.
     *
     * @param view
     */
    // for later: add cap to number of indicators that can be added to list based on if logged in or not
    public void checked(View view) {
        final long startTime = System.currentTimeMillis();
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
        final long endTime = System.currentTimeMillis();
        System.out.println("TOTAL EXECUTION TIME: " + (endTime - startTime));
    }

    /**
     * This function is the final step in seeing the results for a member user. It passes the currently
     * selected states and the currently selected indicators for the active user, proceeds to launch
     * the results activity controller class, and populates the table in that controller class.
     *
     * @param view
     */
    public void results(View view) {
        if(indicators.isEmpty()) {
            AlertDialog.Builder noStates = new AlertDialog.Builder(view.getContext());
            noStates.setMessage("Please select at least one indicator").setPositiveButton("Okay", null);
            noStates.show();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList("States", states);
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
        bundle.putIntegerArrayList("States", states);
        bundle.putIntegerArrayList("Indicators", indicators);
        bundle.putSerializable("activeUser", activeUser);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * The OpenExplanations function allows a member user to get a better insight of what each of the indicators signify.
     *
     * This feature is very helpful for newcomers to the application who might not know some of the indicators signify.
     *
     * Along with the tips, the source of the information is also shown to the user.
     */
    private void OpenExplanations(){
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("States", states);
        bundle.putIntegerArrayList("Indicators", indicators);
        bundle.putSerializable("activeUser", activeUser);
        bundle.putInt("question",question);
        Intent intent = new Intent(IndicatorActivity.this, ExplanationActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}