package mullen.liftnotes;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PRActivity extends AppCompatActivity {

    private ArrayList<PRObject> prHistoryList = new ArrayList<PRObject>();
    private ArrayList<PRObject> PRList = new ArrayList<PRObject>();
    private PRObject tempPR;
    private String key = "arg";
    private ListView listViewer;
    private ImageButton prBackBtn;
    private final String PRKey = "PRArgs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pr);
        Bundle extra = getIntent().getExtras();
        final String extraString = extra.getString(key);

        /**
         * Checks to see if there is already a list that was saved. If so, it
         * loads that list.
         */
        if(loadHistoryList(extraString) != null){
            prHistoryList = loadHistoryList(extraString);
        }

        if(loadHistoryList(PRKey) != null) {
            PRList = loadHistoryList(PRKey);
            for (int i = 0; i < PRList.size(); i++) {
                if (PRList.get(i).getTitle().equals(extraString)) {
                    if (prHistoryList.size() == 0 || !(PRList.get(i).getDate().equals(prHistoryList.get(0).getDate()))){
                        tempPR = new PRObject(PRList.get(i).getTitle(), PRList.get(i).getNum(), PRList.get(i).getDate());
                        prHistoryList.add(0, tempPR);
                        saveHistoryList(prHistoryList, extraString);
                        break;
                    }
                    break;
                }
            }
        }

        final PRObjectAdapter adapter = new PRObjectAdapter(this, prHistoryList);
        listViewer = (ListView) findViewById(R.id.PRhistListView);
        listViewer.setAdapter(adapter);

        prBackBtn = (ImageButton) findViewById(R.id.prHistBckBtn);
        prBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private ArrayList<PRObject> loadHistoryList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<PRObject>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void saveHistoryList(ArrayList<PRObject> list, String key) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }
}
