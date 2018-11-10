package mullen.liftnotes;

import android.content.Intent;
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

public class DietActivity extends AppCompatActivity {

    private ArrayList<DietObjects> historyList = new ArrayList<DietObjects>();
    private String key = "args";
    private ListView listViewer;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        Bundle extra = getIntent().getExtras();
        final String extraString = extra.getString(key);

        if(loadHistoryList(extraString) != null){
            historyList = loadHistoryList(extraString);
        }

        final DietObjectsAdapter adapter = new DietObjectsAdapter(this, historyList);
        listViewer = (ListView) findViewById(R.id.histListView);
        listViewer.setAdapter(adapter);

        back = (ImageButton) findViewById(R.id.histBackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton();
            }
        });
    }

    private ArrayList<DietObjects> loadHistoryList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<DietObjects>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void backButton(){
        Intent intentBack = new Intent(this, MainActivity.class);
        intentBack.putExtra("frag", 2);

        startActivity(intentBack);
    }
}
