package mullen.liftnotes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Scanner;

import static android.app.PendingIntent.getActivity;
/**
 * This class handles all of the functions of the diet exercise activity screen.
 */
public class ExercisesActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 1;
    Button addExercise;
    ImageButton back;
    Button csv;
    private ListView listViewer;
    ExerciseObjectsAdapter adapter;
    ArrayList<ExerciseObjects> exercises = new ArrayList<ExerciseObjects>();
    private String key = "arg";
    public String csvText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Bundle extra = getIntent().getExtras();
        final String extraString = extra.getString(key);

        /**
         * Checks to see if there is already a list that was saved. If so, it
         * loads that list.
         */
        if(loadList(extraString) != null){
            exercises = loadList(extraString);
        }

        adapter = new ExerciseObjectsAdapter(this, exercises);
        listViewer = (ListView) findViewById(R.id.exerciseListView);
        listViewer.setAdapter(adapter);

        addExercise = (Button) findViewById(R.id.addExerciseBtn);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExerciseToList(extraString);
            }
        });

        back = (ImageButton) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton();
            }
        });

        listViewer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                           long arg3) {
                // TODO Auto-generated method stub
                Log.v("TAG", "CLICKED row number: " + arg2);

                //Toast.makeText(getActivity(), "Test button LOOOOOng click", Toast.LENGTH_SHORT).show();
                editOrDelete(arg2, extraString);
                return true;
            }
        });

        csv = (Button) findViewById(R.id.csvBtn);
        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importWorkout();
                saveList(exercises, extraString);

                //exportWorkout(extraString, exercises);
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
        finish();
    }

    private void delete(int args, String listKey) {
        final int tempArg = args;
        final String tempStr = listKey;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you wish to delete this item?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exercises.remove(tempArg);
                saveList(exercises, tempStr);
                adapter.notifyDataSetChanged();
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

    private void addExerciseToList(String exKey) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
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
                adapter.notifyDataSetChanged();
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

    private void editOrDelete(int position, String exKey){
        final String key = exKey;
        final int pos = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("OPTIONS");

        builder.setNeutralButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editExerciseListItem(pos, key);
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
                delete(pos, key);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void editExerciseListItem(int position, String exKey){
        ExerciseObjects list = exercises.get(position);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final int pos = position;
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
                adapter.notifyDataSetChanged();
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

    /** exportWorkout
     * This method allows the user to export their list of exercises to their device's internal
     * storage. This will specifically save under MyFiles > Internal Storage > MullFit_Workouts.
     * The filename will be the name of the workout the exercise list iss located in.
     * @param fName the filename to be saved as (the "workout" name to be exact).
     * @param arr the arraylist of exercise objects
     */
    public void exportWorkout(String fName, ArrayList<ExerciseObjects> arr){
        StringBuilder sb = new StringBuilder(fName);
        for (int j = 0; j < sb.length(); j++){
            if (sb.charAt(j) == '/'){
                sb.deleteCharAt(j);
            }
        }
        fName = sb.toString();
        String fileName = fName + ".csv";
        String dirName = "MullFit_Workouts";
        String content = "";
        File myDir = new File("sdcard", dirName);

        /*
        if directory doesn't exist, create it
        */
        if(!myDir.exists())
            myDir.mkdirs();


        File myFile = new File(myDir, fileName);

        /*
        Write to file
        */
        try {
            FileWriter fileWriter = new FileWriter(myFile);
            fileWriter.append("EXERCISE, SETS, REPS, WEIGHT\n");
            for (int i = 0; i < arr.size(); i++){
                content = arr.get(i).getEx1() + "," +
                          arr.get(i).getEx2() + "," +
                          arr.get(i).getEx3() + "," +
                          arr.get(i).getEx4() + "\n";
                fileWriter.append(content);
            }
            fileWriter.flush();
            fileWriter.close();
            Toast.makeText(getApplicationContext(), "Workout saved in MullFit_Workouts", Toast.LENGTH_LONG).show();
        }
        catch(IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Unknown error occurred, data not saved. Allow access to storage.",
                    Toast.LENGTH_LONG).show();
        }
        catch(SecurityException err){
            Toast.makeText(getApplicationContext(), "Unknown error occurred, data not saved.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void importWorkout(){
        Intent readIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        readIntent.addCategory(Intent.CATEGORY_OPENABLE);
        readIntent.setType("text/comma-separated-values");
        startActivityForResult(readIntent, READ_REQUEST_CODE);
        onActivityResult(READ_REQUEST_CODE, 2, readIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                try {
                    csvText = readTextFromUri(uri);
                    addFileToExerciseList();
                    adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "File error occurred. Make sure file is in correct format.",
                            Toast.LENGTH_LONG).show();
                } catch (Throwable ex) {
                    Toast.makeText(getApplicationContext(), "Unknown error occurred. Make sure file is in correct format.",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public String readTextFromUri(Uri uri)throws IOException{
        InputStream IS = getContentResolver().openInputStream(uri);
        BufferedReader br = new BufferedReader(new InputStreamReader(IS));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public void addFileToExerciseList(){
        Scanner scan = new Scanner(csvText);
        scan.useDelimiter(",");
        ExerciseObjects blank;
        String ex = "";
        String sets = "";
        String reps = "";
        String wght = "";

        while (scan.hasNext()) {
            ex = scan.next();
            if (scan.hasNext()) {
                sets = scan.next();
            }
            if (scan.hasNext()) {
                reps = scan.next();
            }
            if (scan.hasNext()) {
                wght = scan.next();
            }
            blank = new ExerciseObjects(ex, sets, reps, wght);
            exercises.add(blank);
        }
        scan.close();
    }

}
