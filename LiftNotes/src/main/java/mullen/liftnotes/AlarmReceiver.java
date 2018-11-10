package mullen.liftnotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    private ArrayList<DietObjects> historyList = new ArrayList<DietObjects>();
    private final String histKey = "key005";
    private String cal = "0";
    private String pro = "0";
    private String fat = "0";
    private String carb = "0";

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Alarm Test", Toast.LENGTH_SHORT).show();

        if(loadHistoryList(context,histKey) != null) {
            historyList = loadHistoryList(context,histKey);
        }
        if(loadValue(context,"key01") != null){
            cal = loadValue(context,"key01");
        }
        if(loadValue(context,"key02") != null){
            pro = loadValue(context,"key02");
        }
        if(loadValue(context,"key03") != null){
            fat = loadValue(context,"key03");
        }
        if(loadValue(context,"key04") != null){
            carb = loadValue(context,"key04");
        }
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        saveToHistory(context, currentDate, cal, pro, fat, carb, histKey);

        cal = "0";
        pro = "0";
        fat = "0";
        carb = "0";
        saveValue(context, cal, "key01");
        saveValue(context, pro, "key02");
        saveValue(context, fat, "key03");
        saveValue(context, carb, "key04");
    }

    private ArrayList<DietObjects> loadHistoryList(Context context, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<DietObjects>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void saveToHistory(Context context, String d1, String c1, String p1, String f1, String cb1, String key){
        DietObjects tempHist = new DietObjects(d1,c1, p1, f1, cb1);
        historyList.add(0, tempHist);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(historyList);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    private String loadValue(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<String>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void saveValue(Context context, String val, String key){
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(val);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }
}
