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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pff.design.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the results activity controller class.
 *
 * The class is responsible for populating the table with the data according to the previously
 * selected indicators and states on the landing page.

 *
 * The class does not have any other additional functionality other than behind-the-scenes work that
 * is accomplished with multi-threadding.
 *
 * @author Filip Klosiewicz
 * @author Sofia Ozol
 * @author Max Pandolpho
 * @author Samantha Cheng
 * @author Zining Ou
 */

public class ResultsActivity<color> extends AppCompatActivity {

    public ArrayList<Integer> states;
    public ArrayList<Integer> indicators;
    public User activeUser;

    private static final String URL = "jdbc:mysql://172.16.122.19:3306/future_finders";
    private static final String USER = "finder";
    private static final String PASS = "1234abcd";

    /**
     * This is the main asynchronous thread sub-class that connects to the MariaDB database with the state-indicator data and populates the table according to the selected indicators beforehand.
     *
     * This is the core of the entire results activity controller, as it does not have any additional
     * functions other than the back button.
     *
     * The thread takes a single String as a parameter (although it may take more), but the current development is designed to only
     * take a single String parameter.
     *
     * One parameter, namely string[0], corresponds to the selected state abbreviation (such as 'NJ', 'NY', 'RI') and
     * proceeds to execute the SQL query.
     */
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncTask extends AsyncTask<String, Void, Map<String, String>> {
        protected Map<String, String> doInBackground(String... strings) {
            Map<String, String> info = new HashMap<>();
            System.out.println("Connecting to the database...");
            try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
                System.out.println("Connection valid: " + connection.isValid(5));

                //String sql = "SELECT Wage, Happiness, StateTax FROM Indicators WHERE StateABBR = '" + strings[0] + "';";
                String sql = "SELECT * FROM Indicators WHERE StateABBR = '" + strings[0] + "';";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()) {
                    info.put("State", strings[0]);
                    info.put("Wage", resultSet.getString("Wage"));
                    info.put("StateTax", resultSet.getString("StateTax"));
                    info.put("Happiness", resultSet.getString("Happiness"));

                    info.put("SafetyRanking", resultSet.getString("SafetyRanking"));
                    info.put("Entertainment", resultSet.getString("Entertainment"));
                    info.put("Healthcare", resultSet.getString("Healthcare"));
                    info.put("AirQuality", resultSet.getString("AirQuality"));
                    info.put("Education", resultSet.getString("Education"));
                    info.put("CoLIndex", resultSet.getString("CoLIndex"));
                    info.put("AvgHouse", resultSet.getString("AvgHouse"));

                }
            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error reading information", e);
            }
            return info;
        }

        /**
         *  The post execute function is what takes place after the initial thread returns.
         *
         *  As mentioned above, this is where the behind the scenes work is performed, where multiple threads
         *  are started and each of them populate the table separately, yet completely, within a second.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (!result.isEmpty()) {
                //guest user results data
                if(activeUser==null) {
                    TextView State1 = findViewById(R.id.textView6);
                    TextView State2 = findViewById(R.id.textView11);
                    if (result.get("State").equals(State1.getText().toString())) {
                        TextView Indicator1 = findViewById(R.id.textView7);
                        Indicator1.setText(result.get("Wage"));
                        TextView Indicator2 = findViewById(R.id.textView8);
                        String tax = result.get("StateTax") + "%";
                        Indicator2.setText(tax);
                        TextView Indicator3 = findViewById(R.id.textView9);
                        Indicator3.setText(result.get("Happiness"));
                    }
                    if (result.get("State").equals(State2.getText().toString())) {
                        TextView Indicator1 = findViewById(R.id.textView12);
                        Indicator1.setText(result.get("Wage"));
                        TextView Indicator2 = findViewById(R.id.textView13);
                        String tax = result.get("StateTax") + "%";
                        Indicator2.setText(tax);
                        TextView Indicator3 = findViewById(R.id.textView14);
                        Indicator3.setText(result.get("Happiness"));
                    }
                }
                //member user results data
                else{
                    TextView State1 = findViewById(R.id.textView6);
                    TextView State2 = findViewById(R.id.textView11);
                    TextView State3 = findViewById(R.id.textView16);
                    String[] ind = getSelectedIndicators();

                    if (result.get("State").equals(State1.getText().toString())) {
                        TextView Indicator1 = findViewById(R.id.textView7);
                        TextView Indicator2 = findViewById(R.id.textView8);
                        TextView Indicator3 = findViewById(R.id.textView9);
                        TextView Indicator4 = findViewById(R.id.textView10);
                        TextView Indicator5 = findViewById(R.id.textView22);
                        switch (indicators.size()){
                            case 1:
                                Indicator1.setText(result.get(ind[0]));
                                break;
                            case 2:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                break;
                            case 3:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                Indicator3.setText(result.get(ind[2]));
                                break;
                            case 4:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                Indicator3.setText(result.get(ind[2]));
                                Indicator4.setText(result.get(ind[3]));
                                break;
                            case 5:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                Indicator3.setText(result.get(ind[2]));
                                Indicator4.setText(result.get(ind[3]));
                                Indicator5.setText(result.get(ind[4]));
                                break;
                        }
                    }
                    if (result.get("State").equals(State2.getText().toString())) {
                        TextView Indicator1 = findViewById(R.id.textView12);
                        TextView Indicator2 = findViewById(R.id.textView13);
                        TextView Indicator3 = findViewById(R.id.textView14);
                        TextView Indicator4 = findViewById(R.id.textView15);
                        TextView Indicator5 = findViewById(R.id.textView23);
                        switch (indicators.size()){
                            case 1:
                                Indicator1.setText(result.get(ind[0]));
                                break;
                            case 2:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                break;
                            case 3:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                Indicator3.setText(result.get(ind[2]));
                                break;
                            case 4:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                Indicator3.setText(result.get(ind[2]));
                                Indicator4.setText(result.get(ind[3]));
                                break;
                            case 5:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                Indicator3.setText(result.get(ind[2]));
                                Indicator4.setText(result.get(ind[3]));
                                Indicator5.setText(result.get(ind[4]));
                                break;
                        }
                    }
                    if (result.get("State").equals(State3.getText().toString())) {
                        TextView Indicator1 = findViewById(R.id.textView17);
                        TextView Indicator2 = findViewById(R.id.textView18);
                        TextView Indicator3 = findViewById(R.id.textView19);
                        TextView Indicator4 = findViewById(R.id.textView20);
                        TextView Indicator5 = findViewById(R.id.textView24);
                        switch (indicators.size()){
                            case 1:
                                Indicator1.setText(result.get(ind[0]));
                                break;
                            case 2:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                break;
                            case 3:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                Indicator3.setText(result.get(ind[2]));
                                break;
                            case 4:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                Indicator3.setText(result.get(ind[2]));
                                Indicator4.setText(result.get(ind[3]));
                                break;
                            case 5:
                                Indicator1.setText(result.get(ind[0]));
                                Indicator2.setText(result.get(ind[1]));
                                Indicator3.setText(result.get(ind[2]));
                                Indicator4.setText(result.get(ind[3]));
                                Indicator5.setText(result.get(ind[4]));
                                break;
                        }
                    }
                }
            }
        }
    }


    /**
     * The onCreate function takes the special role in ensuring that the results table is dynamic.
     *
     * Depending on the number of states selected, the onCreate will determine this number, and change
     * the size of the table view accordingly. The size can range from a 1x1 (1 state, 1 indicator) to a 3x5 (3 states, 5 indicators).
     *
     * @param savedInstanceState
     */
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

        // saving results
        if(activeUser != null && !getIntent().getExtras().containsKey("priorPage") && activeUser.indicators.size() < 5) {
            activeUser.indicators.add(indicators);
            activeUser.states.add(states);
            String p = this.getApplicationInfo().dataDir + "/appdata.dat";
            ArrayList<User> users = new ArrayList<User>();
            try {
                FileInputStream fis = new FileInputStream(p);
                ObjectInputStream ois = new ObjectInputStream(fis);
                users = (ArrayList<User>) ois.readObject();
                fis.close();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            for(int x = 0; x < users.size(); x++) {
                if(users.get(x).username.equals(activeUser.username)) {
                    users.remove(x);
                    break;
                }
            }
            users.add(activeUser);
            try {
                FileOutputStream fos = new FileOutputStream(this.getApplicationInfo().dataDir + "/appdata.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(users);
                oos.close();
                fos.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    /**
     * The shortIndicatorName converts the longer button names to a shorter, more concise name, as to properly fit the table.
     *
     * @param buttonName
     * @return
     */
    public String shortIndicatorName(String buttonName){
        if(buttonName.equals("Annual Median Wage"))
            return "Salary";
        else if(buttonName.equals("Happiness Ranking")){
            return "Happiness";
        }
        else if(buttonName.equals("State Income Tax Rate")){
            return "Tax";
        }
        else if(buttonName.equals("Safety Ranking")){
            return "Safety";
        }
        else if(buttonName.equals("Entertainment, Recreation, and Nightlife Ranking")){
            return
                    "Fun";
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
        else if(buttonName.equals("Average House Cost")){
            return "Avg House Cost";
        }
        //Cost of Living Ranking
        return "Living Rank";

    }

    /**
     * The behind-the-scene function that takes a String parameter of a button name and converts it
     * to a corresponding SQL id.
     *
     * @param buttonName
     * @return
     */
    ///converts button names into SQL indicator names
    public String getIndicatorSQLID(String buttonName){
        if(buttonName.equals("Annual Median Wage"))
            return "Wage";
        else if(buttonName.equals("Happiness Ranking")){
            return "Happiness";
        }
        else if(buttonName.equals("State Income Tax Rate")){
            return "StateTax";
        }
        else if(buttonName.equals("Safety Ranking")){
            return "SafetyRanking";
        }
        else if(buttonName.equals("Entertainment, Recreation, and Nightlife Ranking")){
            return "Entertainment";
        }
        else if(buttonName.equals("Healthcare Ranking")){
            return "Healthcare";
        }
        else if(buttonName.equals("Air and Water Quality")){
            return "AirQuality";
        }
        else if(buttonName.equals("Education Ranking")){
            return "Education";
        }
        else if(buttonName.equals("Cost of Living Index")){
            return "CoLIndex";
        }
        else if(buttonName.equals("Average House Cost")){
            return "AvgHouse";
        }
        //Cost of Living Ranking
        return "CoLRanking";
    }

    /**
     * The getSelectedIndicators function is such that the button name gets passed into getIndicatorSQLID
     * and SQL indicator name is returned from method call and saved into c1,c2,c3,c4,c5
     *
     * SQL indicator names are saved in 'String[] s' and returned through this method.
     *
     * @return
     */

    public String[] getSelectedIndicators(){
        String[] s = new String[5];
        String c1,c2, c3, c4, c5;
        LayoutInflater li = getLayoutInflater();
        View v2 = li.inflate(R.layout.indicators, null);
        switch(indicators.size()){
            case 1:
                c1 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(0))).getText());
                s[0]=c1;
                break;
            case 2:
                c1 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(0))).getText());
                s[0]=c1;
                c2 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(1))).getText());
                s[1]=c2;
                break;
            case 3:
                c1 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(0))).getText());
                s[0]=c1;
                c2 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(1))).getText());
                s[1]=c2;
                c3 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(2))).getText());
                s[2]=c3;
                break;
            case 4:
                c1 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(0))).getText());
                s[0]=c1;
                c2 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(1))).getText());
                s[1]=c2;
                c3 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(2))).getText());
                s[2]=c3;
                c4 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(3))).getText());
                s[3]=c4;
                break;
            case 5:
                c1 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(0))).getText());
                s[0]=c1;
                c2 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(1))).getText());
                s[1]=c2;
                c3 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(2))).getText());
                s[2]=c3;
                c4 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(3))).getText());
                s[3]=c4;
                c5 = getIndicatorSQLID((String) ((CheckBox) v2.findViewById(indicators.get(4))).getText());
                s[4]=c5;
                break;
        }
        return s;
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
        if(activeUser!=null){
            Intent intent = new Intent(this, IndicatorActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

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
