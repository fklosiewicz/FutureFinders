package com.example.pff;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.pff.design.User;

/**
 * This class is the explanation activity controller class.
 *
 * As previously mentioned, the class allows a member user to get a better insight of what each of the indicators signify.
 * This feature is very helpful for newcomers to the application who might not know some of the indicators signify.
 * Along with the tips, the source of the information is also shown to the user.
 *
 *
 * @author Filip Klosiewicz
 * @author Sofia Ozol
 * @author Max Pandolpho
 * @author Samantha Cheng
 * @author Zining Ou
 */

public class ExplanationActivity<color> extends Activity {

    public User activeUser;//so that the user can remain logged in when going back
    public int question;
    private TextView explanation_text;
    private Button explanation_close;

    /**
     * The onCreate will prompt the display window from the previously selected question, which the
     * user will be able to see and scroll through in order to inform themselves on a certain indicator.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activeUser = getIntent().getExtras().containsKey("activeUser") ? (User) getIntent().getExtras().getSerializable("activeUser") : null;
        question = getIntent().getExtras().getInt("question");

        setContentView(R.layout.explanation);
        explanation_text = (TextView) findViewById(R.id.explanation_text);
        explanation_close = (Button) findViewById(R.id.explanation_close);

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

        explanation_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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