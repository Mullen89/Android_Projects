package mullen.liftnotes;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class StorageAccess extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private RecyclerView RecyclerViewer;
    ArrayList<ExerciseObjects> exercises = new ArrayList<ExerciseObjects>();
    private String key = "arg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage_list_layout);
        Bundle extra = getIntent().getExtras();
        final String extraString = extra.getString(key);

        /**
         * READ_EXTERNAL_STORAGE permission may not be needed since it is implicitly implied by the
         * WRITE_EXTERNAL_STORAGE permission.
         */
//*****************************************************PERMISSIONS*****************************************************
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            /*
//             * This shows if permission is NOT granted
//             */
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                new AlertDialog.Builder(this)
//                        .setTitle("Permission Needed")
//                        .setMessage("In order to allow the importing of your workouts from your device," +
//                                " this app needs access to your device's storage (media, files, etc..)")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ActivityCompat.requestPermissions(StorageAccess.this,
//                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                        STORAGE_PERMISSION_CODE);
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .create().show();
//            } else{
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        STORAGE_PERMISSION_CODE);
//            }
//        }
//*********************************************************************************************************************

        if(loadList(extraString) != null){
            exercises = loadList(extraString);
        }
        
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
}
