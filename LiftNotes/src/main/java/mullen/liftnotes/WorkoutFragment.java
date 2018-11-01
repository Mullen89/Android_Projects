package mullen.liftnotes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutFragment extends Fragment {

    Button addExercise;
    Button back;
    ArrayList<String> exerciseList = new ArrayList<String>();
    ArrayAdapter<String> exerciseListAdapter;
    ListView listViewer;

    public WorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1_exercise_screen, container, false);
        if (container != null) {
            container.removeAllViews();
        }
        exerciseListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.exercise_item_layout, exerciseList);
        listViewer = (ListView) view.findViewById(R.id.exerciseListView);

        addExercise = (Button) view.findViewById(R.id.addExerciseBtn);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Test exercise click", Toast.LENGTH_SHORT).show();

            }
        });

        back = (Button) view.findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabFragment1 tf = new TabFragment1();
                android.support.v4.app.FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.exerciseFrameLayout, tf, tf.getTag())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

}
