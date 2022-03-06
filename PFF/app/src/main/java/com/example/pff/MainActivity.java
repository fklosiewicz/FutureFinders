package com.example.pff;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pff.design.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;


public class MainActivity<color> extends AppCompatActivity {

    Button Register;
    public ArrayList<User> users;//List of Users
    public ArrayList<String> states;//List of selected states
    public ArrayList<String> indicators;//List of selected indicators
    public User activeUser;//Logged in member
    public final int CAP_GUEST = 2;//State cap for guest
    public final int CAP_MEMBER = 3;//State cap for member


    private static Connection connection;
    public static final String url = "jdbc:mariadb://172.16.122.19:3306/future_finders";

    private static void openDatabaseConnection() throws SQLException {
        System.out.println("Connecting to the database...");
        connection = DriverManager.getConnection(url, "finder", "1234abcd");
        //System.out.println("Connection valid: " + connection.isValid(5));
    }

    private static void closeDatabaseConnection() throws SQLException {
        System.out.println("Closing database connection...");
        connection.close();
    }

    public void establishConnection(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    openDatabaseConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        activeUser = null;//Start main with no active user
        String p = this.getApplicationInfo().dataDir + "/appdata.dat";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(intent.hasExtra("States")) {
            states = intent.getExtras().getStringArrayList("States");
            indicators = intent.getExtras().getStringArrayList("Indicators");
        }
        else {
            states = new ArrayList<String>();
            indicators = new ArrayList<String>();
        }

        File data = new File(p);
        boolean de = true;
        if(!data.exists() || !data.isFile()) {
            de = false;
            try {
                data.createNewFile();
                users = new ArrayList<User>();
                User stock = new User("username", "password");
                users.add(stock);

                FileOutputStream fos = new FileOutputStream(p);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(users);
                fos.close();
                oos.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (de){
            try {
                FileInputStream fis = new FileInputStream(p);
                ObjectInputStream ois = new ObjectInputStream(fis);
                users = (ArrayList<User>) ois.readObject();
                fis.close();
                ois.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void register(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Users", users);
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // for later: add cap to number of states that can be added to list based on if logged in or not
    public void selectState(View view) {
        Button b = (Button)view;
        if(activeUser==null){//If no User is logged in
            if(!states.contains(b.getText())) {
                if(!(states.size()<CAP_GUEST)){//If the cap is reached, prompt user to register
                    AlertDialog.Builder cap_reached = new AlertDialog.Builder(this);
                    cap_reached.setMessage("Maximum " + CAP_GUEST + " states for guest.\nTo " +
                            "select up to "+ CAP_MEMBER + " states, " +
                            "please register.").setPositiveButton("Okay", null);
                    cap_reached.show();
                    return;
                }
                states.add((String)b.getText());
                //Change background color to indicate selected
                b.setBackgroundColor(Color.parseColor("#800080"));
            }
            else {
                states.remove(b.getText());
                //Change background color back to default to indicate deselected
                b.setBackgroundColor(Color.parseColor("#FF6200EE"));
            }
        }else{//If User is logged in
            if(!states.contains(b.getText())) {
                if(!(states.size()<CAP_MEMBER)){//If the cap is reached, extort
                    AlertDialog.Builder cap_reached = new AlertDialog.Builder(this);
                    cap_reached.setMessage("Maximum " + CAP_MEMBER + " states for members.\nTo " +
                            "select more states, send 2 BTC to 0xFUTURE_FINDERS, " +
                            "and delete app.").setPositiveButton("Okay", null);
                    cap_reached.show();
                    return;
                }
                states.add((String)b.getText());
                //Change background color to indicate selected
                b.setBackgroundColor(Color.parseColor("#800080"));
            }
            else {
                states.remove(b.getText());
                //Change background color back to default to indicate deselected
                b.setBackgroundColor(Color.parseColor("#FF6200EE"));
            }
        }
    }

    public void indicators(View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("States", states);
        bundle.putStringArrayList("Indicators", indicators);
        Intent intent = new Intent(this, IndicatorActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void logout(View view) {
        if(activeUser != null) {
            Toast.makeText(this, "User: " + activeUser.username + " successfully logged out!", Toast.LENGTH_LONG).show();
            activeUser = null;
            return;
        }
        else if(activeUser == null) {
            Toast.makeText(this, "Not currently logged in.", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void login(View view) {
        String p = this.getApplicationInfo().dataDir + "/appdata.dat";
        try {
            FileInputStream fis = new FileInputStream(p);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (ArrayList<User>) ois.readObject();
            fis.close();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter your username and password:");

        final EditText login = new EditText(this);
        final EditText pass = new EditText(this);

        login.setInputType(InputType.TYPE_CLASS_TEXT);
        pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(login);
        lay.addView(pass);
        builder.setView(lay);
        final boolean[] success = {false};

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                for (User t : users) {
                    if(login.getText().toString().equals(t.username) && pass.getText().toString().equals(t.password)) {
                            AlertDialog.Builder no_delete = new AlertDialog.Builder(view.getContext());
                            no_delete.setMessage("Successful Login!").setPositiveButton("Okay", null);
                            no_delete.show();
                            activeUser = t;//Capture the logged in user
                            success[0] = true;
                            return;
                    }
                }
                if(success[0] == false) {
                    AlertDialog.Builder no_delete = new AlertDialog.Builder(view.getContext());
                    no_delete.setMessage("This user does not exist.").setPositiveButton("Okay", null);
                    no_delete.show();
                    return;
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}