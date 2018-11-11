package mullen.liftnotes;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment0 extends Fragment {

    Button addPR;
    ArrayList<PRObject> PRList = new ArrayList<PRObject>();
    PRObjectAdapter PRListAdapter;
    private ListView listViewer;
    private final String PRKey = "PRArgs";

    public TabFragment0() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_personalrecords, container, false);

        if(loadList(PRKey) != null) {
            PRList = loadList(PRKey);
        }

        PRListAdapter = new PRObjectAdapter(getActivity(), PRList);
        listViewer = (ListView) view.findViewById(R.id.prListView);
        listViewer.setAdapter(PRListAdapter);

        addPR = (Button) view.findViewById(R.id.addPRbtn);
        addPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPRToList(PRListAdapter, PRKey);
            }
        });

        listViewer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                           long arg3) {
                // TODO Auto-generated method stub
                Log.v("TAG", "CLICKED row number: " + arg2);

                editOrDelete(arg2, PRListAdapter, PRKey);
                return true;
            }
        });
        return view;
    }

    private void delete(int args) {
        final int tempArg = args;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Delete Record");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PRList.remove(tempArg);
                saveList(PRList, PRKey);
                PRListAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Record Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //Forces keyboard to pop-up whenever "Add PR" button is clicked.
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //Shows the dialog pop-up
        dialog.show();
    }

    private void addPRToList(PRObjectAdapter adapter, String key) {
        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        final PRObjectAdapter tempAdp = adapter;
        final String tKey = key;
        final EditText titleEditText = new EditText(this.getContext());
        final EditText numEditText = new EditText(this.getContext());
        layout.addView(titleEditText);
        layout.addView(numEditText);
        titleEditText.setHint("Lift Title");
        numEditText.setHint("1 Rep Max");
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext())
                .setTitle("Add PR Details")
                .setMessage("Add the name of the Lift and your 1 rep max (1RM).")
                .setView(layout); //<-- add layout

        dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                titleEditText.setMaxLines(1);
                titleEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                numEditText.setMaxLines(1);
                numEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                String title = titleEditText.getText().toString();
                String num = numEditText.getText().toString();
                if(title.equals(null)){
                    title = "";
                }
                if(num.equals(null)){
                    num = "";
                }
                if(!(title.equals("") || num.equals(""))){
                    PRObject blank = new PRObject(title, num);
                    PRList.add(blank);
                    saveList(PRList, tKey);
                    tempAdp.notifyDataSetChanged();
                }
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

    private void editOrDelete(int position, PRObjectAdapter adapter, String key){
        final String tKey = key;
        final PRObjectAdapter tempAdp = adapter;
        final int pos = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("OPTIONS");

        builder.setNeutralButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editPRListItem(pos, tempAdp, tKey);
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
                delete(pos);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void editPRListItem(int position, PRObjectAdapter adapter, String key){
        PRObject list = PRList.get(position);
        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        final int pos = position;
        final PRObjectAdapter tempAdp = adapter;
        final String tKey = key;
        final EditText titleEditText = new EditText(this.getContext());
        final EditText numEditText = new EditText(this.getContext());
        layout.addView(titleEditText);
        layout.addView(numEditText);
        titleEditText.setHint("Lift Title");
        numEditText.setHint("1 Rep Max");
        titleEditText.setText(list.getTitle());
        numEditText.setText(list.getNum());
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext())
                .setTitle("Add PR Details")
                .setMessage("Add the name of the Lift and your 1 rep max (1RM).")
                .setView(layout); //<-- add layout

        dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                titleEditText.setMaxLines(1);
                titleEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                numEditText.setMaxLines(1);
                numEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                String title = titleEditText.getText().toString();
                String num = numEditText.getText().toString();
                if(title.equals(null)){
                    title = "";
                }
                if(num.equals(null)){
                    num = "";
                }
                if(!(title.equals("") || num.equals(""))){
                    PRObject blank = new PRObject(title, num);
                    PRList.set(pos, blank);
                    saveList(PRList, tKey);
                    tempAdp.notifyDataSetChanged();
                }
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

    private void saveList(ArrayList<PRObject> list, String key) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    private ArrayList<PRObject> loadList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<PRObject>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
