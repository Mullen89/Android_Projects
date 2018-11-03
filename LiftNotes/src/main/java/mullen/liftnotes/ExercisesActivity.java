package mullen.liftnotes;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ExercisesActivity extends AppCompatActivity {

    Button addExercise;
    Button back;
    ArrayList<String> exerciseList = new ArrayList<String>();
    ArrayAdapter<String> exerciseListAdapter;
    ListView listViewer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment1_exercise_screen);
//
//        listViewer = (ListView) findViewById(R.id.exerciseListView);
//        final ArrayList<ExerciseObjects> exercises = new ArrayList<ExerciseObjects>();
//        final ExerciseObjectsAdapter adapter = new ExerciseObjectsAdapter(this, exercises);
//        listViewer.setAdapter(adapter);
//
//        addExercise = (Button) findViewById(R.id.addExerciseBtn);
//        addExercise.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(getActivity(), "Test exercise click", Toast.LENGTH_SHORT).show();
//                ExerciseObjects blank = new ExerciseObjects("", "", "", "");
//                exercises.add(blank);
//                adapter.notifyDataSetChanged();
//
//            }
//        });

    }
}
