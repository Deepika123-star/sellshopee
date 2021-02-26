package com.smartwebarts.ecoosa.productlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartwebarts.ecoosa.R;
import com.smartwebarts.ecoosa.models.SubSubCategoryModel;
import com.smartwebarts.ecoosa.retrofit.AttributModel;

import java.util.List;

public class ColorAdapterForAttribut extends RecyclerView.Adapter<ColorAdapterForAttribut.MYViewHolder> {
    private Context context;
    private List<AttributModel> list;

    public ColorAdapterForAttribut(Context context, List<AttributModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MYViewHolder(LayoutInflater.from(context).inflate(R.layout.unit_color_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
holder.title.setText(list.get(position).getTitle());
holder.values.setText(list.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class MYViewHolder extends RecyclerView.ViewHolder {
        TextView title,values;
        public MYViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.valuofTitle);
            values=itemView.findViewById(R.id.valuofValue);

        }
    }
}
