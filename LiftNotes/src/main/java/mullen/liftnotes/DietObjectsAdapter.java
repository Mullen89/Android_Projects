package mullen.liftnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class DietObjectsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<DietObjects> objects;

    private class ViewHolder {
        TextView textview1;
        TextView textview2;
        TextView textview3;
        TextView textview4;
    }

    public DietObjectsAdapter(Context context, ArrayList<DietObjects> objects) {
        inflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.diet_item_layout, null);
            holder.textview1 = (TextView) convertView.findViewById(R.id.);
            holder.textview2 = (TextView) convertView.findViewById(R.id.);
            holder.textview3 = (TextView) convertView.findViewById(R.id.);
            holder.textview4 = (TextView) convertView.findViewById(R.id.);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textview1.setText(objects.get(position).getCal());
        holder.textview2.setText(objects.get(position).getPro());
        holder.textview3.setText(objects.get(position).getFat());
        holder.textview4.setText(objects.get(position).getCarb());
        return convertView;
    }
}