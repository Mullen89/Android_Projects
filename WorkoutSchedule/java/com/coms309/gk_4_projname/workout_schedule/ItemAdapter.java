package com.coms309.gk_4_projname.workout_schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by esmul on 2/21/2018.
 */

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflator;
    String[] workouts;
    String[] sets;
    String[] reps;
    String[] weight;

    public ItemAdapter(Context c, String[] wk, String[] s, String[] r, String[] wgt){
        workouts = wk;
        sets = s;
        reps = r;
        weight = wgt;
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return workouts.length;
    }

    @Override
    public Object getItem(int i) {
        return workouts[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflator.inflate(R.layout.workout_details, null);
        TextView workoutTV = (TextView) v.findViewById(R.id.wk_detailTxtVw);
        TextView setsTV = (TextView) v.findViewById(R.id.sets_detailTxtVw);
        TextView repsTV = (TextView) v.findViewById(R.id.reps_detailTxtVw);
        TextView wgtTV = (TextView) v.findViewById(R.id.wgt_detailTxtVw);

        String workoutDesc = workouts[i];
        String setNum = sets[i];
        String repNum = reps[i];
        String wgtNum = weight[i];

        workoutTV.setText(workoutDesc);
        setsTV.setText(setNum);
        repsTV.setText(repNum);
        wgtTV.setText(wgtNum);

        return v;
    }
}
