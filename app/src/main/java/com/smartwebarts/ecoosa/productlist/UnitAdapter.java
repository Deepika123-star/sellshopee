package com.smartwebarts.ecoosa.productlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.smartwebarts.ecoosa.R;
import com.smartwebarts.ecoosa.models.ProductDetailModel;
import com.smartwebarts.ecoosa.models.ProductModel;
import com.smartwebarts.ecoosa.utils.ApplicationConstants;
import com.smartwebarts.ecoosa.utils.MyGlide;
import java.util.List;

public class UnitAdapter  extends RecyclerView.Adapter<UnitAdapter.MYViewHolder> {
    Context context;
    private List<ProductModel> list;
    private static int currentposition = -1;
    public UnitAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MYViewHolder(LayoutInflater.from(context).inflate(R.layout.unit_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        MyGlide.withCircle(context,
                ApplicationConstants.INSTANCE.PRODUCT_IMAGES + list.get(position).getThumbnail(),
                holder.colorUnit);

        holder.itemView.setOnClickListener(v -> {
            if (context instanceof ProductDetailActivity) {
              //  ((ProductDetailActivity) context).list.get(position).getId();
                currentposition = position;
                notifyDataSetChanged();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.ID, list.get(position).getId());
                intent.putExtra(ProductDetailActivity.LIST, new Gson().toJson(list));
                context.startActivity(intent);

            }
        });

        if (currentposition == position) {
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.zoomin);
            holder.itemView.startAnimation(animation);
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
//        return list.size();
    }

    public void setList(List<ProductModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MYViewHolder extends RecyclerView.ViewHolder {
        ImageView colorUnit;
        public MYViewHolder(@NonNull View itemView) {
            super(itemView);
            colorUnit=itemView.findViewById(R.id.colorcardview);
        }
    }
}
