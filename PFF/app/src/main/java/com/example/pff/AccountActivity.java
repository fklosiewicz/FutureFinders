package com.example.pff;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pff.design.User;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AccountActivity<color> extends AppCompatActivity {

    public User activeUser;
    public ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        Intent intent = getIntent();
        activeUser = (User)intent.getExtras().getSerializable("User");
        users = (ArrayList<User>)intent.getExtras().getSerializable("Users");
    }

    public void update(View view) {
        String p = this.getApplicationInfo().dataDir + "/appdata.dat";
        EditText pwd = findViewById(R.id.textPassword2);
        String password = pwd.getText().toString();
        if(new String("HERE").equals("HERE")) {
            if (password.equals("")) {
                AlertDialog.Builder noPassword = new AlertDialog.Builder(this);
                noPassword.setMessage("Must input password").setPositiveButton("Okay", null);
                noPassword.show();
                return;
            } else if (password.trim().equals("")) {
                AlertDialog.Builder noPassword = new AlertDialog.Builder(this);
                noPassword.setMessage("Cannot have a password with only space characters.").setPositiveButton("Okay", null);
                noPassword.show();
                return;
            } else if (password.contains(" ")) {
                AlertDialog.Builder noPassword = new AlertDialog.Builder(this);
                noPassword.setMessage("Cannot have a password that contains space characters.").setPositiveButton("Okay", null);
                noPassword.show();
                return;
            } else {
                for(int x = 0; x < users.size(); x++) {
                    if(users.get(x).username.equals(activeUser.username)) {
                        users.remove(x);
                        break;
                    }
                }
                activeUser.password = password;
                users.add(activeUser);
                try {
                    FileOutputStream fos = new FileOutputStream(p);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(users);
                    oos.close();
                    fos.close();
                    Toast.makeText(this, "Password successfully updated!", Toast.LENGTH_LONG).show();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("User", activeUser);
        bundle.putSerializable("Users", users);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void results(View v) {
        int x = -1;
        if(v.getId() == R.id.option1) {
            x = 0;
        }
        else if(v.getId() == R.id.option2) {
            x = 1;
        }
        else if(v.getId() == R.id.option3) {
            x = 2;
        }
        else if(v.getId() == R.id.option4) {
            x = 3;
        }
        else {
            x = 4;
        }

        if(activeUser.indicators.size() <= x) {
            AlertDialog.Builder noStates = new AlertDialog.Builder(v.getContext());
            noStates.setMessage("Please select a valid prior search").setPositiveButton("Okay", null);
            noStates.show();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("States", activeUser.states.get(x));
        bundle.putIntegerArrayList("Indicators", activeUser.indicators.get(x));
        bundle.putSerializable("activeUser", activeUser);
        bundle.putString("priorPage", "accountActivity");
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Note: changes not saved", Toast.LENGTH_LONG).show();
    }
}