package com.coms309.gk_4_projname.workout_schedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView incorrect = (TextView) findViewById(R.id.wrongText);
        incorrect.setText("");

        Button login = (Button) findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText password = (EditText) findViewById(R.id.passwordText);
                int pwd = Integer.parseInt(password.getText().toString());
                int result = 1989;
                if(pwd == result){
                    Intent intentOne = new Intent(getApplicationContext(), WeekActivity.class);
                    incorrect.setText("");
                    startActivity(intentOne);
                }
                else{
                    incorrect.setText("Password Incorrect");
                }
            }
        });
    }
}
