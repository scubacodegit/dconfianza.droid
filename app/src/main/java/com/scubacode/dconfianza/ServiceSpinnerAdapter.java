package com.scubacode.dconfianza;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scubacode.model.Service;

import java.util.List;

public class ServiceSpinnerAdapter extends ArrayAdapter<Service>
{
    private List<Service> values;

    public ServiceSpinnerAdapter(Context context, int textViewResourceId, List<Service> values)
    {
        super(context, textViewResourceId, values);
        this.values = values;
    }

    public int getCount()
    {
        return values.size()-1;
    }

    public Service getItem(int position) {
        return values.get(position);
    }

    public int GetPositionByItemID(int itemID)
    {
        int position = -1;

        for (int i = 0; i < values.size(); i++)
        {

            if (values.get(i).getID() == itemID)
            {
                position = i;
                break;
            }
        }

        return position;

    }

    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(this.getItem(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(this.getItem(position).getName());
        return label;
    }
}
