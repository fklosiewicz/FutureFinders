package com.example.pff;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.pff.design.User;

public class ExplanationActivity<color> extends Activity {

    public User activeUser;//so that the user can remain logged in when going back
    public int question;
    private TextView explanation_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activeUser = getIntent().getExtras().containsKey("activeUser") ? (User) getIntent().getExtras().getSerializable("activeUser") : null;
        question = getIntent().getExtras().getInt("question");

        setContentView(R.layout.explanation);
        explanation_text = (TextView) findViewById(R.id.explanation_text);

        switch (question){
            case 1:
                explanation_text.setText(R.string.explanation1);
                break;
            case 2:
                explanation_text.setText(R.string.explanation2);
                break;
            case 3:
                explanation_text.setText(R.string.explanation3);
                break;
            case 4:
                explanation_text.setText(R.string.explanation4);
                break;
            case 5:
                explanation_text.setText(R.string.explanation5);
                break;
        }
//        setContentView(R.layout.explanation);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.7), (int)(height*0.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);
    }
}