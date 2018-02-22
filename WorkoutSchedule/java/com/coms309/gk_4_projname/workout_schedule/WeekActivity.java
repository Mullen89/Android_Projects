package com.coms309.gk_4_projname.workout_schedule;

import android.content.Intent;
import android.net.sip.SipSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WeekActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        TextView day1 = (TextView) findViewById(R.id.Day1);
        TextView day2 = (TextView) findViewById(R.id.Day2);
        TextView day3 = (TextView) findViewById(R.id.Day3);
//        TextView day4 = (TextView) findViewById(R.id.Day4);
//        TextView day5 = (TextView) findViewById(R.id.Day5);
//        TextView day6 = (TextView) findViewById(R.id.Day6);
//        TextView day7 = (TextView) findViewById(R.id.Day7);

        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DayActivity.class);
                intent.putExtra("DAY1", "day1");
                startActivity(intent);
            }
        });

        day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DayActivity.class);
                intent.putExtra("DAY2", "day2");
                startActivity(intent);
            }
        });

        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DayActivity.class);
                intent.putExtra("DAY3", "day3");
                startActivity(intent);
            }
        });

//        day4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        day5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        day6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        day7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
}
