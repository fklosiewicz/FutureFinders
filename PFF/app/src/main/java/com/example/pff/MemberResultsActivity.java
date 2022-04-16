package com.example.pff;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pff.design.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MemberResultsActivity<color> extends AppCompatActivity {

    public ArrayList<Integer> states;
    public ArrayList<Integer> indicators;
    public User activeUser;

    private static final String URL = "jdbc:mysql://172.16.122.19:3306/future_finders";
    private static final String USER = "finder";
    private static final String PASS = "1234abcd";

    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncTask extends AsyncTask<String, Void, Map<String, String>> {
        protected Map<String, String> doInBackground(String... strings) {
            Map<String, String> info = new HashMap<>();
            System.out.println("Connecting to the database...");
            try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
                System.out.println("Connection valid: " + connection.isValid(5));

                String sql = "SELECT Wage, Happiness, StateTax FROM Indicators WHERE StateABBR = '" + strings[0] + "';";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()) {
                    info.put("State", strings[0]);
                    info.put("Wage", resultSet.getString("Wage"));
                    info.put("StateTax", resultSet.getString("StateTax"));
                    info.put("Happiness", resultSet.getString("Happiness"));
                }
            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error reading information", e);
            }
            return info;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (!result.isEmpty()) {
                TextView State1 = findViewById(R.id.textView6);
                TextView State2 = findViewById(R.id.textView11);
                if(result.get("State").equals(State1.getText().toString())) {
                    TextView Indicator1 = findViewById(R.id.textView7);
                    Indicator1.setText(result.get("Wage"));
                    TextView Indicator2 = findViewById(R.id.textView8);
                    String tax = result.get("StateTax") + "%";
                    Indicator2.setText(tax);
                    TextView Indicator3 = findViewById(R.id.textView9);
                    Indicator3.setText(result.get("Happiness"));

                }
                if(result.get("State").equals(State2.getText().toString())) {
                    TextView Indicator1 = findViewById(R.id.textView12);
                    Indicator1.setText(result.get("Wage"));
                    TextView Indicator2 = findViewById(R.id.textView13);
                    String tax = result.get("StateTax") + "%";
                    Indicator2.setText(tax);
                    TextView Indicator3 = findViewById(R.id.textView14);
                    Indicator3.setText(result.get("Happiness"));
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        states = getIntent().getExtras().getIntegerArrayList("States");
        indicators = getIntent().getExtras().getIntegerArrayList("Indicators");
        activeUser = getIntent().getExtras().containsKey("activeUser") ? (User) getIntent().getExtras().getSerializable("activeUser") : null;

        //This will route to the correct results page depending on whether logged in or not
        if(activeUser == null){
            setContentView(R.layout.guest_results);
        }else{
            setContentView(R.layout.results);
        }

        LayoutInflater li = getLayoutInflater();
        View v1 = li.inflate(R.layout.activity_main, null);
        View v2 = li.inflate(R.layout.indicators, null);

        if(activeUser==null) {
            TextView Indicator1 = findViewById(R.id.textView2);
            Indicator1.setText("Salary");

            TextView Indicator2 = findViewById(R.id.textView3);
            Indicator2.setText("Tax");

            TextView Indicator3 = findViewById(R.id.textView4);
            Indicator3.setText("Happiness");
        }
        else {
            TextView Indicator1, Indicator2, Indicator3, Indicator4, Indicator5;
            String c1, c2, c3, c4, c5;
            switch (indicators.size()) {
                case 1:
                    Indicator1 = findViewById(R.id.textView2);
                    c1 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(0))).getText());
                    Indicator1.setText(c1);
                    break;
                case 2:
                    Indicator1 = findViewById(R.id.textView2);
                    c1 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(0))).getText());
                    Indicator1.setText(c1);
                    Indicator2 = findViewById(R.id.textView3);
                    c2 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(1))).getText());
                    Indicator2.setText(c2);
                    break;
                case 3:
                    Indicator1 = findViewById(R.id.textView2);
                    c1 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(0))).getText());
                    Indicator1.setText(c1);
                    Indicator2 = findViewById(R.id.textView3);
                    c2 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(1))).getText());
                    Indicator2.setText(c2);
                    Indicator3 = findViewById(R.id.textView4);
                    c3 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(2))).getText());
                    Indicator3.setText(c3);
                    break;
                case 4:
                    Indicator1 = findViewById(R.id.textView2);
                    c1 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(0))).getText());
                    Indicator1.setText(c1);
                    Indicator2 = findViewById(R.id.textView3);
                    c2 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(1))).getText());
                    Indicator2.setText(c2);
                    Indicator3 = findViewById(R.id.textView4);
                    c3 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(2))).getText());
                    Indicator3.setText(c3);
                    Indicator4 = findViewById(R.id.textView5);
                    c4 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(3))).getText());
                    Indicator4.setText(c4);
                    break;
                case 5:
                    Indicator1 = findViewById(R.id.textView2);
                    c1 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(0))).getText());
                    Indicator1.setText(c1);
                    Indicator2 = findViewById(R.id.textView3);
                    c2 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(1))).getText());
                    Indicator2.setText(c2);
                    Indicator3 = findViewById(R.id.textView4);
                    c3 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(2))).getText());
                    Indicator3.setText(c3);
                    Indicator4 = findViewById(R.id.textView5);
                    c4 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(3))).getText());
                    Indicator4.setText(c4);
                    Indicator5 = findViewById(R.id.textView21);
                    c5 = shortIndicatorName((String) ((CheckBox) v2.findViewById(indicators.get(4))).getText());
                    Indicator5.setText(c5);
                    break;
            }
        }

        int numStates = states.size();//The number of states selected
        int numIndicators = indicators.size();//The number of indicators
        TextView State1;
        TextView State2;
        TextView State3;
        String s1;
        String s2;
        String s3;

        switch (numStates){
            case 1://only one state is selected
                State1 = findViewById(R.id.textView6);
                State1.setText(((Button)v1.findViewById(states.get(0))).getText());
                s1 = (String) ((Button)v1.findViewById(states.get(0))).getText();
                new InfoAsyncTask().execute(s1);
                findViewById(R.id.rowS2).setVisibility(View.GONE);
                if(activeUser!=null){
                    findViewById(R.id.rowS3).setVisibility(View.GONE);
                }

                break;
            case 2://two states are selected
                State1 = findViewById(R.id.textView6);
                State1.setText(((Button)v1.findViewById(states.get(0))).getText());
                s1 = (String) ((Button)v1.findViewById(states.get(0))).getText();

                State2 = findViewById(R.id.textView11);
                State2.setText(((Button)v1.findViewById(states.get(1))).getText());
                s2 = (String) ((Button)v1.findViewById(states.get(1))).getText();

                new InfoAsyncTask().execute(s1);
                new InfoAsyncTask().execute(s2);
                if(activeUser!=null){
                    findViewById(R.id.rowS3).setVisibility(View.GONE);
                }
                break;
            case 3://three states are selected
                State1 = findViewById(R.id.textView6);
                State1.setText(((Button)v1.findViewById(states.get(0))).getText());
                s1 = (String) ((Button)v1.findViewById(states.get(0))).getText();

                State2 = findViewById(R.id.textView11);
                State2.setText(((Button)v1.findViewById(states.get(1))).getText());
                s2 = (String) ((Button)v1.findViewById(states.get(1))).getText();

                State3 = findViewById(R.id.textView16);
                State3.setText(((Button)v1.findViewById(states.get(2))).getText());
                s3 = (String) ((Button)v1.findViewById(states.get(2))).getText();

                new InfoAsyncTask().execute(s1);
                new InfoAsyncTask().execute(s2);
                new InfoAsyncTask().execute(s3);
                break;
            default://not really needed because we will have 1, 2, or 3 states for sure...
        }

        if(activeUser!=null){//If member is logged in, show the correct number of indicators
            switch(numIndicators){
                case 1:
                    findViewById(R.id.textView3).setVisibility(View.GONE);
                    findViewById(R.id.textView8).setVisibility(View.GONE);
                    findViewById(R.id.textView13).setVisibility(View.GONE);
                    findViewById(R.id.textView18).setVisibility(View.GONE);
                    findViewById(R.id.textView4).setVisibility(View.GONE);
                    findViewById(R.id.textView9).setVisibility(View.GONE);
                    findViewById(R.id.textView14).setVisibility(View.GONE);
                    findViewById(R.id.textView19).setVisibility(View.GONE);
                    findViewById(R.id.textView5).setVisibility(View.GONE);
                    findViewById(R.id.textView10).setVisibility(View.GONE);
                    findViewById(R.id.textView15).setVisibility(View.GONE);
                    findViewById(R.id.textView20).setVisibility(View.GONE);
                    findViewById(R.id.textView21).setVisibility(View.GONE);
                    findViewById(R.id.textView22).setVisibility(View.GONE);
                    findViewById(R.id.textView23).setVisibility(View.GONE);
                    findViewById(R.id.textView24).setVisibility(View.GONE);
                    break;
                case 2:
                    findViewById(R.id.textView4).setVisibility(View.GONE);
                    findViewById(R.id.textView9).setVisibility(View.GONE);
                    findViewById(R.id.textView14).setVisibility(View.GONE);
                    findViewById(R.id.textView19).setVisibility(View.GONE);
                    findViewById(R.id.textView5).setVisibility(View.GONE);
                    findViewById(R.id.textView10).setVisibility(View.GONE);
                    findViewById(R.id.textView15).setVisibility(View.GONE);
                    findViewById(R.id.textView20).setVisibility(View.GONE);
                    findViewById(R.id.textView21).setVisibility(View.GONE);
                    findViewById(R.id.textView22).setVisibility(View.GONE);
                    findViewById(R.id.textView23).setVisibility(View.GONE);
                    findViewById(R.id.textView24).setVisibility(View.GONE);
                    break;
                case 3:
                    findViewById(R.id.textView5).setVisibility(View.GONE);
                    findViewById(R.id.textView10).setVisibility(View.GONE);
                    findViewById(R.id.textView15).setVisibility(View.GONE);
                    findViewById(R.id.textView20).setVisibility(View.GONE);
                    findViewById(R.id.textView21).setVisibility(View.GONE);
                    findViewById(R.id.textView22).setVisibility(View.GONE);
                    findViewById(R.id.textView23).setVisibility(View.GONE);
                    findViewById(R.id.textView24).setVisibility(View.GONE);
                    break;
                case 4:
                    findViewById(R.id.textView21).setVisibility(View.GONE);
                    findViewById(R.id.textView22).setVisibility(View.GONE);
                    findViewById(R.id.textView23).setVisibility(View.GONE);
                    findViewById(R.id.textView24).setVisibility(View.GONE);
                    break;
                default://not needed...
            }
        }
    }

    public String shortIndicatorName(String buttonName){
        if(buttonName.equals("Annual Median Wage"))
            return "Salary";
        else if(buttonName.equals("Happiness Ranking")){
            return "Happiness";
        }
        else if(buttonName.equals("State Income Tax Rate")){
            return "Tax";
        }
        else if(buttonName.equals("State Income Tax Bracket")){
            return "Tax Bracket";
        }
        else if(buttonName.equals("Entertainment, Recreation, and Nightlife Ranking")){
            return "Fun";
        }
        else if(buttonName.equals("Healthcare Ranking")){
            return "Health";
        }
        else if(buttonName.equals("Air and Water Quality")){
            return "Air and Water";
        }
        else if(buttonName.equals("Education Ranking")){
            return "Education";
        }
        else if(buttonName.equals("Cost of Living Index")){
            return "Living Index";
        }
        //Cost of Living Ranking
        return "Living Rank";

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
        if(activeUser != null){
            bundle.putSerializable("activeUser", activeUser);
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
