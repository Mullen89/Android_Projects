package mullen.liftnotes;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment2 extends Fragment {

    private final String calKey = "key01";
    private final String proKey = "key02";
    private final String fatKey = "key03";
    private final String carbKey = "key04";

    private String cal = "0";
    private String pro = "0";
    private String fat = "0";
    private String carb = "0";

    private TextView calVal;
    private TextView proVal;
    private TextView fatVal;
    private TextView carbVal;

    public TabFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment2, container, false);

        Button calBtn = (Button) view.findViewById(R.id.calEditBtn);
        Button proBtn = (Button) view.findViewById(R.id.proEditBtn);
        Button fatBtn = (Button) view.findViewById(R.id.fatEditBtn);
        Button carbBtn = (Button) view.findViewById(R.id.carbEditBtn);
        Button viewHis = (Button) view.findViewById(R.id.dietHistoryBtn);

        calVal = (TextView) view.findViewById(R.id.calNumView);
        proVal = (TextView) view.findViewById(R.id.proNumView);
        fatVal = (TextView) view.findViewById(R.id.fatNumView);
        carbVal = (TextView) view.findViewById(R.id.carbNumView);

        if(loadValue(calKey) != null) {
            cal = loadValue(calKey);
            calVal.setText(cal);
        }
        if(loadValue(proKey) != null) {
            pro = loadValue(proKey);
            proVal.setText(pro);
        }
        if(loadValue(fatKey) != null) {
            fat = loadValue(fatKey);
            fatVal.setText(fat);
        }
        if(loadValue(carbKey) != null) {
            carb = loadValue(carbKey);
            carbVal.setText(carb);
        }

        calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrSubtract(cal, "c", calKey);
            }
        });

        proBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrSubtract(pro, "p", proKey);
            }
        });

        fatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrSubtract(fat, "f", fatKey);
            }
        });

        carbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrSubtract(carb, "cb", carbKey);
            }
        });

        viewHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DietActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void saveValue(TextView val, String key){
        String str = val.getText().toString();
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(str);
        prefsEditor.putString(key, json);
        prefsEditor.apply();

    }

    private String loadValue(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<String>() {}.getType();
        return gson.fromJson(json, type);
    }

    private int convertToInt(String num) {
        return Integer.parseInt(num);
    }

    private void addOrSubtract(String num, String str, String key){
        final String tStr = str;
        final String tkey = key;
        final int tnum = convertToInt(num);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("ADD or SUBTRACT");
        builder.setMessage("Choose whether to add or subtract from the current value.");

        builder.setNeutralButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addToValue(tnum, tStr, tkey);
            }
        });

        builder.setPositiveButton("CANCEl", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("SUBTRACT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                subtractFromValue(tnum, tStr, tkey);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addToValue(int num, String str, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("ADD");

        final String tStr = str;
        final int tnum = num;
        final String tkey = key;
        final EditText input = new EditText(this.getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int tempNum = Integer.parseInt(input.getText().toString());
                int finalValue = tnum + tempNum;
                switch(tStr){
                    case "c":
                        calVal.setText(finalValue + "");
                        saveValue(calVal, tkey);
                        cal = finalValue + "";
                        break;
                    case "p":
                        proVal.setText(finalValue + "");
                        saveValue(proVal, tkey);
                        pro = finalValue + "";
                        break;
                    case "f":
                        fatVal.setText(finalValue + "");
                        saveValue(fatVal, tkey);
                        fat = finalValue + "";
                        break;
                    case "cb":
                        carbVal.setText(finalValue + "");
                        saveValue(carbVal, tkey);
                        carb = finalValue + "";
                        break;
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
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

    private void subtractFromValue(int num, String str, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("SUBTRACT");

        final String tStr = str;
        final int tnum = num;
        final String tkey = key;
        final EditText input = new EditText(this.getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int tempNum = Integer.parseInt(input.getText().toString());
                int finalValue = tnum - tempNum;
                if(finalValue < 0) {
                    finalValue = 0;
                }
                switch(tStr){
                    case "c":
                        calVal.setText(finalValue + "");
                        saveValue(calVal, tkey);
                        cal = finalValue + "";
                        break;
                    case "p":
                        proVal.setText(finalValue + "");
                        saveValue(proVal, tkey);
                        pro = finalValue + "";
                        break;
                    case "f":
                        fatVal.setText(finalValue + "");
                        saveValue(fatVal, tkey);
                        fat = finalValue + "";
                        break;
                    case "cb":
                        carbVal.setText(finalValue + "");
                        saveValue(carbVal, tkey);
                        carb = finalValue + "";
                        break;
                }

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
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
}
