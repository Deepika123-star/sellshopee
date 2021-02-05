package com.smartwebarts.ecoosa.dashboard.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.smartwebarts.ecoosa.R;
import com.smartwebarts.ecoosa.utils.MyGlide;

public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerImageAdapter.MyViewHolder> {

private Context context;
private ArrayList<String> list;

    RecyclerImageAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_view2, parent, false));
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyGlide.with(context,list.get(position),holder.imageView);
        }

@Override
public int getItemCount() {
        return list.size();
        }

class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.tvName);
    }
}
}

