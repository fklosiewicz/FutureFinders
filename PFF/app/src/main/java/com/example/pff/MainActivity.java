package com.example.pff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.pff.design.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity<color> extends AppCompatActivity {

    Button Register;
    public ArrayList<User> users;
    public ArrayList<String> states;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String p = this.getApplicationInfo().dataDir + "/appdata.dat";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        else if (de == true){
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
        if(!states.contains(b.getText())) {
            states.add((String)b.getText());
        }
        else {
            states.remove(b.getText());
        }
    }

    public void indicators(View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("States", states);
        Intent intent = new Intent(this, IndicatorActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}