package mullen.liftnotes;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.ColorRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class ExercisesActivity extends AppCompatActivity {

    Button addExercise;
    ImageButton back;
    private ListView listViewer;
    ArrayList<ExerciseObjects> exercises = new ArrayList<ExerciseObjects>();
    private String key = "arg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment1_exercise_screen);
        Bundle extra = getIntent().getExtras();
        final String extraString = extra.getString(key);

        if(loadList(extraString) != null){
            exercises = loadList(extraString);
        }

        final ExerciseObjectsAdapter adapter = new ExerciseObjectsAdapter(this, exercises);
        listViewer = (ListView) findViewById(R.id.exerciseListView);
        listViewer.setAdapter(adapter);

        addExercise = (Button) findViewById(R.id.addExerciseBtn);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExerciseToList(adapter, extraString);
            }
        });

        back = (ImageButton) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton();
            }
        });

        listViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.v("TAG", "CLICKED row number: " + arg2);

                editExerciseListItem(arg2, adapter, extraString);
            }
        });

        listViewer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                           long arg3) {
                // TODO Auto-generated method stub
                Log.v("TAG", "CLICKED row number: " + arg2);

                //Toast.makeText(getActivity(), "Test button LOOOOOng click", Toast.LENGTH_SHORT).show();
                //delete(arg2, adapter, extraString);
                //editExerciseListItem(arg2, adapter, extraString);
                editOrDelete(arg2, adapter, extraString);
                return true;
            }
        });
    }

    private void saveList(ArrayList<ExerciseObjects> list, String key) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    private ArrayList<ExerciseObjects> loadList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<ExerciseObjects>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void backButton(){
        Intent intentBack = new Intent(this, MainActivity.class);
        intentBack.putExtra("frag", 1);

        startActivity(intentBack);
    }

    private void delete(int args, ExerciseObjectsAdapter adapter, String listKey) {
        final int tempArg = args;
        final String tempStr = listKey;
        final ExerciseObjectsAdapter tempAdp = adapter;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you wish to delete this item?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exercises.remove(tempArg);
                saveList(exercises, tempStr);
                tempAdp.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //Forces keyboard to pop-up whenever "Add Workout" button is clicked.
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //Shows the dialog pop-up
        dialog.show();
    }

    private void addExerciseToList(ExerciseObjectsAdapter adapter, String exKey) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final ExerciseObjectsAdapter tempAdp = adapter;
        final String key = exKey;
        final EditText exEditText = new EditText(this);
        final EditText setsEditText = new EditText(this);
        final EditText repsEditText = new EditText(this);
        final EditText wgtEditText = new EditText(this);
        layout.addView(exEditText);
        layout.addView(setsEditText);
        layout.addView(repsEditText);
        layout.addView(wgtEditText);
        exEditText.setHint("Exercise Title");
        setsEditText.setHint("Sets");
        repsEditText.setHint("Reps");
        wgtEditText.setHint("Weight/Resistance");
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Add Exercise Details")
                .setView(layout); //<-- add layout

        dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exEditText.setMaxLines(1);
                exEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                setsEditText.setMaxLines(1);
                setsEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                repsEditText.setMaxLines(1);
                repsEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                wgtEditText.setMaxLines(1);
                wgtEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                String ex = exEditText.getText().toString();
                String sets = setsEditText.getText().toString();
                String reps = repsEditText.getText().toString();
                String wgt = wgtEditText.getText().toString();
                if(ex.equals(null)){
                    ex = "";
                }
                if(sets.equals(null)){
                    sets = "";
                }
                if(reps.equals(null)){
                    reps = "";
                }
                if(wgt.equals(null)){
                    wgt = "";
                }
                ExerciseObjects blank = new ExerciseObjects(ex, sets, reps, wgt);
                exercises.add(blank);
                saveList(exercises, key);
                tempAdp.notifyDataSetChanged();
            }
        });
       dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog builder = dialog.create();
        builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //Shows the dialog pop-up
        builder.show();
    }

    private void editOrDelete(int position, ExerciseObjectsAdapter adapter, String exKey){
        final String key = exKey;
        final ExerciseObjectsAdapter tempAdp = adapter;
        final int pos = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("OPTIONS");

        builder.setNeutralButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editExerciseListItem(pos, tempAdp, key);
            }
        });

        builder.setPositiveButton("CANCEl", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(pos, tempAdp, key);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void editExerciseListItem(int position, ExerciseObjectsAdapter adapter, String exKey){
        ExerciseObjects list = exercises.get(position);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final int pos = position;
        final ExerciseObjectsAdapter tempAdp = adapter;
        final String key = exKey;
        final EditText exEditText = new EditText(this);
        final EditText setsEditText = new EditText(this);
        final EditText repsEditText = new EditText(this);
        final EditText wgtEditText = new EditText(this);
        layout.addView(exEditText);
        layout.addView(setsEditText);
        layout.addView(repsEditText);
        layout.addView(wgtEditText);
        exEditText.setHint("Exercise Title");
        setsEditText.setHint("Sets");
        repsEditText.setHint("Reps");
        wgtEditText.setHint("Weight/Resistance");
        exEditText.setText(list.getEx1());
        setsEditText.setText(list.getEx2());
        repsEditText.setText(list.getEx3());
        wgtEditText.setText(list.getEx4());
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Add Exercise Details")
                .setView(layout); //<-- add layout

        dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exEditText.setMaxLines(1);
                exEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                setsEditText.setMaxLines(1);
                setsEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                repsEditText.setMaxLines(1);
                repsEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                wgtEditText.setMaxLines(1);
                wgtEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                String ex = exEditText.getText().toString();
                String sets = setsEditText.getText().toString();
                String reps = repsEditText.getText().toString();
                String wgt = wgtEditText.getText().toString();
                if(ex.equals(null)){
                    ex = "";
                }
                if(sets.equals(null)){
                    sets = "";
                }
                if(reps.equals(null)){
                    reps = "";
                }
                if(wgt.equals(null)){
                    wgt = "";
                }
                ExerciseObjects blank = new ExerciseObjects(ex, sets, reps, wgt);
                exercises.set(pos, blank);
                saveList(exercises, key);
                tempAdp.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog builder = dialog.create();
        builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //Shows the dialog pop-up
        builder.show();
    }
}
