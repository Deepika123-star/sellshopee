package com.smartwebarts.ecoosa.productlist;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smartwebarts.ecoosa.R;
import com.smartwebarts.ecoosa.retrofit.AttributModel;

import java.util.ArrayList;
import java.util.List;

public class ValuesAdapter extends ArrayAdapter<AttributModel> {

    public ValuesAdapter(Context context,
                         List<AttributModel> algorithmList) {
        super(context, 0, algorithmList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.value_spinner, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.valuespinner);
        AttributModel currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getValue());
            textViewName.setTag(currentItem.getId());
        }
        return convertView;
    }
}
