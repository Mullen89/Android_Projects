package mulltech.journalpro;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utility {

    /**
     * This save method allows the app to save a single String value to a key.
     */
    public static void saveValue(String val, String key, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(val);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    /**
     * This save method allows the app to save a list of String values to a key.
     */
    public static void saveList(ArrayList<String> list, String key, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    /**
     * This save method allows the app to load a String value from shared preferences
     * based on a key value pair.
     */
    public static String loadValue(String key, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<String>() {}.getType();
        return gson.fromJson(json, type);
    }

    /**
     * This save method allows the app to load a list of String values from shared preferences
     * based on a key value pair.
     */
    public static ArrayList<String> loadList(String key, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
