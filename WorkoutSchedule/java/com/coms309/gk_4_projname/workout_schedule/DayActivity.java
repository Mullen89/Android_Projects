package com.coms309.gk_4_projname.workout_schedule;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class DayActivity extends AppCompatActivity {

    ListView listView;
    String[] listWorkout;
    String[] listSets;
    String[] listReps;
    String[] listWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        listView = (ListView) findViewById(R.id.list_View);

        if(getIntent().hasExtra("DAY1")){
            Resources res = getResources();
            listWorkout = res.getStringArray((R.array.day1_Workouts));
            listSets = res.getStringArray(R.array.day1_Sets);
            listReps = res.getStringArray(R.array.day1_Reps);
            listWeight = res.getStringArray(R.array.day1_Weight);
        }
        else if(getIntent().hasExtra("DAY2")){
            Resources res = getResources();
            listWorkout = res.getStringArray((R.array.day2_Workouts));
            listSets = res.getStringArray(R.array.day2_Sets);
            listReps = res.getStringArray(R.array.day2_Reps);
            listWeight = res.getStringArray(R.array.day2_Weight);
        }
        else if(getIntent().hasExtra("DAY3")){
            Resources res = getResources();
            listWorkout = res.getStringArray((R.array.day3_Workouts));
            listSets = res.getStringArray(R.array.day3_Sets);
            listReps = res.getStringArray(R.array.day3_Reps);
            listWeight = res.getStringArray(R.array.day3_Weight);
        }

        ItemAdapter itemAdapter = new ItemAdapter(this, listWorkout, listSets, listReps, listWeight);
        listView.setAdapter(itemAdapter);
    }
}
