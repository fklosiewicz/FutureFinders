package com.example.pff;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
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
        activeUser = getIntent().getExtras().containsKey("activeUser") ? (User) getIntent().getExtras().getSerializable("activeUser") : null;

        init();

        /*LayoutInflater li = getLayoutInflater();
        View v1 = li.inflate(R.layout.activity_main, null);
        View v2 = li.inflate(R.layout.indicators, null);

        ((TextView)findViewById(R.id.textView6)).setText(((Button)v1.findViewById(states.get(0))).getText());
        ((TextView)findViewById(R.id.textView2)).setText(((CheckBox)v2.findViewById(indicators.get(0))).getText());
        */
    }

    public void init() {

        LayoutInflater li = getLayoutInflater();
        View v1 = li.inflate(R.layout.activity_main, null);
        View v2 = li.inflate(R.layout.indicators, null);

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        if(activeUser==null){
            //indicator header for guest
            TableRow tbrow0 = new TableRow(this);
            TextView tv0 = new TextView(this);
            tv0.setText("   ");
            tv0.setTextColor(Color.BLACK);
            tbrow0.addView(tv0);
            TextView tv1 = new TextView(this);
            tv1.setText(" Salary ");
            tv1.setTextColor(Color.BLACK);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(this);
            tv2.setText(" Tax ");
            tv2.setTextColor(Color.BLACK);
            tbrow0.addView(tv2);
            TextView tv3 = new TextView(this);
            tv3.setText(" Happiness ");
            tv3.setTextColor(Color.BLACK);
            tbrow0.addView(tv3);
            stk.addView(tbrow0);
            //dynamic rows
            for (int i = 0; i < states.size(); i++) {
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText(((Button)v1.findViewById(states.get(i))).getText());
                t1v.setTextColor(Color.BLACK);
                t1v.setGravity(Gravity.CENTER);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText("indicator " + i);
                t2v.setTextColor(Color.BLACK);
                t2v.setGravity(Gravity.CENTER);
                tbrow.addView(t2v);
                TextView t3v = new TextView(this);
                t3v.setText("indicator" + i);
                t3v.setTextColor(Color.BLACK);
                t3v.setGravity(Gravity.CENTER);
                tbrow.addView(t3v);
                TextView t4v = new TextView(this);
                t4v.setText("indicator" + i);
                t4v.setTextColor(Color.BLACK);
                t4v.setGravity(Gravity.CENTER);
                tbrow.addView(t4v);
                stk.addView(tbrow);
            }
        }
        else{
            int count = 0;
            //indicator header for member user
            TableRow tbrow0 = new TableRow(this);
            TextView tv0 = new TextView(this);
            tv0.setText("   ");
            tv0.setTextColor(Color.BLACK);
            tbrow0.addView(tv0);
            if(count<indicators.size()){
                TextView t1v = new TextView(this);
                t1v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
                t1v.setTextColor(Color.BLACK);
                t1v.setGravity(Gravity.CENTER);
                tbrow0.addView(t1v);
                count++;
            }
            if(count<indicators.size()){
                TextView t2v = new TextView(this);
                t2v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
                t2v.setTextColor(Color.BLACK);
                t2v.setGravity(Gravity.CENTER);
                tbrow0.addView(t2v);
                count++;
            }
            if(count<indicators.size()){
                TextView t3v = new TextView(this);
                t3v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
                t3v.setTextColor(Color.BLACK);
                t3v.setGravity(Gravity.CENTER);
                tbrow0.addView(t3v);
                count++;
            }
            if(count<indicators.size()){
                TextView t4v = new TextView(this);
                t4v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
                t4v.setTextColor(Color.BLACK);
                t4v.setGravity(Gravity.CENTER);
                tbrow0.addView(t4v);
                count++;
            }
            if(count<indicators.size()){
                TextView t5v = new TextView(this);
                t5v.setText(((Button)v2.findViewById(indicators.get(count))).getText());
                t5v.setTextColor(Color.BLACK);
                t5v.setGravity(Gravity.CENTER);
                tbrow0.addView(t5v);
            }
            stk.addView(tbrow0);

            //dynamic rows
            for (int i = 0; i < states.size(); i++) {
                int count2 = 0;
                TableRow tbrow = new TableRow(this);
                TextView t0v = new TextView(this);
                t0v.setText(((Button)v1.findViewById(states.get(i))).getText());
                t0v.setTextColor(Color.BLACK);
                t0v.setGravity(Gravity.CENTER);
                tbrow.addView(t0v);
                if(count2<indicators.size()){
                    TextView t1v = new TextView(this);
                    t1v.setText("indi " + i);
                    t1v.setTextColor(Color.BLACK);
                    t1v.setGravity(Gravity.CENTER);
                    tbrow.addView(t1v);
                    count2++;
                }
                if(count2<indicators.size()){
                    TextView t2v = new TextView(this);
                    t2v.setText("indi " + i);
                    t2v.setTextColor(Color.BLACK);
                    t2v.setGravity(Gravity.CENTER);
                    tbrow.addView(t2v);
                    count2++;
                }
                if(count2<indicators.size()){
                    TextView t3v = new TextView(this);
                    t3v.setText("indi " + i);
                    t3v.setTextColor(Color.BLACK);
                    t3v.setGravity(Gravity.CENTER);
                    tbrow.addView(t3v);
                    count2++;
                }
                if(count2<indicators.size()){
                    TextView t4v = new TextView(this);
                    t4v.setText("indi " + i);
                    t4v.setTextColor(Color.BLACK);
                    t4v.setGravity(Gravity.CENTER);
                    tbrow.addView(t4v);
                    count2++;
                }
                if(count2<indicators.size()){
                    TextView t5v = new TextView(this);
                    t5v.setText("indi " + i);
                    t5v.setTextColor(Color.BLACK);
                    t5v.setGravity(Gravity.CENTER);
                    tbrow.addView(t5v);
                }
                stk.addView(tbrow);
            }

        }
    }

    /*public void back(View view) {
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("States", states);
        bundle.putIntegerArrayList("Indicators", indicators);
        if(activeUser != null){
            bundle.putSerializable("activeUser", activeUser);
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }*/
}
