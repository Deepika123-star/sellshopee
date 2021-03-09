package com.smartwebarts.ecoosa.productlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.smartwebarts.ecoosa.R;
import com.smartwebarts.ecoosa.database.SaveProductList;
import com.smartwebarts.ecoosa.database.Task;
import com.smartwebarts.ecoosa.models.UnitModel;
import com.smartwebarts.ecoosa.retrofit.Unit1;
import com.smartwebarts.ecoosa.retrofit.VariantModel;
import com.smartwebarts.ecoosa.utils.ApplicationConstants;
import com.smartwebarts.ecoosa.utils.MyGlide;
import com.smartwebarts.ecoosa.utils.Toolbar_Set;

import java.util.ArrayList;
import java.util.List;

public class VariantAdapter extends RecyclerView.Adapter<VariantAdapter.MYVIEWHolder> {
    Context context;
    private List<VariantModel> list;
//    int minValue;
    public VariantAdapter(Context context, List<VariantModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MYVIEWHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MYVIEWHolder(LayoutInflater.from(context).inflate(R.layout.variants, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MYVIEWHolder holder, int position) {

        try {

            MyGlide.with(context, ApplicationConstants.INSTANCE.PRODUCT_IMAGES + list.get(position).getThumbnail(), holder.prodImage);

            holder.txt_pName.setText(list.get(position).getName().trim());
            holder.txt_pInfo.setText(list.get(position).getDescription().trim());

            ArrayList<String> units = new ArrayList<>();
            for (Unit1 unitModel: list.get(position).getUnits()) {
                units.add(unitModel.getUnit() + unitModel.getUnitIn());
            }

            if (list.get(position).getUnits()!=null && list.get(position).getUnits().size()>0)
            {
                holder.currentprice.setText(list.get(position).getUnits().get(0).getCurrentprice());
                holder.price.setText(context.getString(R.string.currency) + list.get(position).getUnits().get(0).getBuingprice());
                holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.minValue = list.get(position).getUnits().get(0).getMinUnit();
                holder.minteger = holder.minValue;


            }


            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, units);
            holder.txt_unit.setAdapter(arrayAdapter);

            holder.txt_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    List<Unit1> temp = list.get(position).getUnits();
                    if (temp!=null && temp.size()>0) {
                        holder.strUnit[position] = temp.get(pos).getUnit();
                        holder.strUnitIn[position] = temp.get(pos).getUnitIn();
                        holder.currentprice.setText(temp.get(pos).getCurrentprice());
                        holder.price.setText(temp.get(pos).getBuingprice().trim());

                        holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        try {
                            int a = (int) Double.parseDouble("0"+temp.get(pos).getCurrentprice().trim());
                            int b = (int) Double.parseDouble("0"+temp.get(pos).getBuingprice().trim());
                            int c = (a-b)*(-1);
                            int d = (int) c*100/a;
                            String discount = "" + d;
                            holder.txt_discountOff.setText(discount+"% OFF");
                        } catch (Exception ignored) {
                            holder.txt_discountOff.setText("NEW");
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*holder.addToWishList.setImageDrawable(
                    context.getDrawable(list.get(position).isWishlist()?
                            R.drawable.ic_heart_red:
                            R.drawable.ic_heart_black));*/

//            int currentPrice = (int) Double.parseDouble("0"+list.get(position).getCurrentprice().trim());
//            int price = Double.parseDouble("0"+list.get(position).getPrice().trim());
//            double discount = (price - currentPrice) *100/price;
//            NumberFormat nf = NumberFormat.getInstance();
//            nf.setMaximumFractionDigits(2);
//            holder.txt_discountOff.setText(""+nf.format(discount*-1)+"%");

        } catch ( Exception ignored){

        }

        holder.prodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.ID, list.get(position).getId());
                intent.putExtra(ProductDetailActivity.LIST, new Gson().toJson(list));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public void setList(List<VariantModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MYVIEWHolder extends RecyclerView.ViewHolder {
        ImageView prodImage;
        ImageView addToWishList;
        TextView txt_pName, txt_pInfo, currentprice, price, txt_discountOff, txtQuan;
        Spinner txt_unit;
        RelativeLayout rlQuan;
        LinearLayout ll_addQuan, ll_Add;
        TextView plus, minus;

        int minteger, minValue;
        String[] strUnit = new String[list.size()];
        String[] strUnitIn = new String[list.size()];
        public MYVIEWHolder(@NonNull View itemView) {
            super(itemView);
            prodImage = itemView.findViewById(R.id.prodImage);
            addToWishList = itemView.findViewById(R.id.addToWishList);

            txt_pName = itemView.findViewById(R.id.txt_pName);
            txt_pInfo = itemView.findViewById(R.id.txt_pInfo);
            txt_unit = itemView.findViewById(R.id.txt_current_price);
            currentprice = itemView.findViewById(R.id.currentprice);
            price = itemView.findViewById(R.id.txt_price);
            txt_discountOff = itemView.findViewById(R.id.txt_discountOff);

            rlQuan =  itemView.findViewById(R.id.rlQuan);
            ll_Add =  itemView.findViewById(R.id.ll_Add);
            ll_addQuan =  itemView.findViewById(R.id.ll_addQuan);
            txtQuan =  itemView.findViewById(R.id.txtQuan);
            minus =  itemView.findViewById(R.id.minus);
            plus =  itemView.findViewById(R.id.plus);

            ll_Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_Add.setVisibility(View.GONE);
                    ll_addQuan.setVisibility(View.VISIBLE);
                    txtQuan.setText("" + minteger);
                    VariantAdapter.MYVIEWHolder.this.addToBag("" + minteger);
                }
            });

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VariantAdapter.MYVIEWHolder.this.increaseInteger();

                    if (Float.parseFloat(txtQuan.getText().toString()) == minteger) {
                        minus.setOnClickListener(v1 -> MYVIEWHolder.this.decreaseInteger());
                    } else if (Float.parseFloat(txtQuan.getText().toString()) > minteger) {
                        minus.setOnClickListener(v1 -> MYVIEWHolder.this.decreaseInteger());
                    }
                }
            });
        }


        public void increaseInteger() {
            minteger = minteger + 1;
            display(minteger);
        }

        public void decreaseInteger() {

//            Log.e("TAG", "decreaseInteger: " + minteger );
//            Log.e("TAG", "decreaseInteger: " + minValue );

            if (minteger <= minValue) {
               minteger = minValue;
                display(minteger);
                ll_addQuan.setVisibility(View.GONE);
                ll_Add.setVisibility(View.VISIBLE);

            }
            else {
                minteger = minteger - 1;
                display(minteger);
            }

//remove code add here
        }

        private void display(Integer number) {
            txtQuan.setText("" + number);
            addToBag(""+number);
        }

        public void addToBag(String quantity) {

            int price = Integer.parseInt("0"+list.get(getAdapterPosition()).getUnits().get(txt_unit.getSelectedItemPosition()).getCurrentprice().trim());
            int qty = Integer.parseInt("0"+quantity.trim());
            int finalprice = price*qty;

            List<Task> items = new ArrayList<>();
            Task task = new Task(list.get(getAdapterPosition()),
                    quantity,
                    list.get(getAdapterPosition()).getUnits().get(txt_unit.getSelectedItemPosition()).getUnit(),
                    list.get(getAdapterPosition()).getUnits().get(txt_unit.getSelectedItemPosition()).getUnitIn(),
                    list.get(getAdapterPosition()).getUnits().get(txt_unit.getSelectedItemPosition()).getCurrentprice(),
                    ""+finalprice,
                    "", "",minValue);

            items.add(task);
            new SaveProductList(context.getApplicationContext(),items).execute();
            Toolbar_Set.INSTANCE.getCartList((Activity) context);

        }

        }

    }

