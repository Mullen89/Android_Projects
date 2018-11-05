package mullen.liftnotes;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class ExercisesActivity extends AppCompatActivity {

    Button addExercise;
    Button back;
    ArrayList<String> exerciseList = new ArrayList<String>();
    ArrayAdapter<String> exerciseListAdapter;
    ListView listViewer;
    String extraString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment1_exercise_screen);
        Bundle extra = getIntent().getExtras();
        extraString = extra.getString("arg");


        listViewer = (ListView) findViewById(R.id.exerciseListView);
        final ArrayList<ExerciseObjects> exercises = new ArrayList<ExerciseObjects>();
        final ExerciseObjectsAdapter adapter = new ExerciseObjectsAdapter(this, exercises);
        listViewer.setAdapter(adapter);

        addExercise = (Button) findViewById(R.id.addExerciseBtn);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), extraString, Toast.LENGTH_LONG).show();
                ExerciseObjects blank = new ExerciseObjects("", "", "", "");
                exercises.add(blank);
                adapter.notifyDataSetChanged();

            }
        });

    }
    private void save(String args) {

    }

    private void load(String args) {

    }
}
