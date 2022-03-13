package com.example.pff;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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


public class ResultsActivity<color> extends AppCompatActivity {

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
        setContentView(R.layout.guest_results);

        states = getIntent().getExtras().getIntegerArrayList("States");
        indicators = getIntent().getExtras().getIntegerArrayList("Indicators");
        activeUser = getIntent().getExtras().containsKey("activeUser") ? (User) getIntent().getExtras().getSerializable("activeUser") : null;

        LayoutInflater li = getLayoutInflater();
        View v1 = li.inflate(R.layout.activity_main, null);
        View v2 = li.inflate(R.layout.indicators, null);

        TextView Indicator1 = findViewById(R.id.textView2);
        Indicator1.setText("Salary");

        TextView Indicator2 = findViewById(R.id.textView3);
        Indicator2.setText("Tax");

        TextView Indicator3 = findViewById(R.id.textView4);
        Indicator3.setText("Happiness");

        int numStates = states.size();//The number of states selected
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




//        init();

        /*LayoutInflater li = getLayoutInflater();
=======
>>>>>>> origin/master
        View v1 = li.inflate(R.layout.activity_main, null);
        View v2 = li.inflate(R.layout.indicators, null);

        //guest user
        if(activeUser==null) {
            //top row, indicator names
            TextView Indicator1 = findViewById(R.id.textView2);
            Indicator1.setText("Salary");

<<<<<<< HEAD
//    public void init() {
//
//        LayoutInflater li = getLayoutInflater();
//        View v1 = li.inflate(R.layout.activity_main, null);
//        View v2 = li.inflate(R.layout.indicators, null);
//
//        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
//
//        if(activeUser==null){
//            //indicator header for guest
//            TableRow tbrow0 = new TableRow(this);
//            TextView tv0 = new TextView(this);
//            tv0.setText("   ");
//            tv0.setTextColor(Color.BLACK);
//            tbrow0.addView(tv0);
//            TextView tv1 = new TextView(this);
//            tv1.setText(" Salary ");
//            tv1.setTextColor(Color.BLACK);
//            tbrow0.addView(tv1);
//            TextView tv2 = new TextView(this);
//            tv2.setText(" Tax ");
//            tv2.setTextColor(Color.BLACK);
//            tbrow0.addView(tv2);
//            TextView tv3 = new TextView(this);
//            tv3.setText(" Happiness ");
//            tv3.setTextColor(Color.BLACK);
//            tbrow0.addView(tv3);
//            stk.addView(tbrow0);
//            //dynamic rows
//            for (int i = 0; i < states.size(); i++) {
//                TableRow tbrow = new TableRow(this);
//                TextView t1v = new TextView(this);
//                t1v.setText(((Button)v1.findViewById(states.get(i))).getText());
//                t1v.setTextColor(Color.BLACK);
//                t1v.setGravity(Gravity.CENTER);
//                tbrow.addView(t1v);
//                TextView t2v = new TextView(this);
//
//                new InfoAsyncTask().execute("CA");
//
//                t2v.setText("indicators" + i);
//
//                t2v.setTextColor(Color.BLACK);
//                t2v.setGravity(Gravity.CENTER);
//                tbrow.addView(t2v);
//
//
//
//                TextView t3v = new TextView(this);
//                t3v.setText("indicator" + i);
//                t3v.setTextColor(Color.BLACK);
//                t3v.setGravity(Gravity.CENTER);
//                tbrow.addView(t3v);
//                TextView t4v = new TextView(this);
//                t4v.setText("indicator" + i);
//                t4v.setTextColor(Color.BLACK);
//                t4v.setGravity(Gravity.CENTER);
//                tbrow.addView(t4v);
//                stk.addView(tbrow);
//            }
//        }
//        else{
//            int count = 0;
//            //indicator header for member user
//            TableRow tbrow0 = new TableRow(this);
//            TextView tv0 = new TextView(this);
//            tv0.setText("   ");
//            tv0.setTextColor(Color.BLACK);
//            tbrow0.addView(tv0);
//            if(count<indicators.size()){
//                TextView t1v = new TextView(this);
//                t1v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
//                t1v.setTextColor(Color.BLACK);
//                t1v.setGravity(Gravity.CENTER);
//                tbrow0.addView(t1v);
//                count++;
//            }
//            if(count<indicators.size()){
//                TextView t2v = new TextView(this);
//                t2v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
//                t2v.setTextColor(Color.BLACK);
//                t2v.setGravity(Gravity.CENTER);
//                tbrow0.addView(t2v);
//                count++;
//            }
//            if(count<indicators.size()){
//                TextView t3v = new TextView(this);
//                t3v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
//                t3v.setTextColor(Color.BLACK);
//                t3v.setGravity(Gravity.CENTER);
//                tbrow0.addView(t3v);
//                count++;
//            }
//            if(count<indicators.size()){
//                TextView t4v = new TextView(this);
//                t4v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
//                t4v.setTextColor(Color.BLACK);
//                t4v.setGravity(Gravity.CENTER);
//                tbrow0.addView(t4v);
//                count++;
//            }
//            if(count<indicators.size()){
//                TextView t5v = new TextView(this);
//                t5v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
//                t5v.setTextColor(Color.BLACK);
//                t5v.setGravity(Gravity.CENTER);
//                tbrow0.addView(t5v);
//            }
//            stk.addView(tbrow0);
//
//            //dynamic rows
//            for (int i = 0; i < states.size(); i++) {
//                int count2 = 0;
//                TableRow tbrow = new TableRow(this);
//                TextView t0v = new TextView(this);
//                t0v.setText(((Button)v1.findViewById(states.get(i))).getText());
//                t0v.setTextColor(Color.BLACK);
//                t0v.setGravity(Gravity.CENTER);
//                tbrow.addView(t0v);
//                if(count2<indicators.size()){
//                    TextView t1v = new TextView(this);
//                    t1v.setText("indi " + i);
//                    t1v.setTextColor(Color.BLACK);
//                    t1v.setGravity(Gravity.CENTER);
//                    tbrow.addView(t1v);
//                    count2++;
//                }
//                if(count2<indicators.size()){
//                    TextView t2v = new TextView(this);
//                    t2v.setText("indi " + i);
//                    t2v.setTextColor(Color.BLACK);
//                    t2v.setGravity(Gravity.CENTER);
//                    tbrow.addView(t2v);
//                    count2++;
//                }
//                if(count2<indicators.size()){
//                    TextView t3v = new TextView(this);
//                    t3v.setText("indi " + i);
//                    t3v.setTextColor(Color.BLACK);
//                    t3v.setGravity(Gravity.CENTER);
//                    tbrow.addView(t3v);
//                    count2++;
//                }
//                if(count2<indicators.size()){
//                    TextView t4v = new TextView(this);
//                    t4v.setText("indi " + i);
//                    t4v.setTextColor(Color.BLACK);
//                    t4v.setGravity(Gravity.CENTER);
//                    tbrow.addView(t4v);
//                    count2++;
//                }
//                if(count2<indicators.size()){
//                    TextView t5v = new TextView(this);
//                    t5v.setText("indi " + i);
//                    t5v.setTextColor(Color.BLACK);
//                    t5v.setGravity(Gravity.CENTER);
//                    tbrow.addView(t5v);
//                }
//                stk.addView(tbrow);
//            }
//
//        }
//    }
=======
            TextView Indicator2 = findViewById(R.id.textView3);
            Indicator2.setText("Tax");

            TextView Indicator3 = findViewById(R.id.textView4);
            Indicator3.setText("Happiness");

            //the later rows, first column shows state, other columns show data
            int count = 0;
            if(count<states.size()){
                TextView State1 = findViewById(R.id.textView6);
                State1.setText(((Button) v1.findViewById(states.get(0))).getText());
                String s1 = (String) ((Button) v1.findViewById(states.get(0))).getText();
                new InfoAsyncTask().execute(s1);
                count++;
            }
            if(count<states.size()) {
                TextView State2 = findViewById(R.id.textView11);
                State2.setText(((Button) v1.findViewById(states.get(1))).getText());
                String s2 = (String) ((Button) v1.findViewById(states.get(1))).getText();
                new InfoAsyncTask().execute(s2);
            }
        }

        //member user
        else{
            //first row shows indicator names, up to 5 indicators
            int indCount = 0;
            if(indCount<indicators.size()){
                TextView Indicator1 = findViewById(R.id.textView2);
                Indicator1.setText(((Button)v2.findViewById(indicators.get(indCount))).getText());
                indCount++;
            }
            if(indCount<indicators.size()){
                TextView Indicator2 = findViewById(R.id.textView3);
                Indicator2.setText(((Button)v2.findViewById(indicators.get(indCount))).getText());
                indCount++;
            }
            if(indCount<indicators.size()){
                TextView Indicator3 = findViewById(R.id.textView4);
                Indicator3.setText(((Button)v2.findViewById(indicators.get(indCount))).getText());
                indCount++;
            }
            if(indCount<indicators.size()){
                TextView Indicator4 = findViewById(R.id.textView5);
                Indicator4.setText(((Button)v2.findViewById(indicators.get(indCount))).getText());
                indCount++;
            }
            if(indCount<indicators.size()){
                TextView Indicator5 = findViewById(R.id.textView6);
                Indicator5.setText(((Button)v2.findViewById(indicators.get(indCount))).getText());
            }

            //the later rows, first column shows state, other columns show data
            int stateCount = 0;
            if(stateCount<states.size()){
                TextView State1 = findViewById(R.id.textView6);
                State1.setText(((Button) v1.findViewById(states.get(0))).getText());
                String s1 = (String) ((Button) v1.findViewById(states.get(0))).getText();
                new InfoAsyncTask().execute(s1);
                stateCount++;
            }
            if(stateCount<states.size()) {
                TextView State2 = findViewById(R.id.textView11);
                State2.setText(((Button) v1.findViewById(states.get(1))).getText());
                String s2 = (String) ((Button) v1.findViewById(states.get(1))).getText();
                new InfoAsyncTask().execute(s2);
                stateCount++;
            }
            if(stateCount<states.size()) {
                TextView State3 = findViewById(R.id.textView16);
                State3.setText(((Button) v1.findViewById(states.get(2))).getText());
                String s3 = (String) ((Button) v1.findViewById(states.get(2))).getText();
                new InfoAsyncTask().execute(s3);
            }

        }




//        init();

        /*LayoutInflater li = getLayoutInflater();
        View v1 = li.inflate(R.layout.activity_main, null);
        View v2 = li.inflate(R.layout.indicators, null);

        ((TextView)findViewById(R.id.textView6)).setText(((Button)v1.findViewById(states.get(0))).getText());
        ((TextView)findViewById(R.id.textView2)).setText(((CheckBox)v2.findViewById(indicators.get(0))).getText());
        */
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
//    public void init() {
//
//        LayoutInflater li = getLayoutInflater();
//        View v1 = li.inflate(R.layout.activity_main, null);
//        View v2 = li.inflate(R.layout.indicators, null);
//
//        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
//
//        if(activeUser==null){
//            //indicator header for guest
//            TableRow tbrow0 = new TableRow(this);
//            TextView tv0 = new TextView(this);
//            tv0.setText("   ");
//            tv0.setTextColor(Color.BLACK);
//            tbrow0.addView(tv0);
//            TextView tv1 = new TextView(this);
//            tv1.setText(" Salary ");
//            tv1.setTextColor(Color.BLACK);
//            tbrow0.addView(tv1);
//            TextView tv2 = new TextView(this);
//            tv2.setText(" Tax ");
//            tv2.setTextColor(Color.BLACK);
//            tbrow0.addView(tv2);
//            TextView tv3 = new TextView(this);
//            tv3.setText(" Happiness ");
//            tv3.setTextColor(Color.BLACK);
//            tbrow0.addView(tv3);
//            stk.addView(tbrow0);
//            //dynamic rows
//            for (int i = 0; i < states.size(); i++) {
//                TableRow tbrow = new TableRow(this);
//                TextView t1v = new TextView(this);
//                t1v.setText(((Button)v1.findViewById(states.get(i))).getText());
//                t1v.setTextColor(Color.BLACK);
//                t1v.setGravity(Gravity.CENTER);
//                tbrow.addView(t1v);
//                TextView t2v = new TextView(this);
//
//                new InfoAsyncTask().execute("CA");
//
//                t2v.setText("indicators" + i);
//
//                t2v.setTextColor(Color.BLACK);
//                t2v.setGravity(Gravity.CENTER);
//                tbrow.addView(t2v);
//
//
//
//                TextView t3v = new TextView(this);
//                t3v.setText("indicator" + i);
//                t3v.setTextColor(Color.BLACK);
//                t3v.setGravity(Gravity.CENTER);
//                tbrow.addView(t3v);
//                TextView t4v = new TextView(this);
//                t4v.setText("indicator" + i);
//                t4v.setTextColor(Color.BLACK);
//                t4v.setGravity(Gravity.CENTER);
//                tbrow.addView(t4v);
//                stk.addView(tbrow);
//            }
//        }
//        else{
//            int count = 0;
//            //indicator header for member user
//            TableRow tbrow0 = new TableRow(this);
//            TextView tv0 = new TextView(this);
//            tv0.setText("   ");
//            tv0.setTextColor(Color.BLACK);
//            tbrow0.addView(tv0);
//            if(count<indicators.size()){
//                TextView t1v = new TextView(this);
//                t1v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
//                t1v.setTextColor(Color.BLACK);
//                t1v.setGravity(Gravity.CENTER);
//                tbrow0.addView(t1v);
//                count++;
//            }
//            if(count<indicators.size()){
//                TextView t2v = new TextView(this);
//                t2v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
//                t2v.setTextColor(Color.BLACK);
//                t2v.setGravity(Gravity.CENTER);
//                tbrow0.addView(t2v);
//                count++;
//            }
//            if(count<indicators.size()){
//                TextView t3v = new TextView(this);
//                t3v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
//                t3v.setTextColor(Color.BLACK);
//                t3v.setGravity(Gravity.CENTER);
//                tbrow0.addView(t3v);
//                count++;
//            }
//            if(count<indicators.size()){
//                TextView t4v = new TextView(this);
//                t4v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
//                t4v.setTextColor(Color.BLACK);
//                t4v.setGravity(Gravity.CENTER);
//                tbrow0.addView(t4v);
//                count++;
//            }
//            if(count<indicators.size()){
//                TextView t5v = new TextView(this);
//                t5v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
//                t5v.setTextColor(Color.BLACK);
//                t5v.setGravity(Gravity.CENTER);
//                tbrow0.addView(t5v);
//            }
//            stk.addView(tbrow0);
//
//            //dynamic rows
//            for (int i = 0; i < states.size(); i++) {
//                int count2 = 0;
//                TableRow tbrow = new TableRow(this);
//                TextView t0v = new TextView(this);
//                t0v.setText(((Button)v1.findViewById(states.get(i))).getText());
//                t0v.setTextColor(Color.BLACK);
//                t0v.setGravity(Gravity.CENTER);
//                tbrow.addView(t0v);
//                if(count2<indicators.size()){
//                    TextView t1v = new TextView(this);
//                    t1v.setText("indi " + i);
//                    t1v.setTextColor(Color.BLACK);
//                    t1v.setGravity(Gravity.CENTER);
//                    tbrow.addView(t1v);
//                    count2++;
//                }
//                if(count2<indicators.size()){
//                    TextView t2v = new TextView(this);
//                    t2v.setText("indi " + i);
//                    t2v.setTextColor(Color.BLACK);
//                    t2v.setGravity(Gravity.CENTER);
//                    tbrow.addView(t2v);
//                    count2++;
//                }
//                if(count2<indicators.size()){
//                    TextView t3v = new TextView(this);
//                    t3v.setText("indi " + i);
//                    t3v.setTextColor(Color.BLACK);
//                    t3v.setGravity(Gravity.CENTER);
//                    tbrow.addView(t3v);
//                    count2++;
//                }
//                if(count2<indicators.size()){
//                    TextView t4v = new TextView(this);
//                    t4v.setText("indi " + i);
//                    t4v.setTextColor(Color.BLACK);
//                    t4v.setGravity(Gravity.CENTER);
//                    tbrow.addView(t4v);
//                    count2++;
//                }
//                if(count2<indicators.size()){
//                    TextView t5v = new TextView(this);
//                    t5v.setText("indi " + i);
//                    t5v.setTextColor(Color.BLACK);
//                    t5v.setGravity(Gravity.CENTER);
//                    tbrow.addView(t5v);
//                }
//                stk.addView(tbrow);
//            }
//
//        }
//    }
}
