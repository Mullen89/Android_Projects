package mullen.liftnotes;


        import android.app.AlertDialog;
        import android.app.ListFragment;
        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.RecyclerView;
        import android.text.InputType;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class TabFragment1 extends Fragment {

    Button addWorkout;
    String mText = "";
    ArrayList<String> workoutList = new ArrayList<String>();
    ArrayAdapter<String> workoutListAdapter;
    ListView listViewer;


    public TabFragment1() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment1, container, false);
        workoutListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.workout_item_layout, workoutList);
        listViewer = (ListView) view.findViewById(R.id.workoutListView);

        addWorkout = (Button) view.findViewById(R.id.addWorkoutBtn);
        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Test button click", Toast.LENGTH_SHORT).show();
                addWorkoutToList();
            }
        });

        listViewer.setAdapter(workoutListAdapter);
        return view;
    }

    //Alert Dialog Interface box pop-up to add workout to workoutList<>
    private void addWorkoutToList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Name of Workout");

        final EditText input = new EditText(this.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mText = input.getText().toString();
                workoutList.add(mText);
                workoutListAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
