package com.example.pff;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pff.design.User;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 * This class is the registration activity class.
 *
 * The class is mainly responsible for registering a user on the application database.
 *
 * @author Filip Klosiewicz
 * @author Sofia Ozol
 * @author Max Pandolpho
 * @author Samantha Cheng
 * @author Zining Ou
 */

public class RegistrationActivity<color> extends AppCompatActivity {

    EditText textUsername;
    EditText textPassword;
    Button createUser;
    public ArrayList<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        textUsername = (EditText) findViewById(R.id.textUsername);
        textPassword = (EditText) findViewById(R.id.textPassword);
        Bundle bundle = getIntent().getExtras();
        users = (ArrayList<User>)bundle.getSerializable("Users");
    }

    /**
     * The create user function will take the inputs from the EditText fields and attempt to create a user.
     *
     * If a user exists, an alert will be thrown that such a user exits. If not, a success alert will display.
     *
     * @param view
     */
    public void createUser(View view) {
        String p = this.getApplicationInfo().dataDir + "/appdata.dat";
        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();
        User newUser = new User(username, password);
        if (username.equals("") || password.equals("")) {
            AlertDialog.Builder no_delete = new AlertDialog.Builder(this);
            no_delete.setMessage("Cannot create an empty user.").setPositiveButton("Okay", null);
            no_delete.show();
            return;
        }
        else if (username.trim().equals("") || password.trim().equals("")) {
            AlertDialog.Builder no_delete = new AlertDialog.Builder(this);
            no_delete.setMessage("Cannot create a user with only space characters.").setPositiveButton("Okay", null);
            no_delete.show();
            return;
        }
        else if(username.contains(" ") || password.contains(" ")) {
            AlertDialog.Builder no_delete = new AlertDialog.Builder(this);
            no_delete.setMessage("Cannot create a user that contains space characters.").setPositiveButton("Okay", null);
            no_delete.show();
            return;
        }
        else {
            for (User t : users) {
                if(newUser.username.equals(t.username)) {
                    AlertDialog.Builder no_delete = new AlertDialog.Builder(this);
                    no_delete.setMessage("Cannot add an existing username to the database.").setPositiveButton("Okay", null);
                    no_delete.show();
                    return;
                }
            }
        }
        users.add(newUser);
        try {
            FileOutputStream fos = new FileOutputStream(p);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
            Toast.makeText(this, "User successfully added!", Toast.LENGTH_LONG).show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}