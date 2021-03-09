package com.smartwebarts.ecoosa.productlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
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

import java.util.ArrayList;
import java.util.List;

import com.smartwebarts.ecoosa.R;
import com.smartwebarts.ecoosa.database.DatabaseClient;
import com.smartwebarts.ecoosa.database.SaveProductList;
import com.smartwebarts.ecoosa.database.Task;
import com.smartwebarts.ecoosa.database.WishItem;
import com.smartwebarts.ecoosa.models.AddWishListResponse;
import com.smartwebarts.ecoosa.models.ProductModel;
import com.smartwebarts.ecoosa.models.UnitModel;
import com.smartwebarts.ecoosa.retrofit.UtilMethods;
import com.smartwebarts.ecoosa.retrofit.mCallBackResponse;
import com.smartwebarts.ecoosa.shared_preference.AppSharedPreferences;
import com.smartwebarts.ecoosa.utils.ApplicationConstants;
import com.smartwebarts.ecoosa.utils.MyGlide;
import com.smartwebarts.ecoosa.utils.Toolbar_Set;

public class ProductListGridAdapter extends RecyclerView.Adapter<ProductListGridAdapter.MyViewHolder>{

    private Context context;
    private List<ProductModel> list;

   // public static final int minValue = 5;

   // int minValue;

    public ProductListGridAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductListGridAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductListGridAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListGridAdapter.MyViewHolder holder, final int position) {

        try {

            MyGlide.with(context, ApplicationConstants.INSTANCE.PRODUCT_IMAGES + list.get(position).getThumbnail(), holder.prodImage);

            holder.txt_pName.setText(list.get(position).getName().trim());
            holder.txt_pInfo.setText(list.get(position).getDescription().trim());

            ArrayList<String> units = new ArrayList<>();
            for (UnitModel unitModel: list.get(position).getUnits()) {
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

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.simple_list_item_1, units);
            holder.txt_unit.setAdapter(arrayAdapter);

            holder.txt_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                    holder.strUnit[position] = list.get(position).getUnits().get(pos).getUnit();
//                    holder.strUnitIn[position] = list.get(position).getUnits().get(pos).getUnitIn();
//                    holder.currentprice.setText(context.getString(R.string.currency) + list.get(position).getUnits().get(pos).getBuingprice());
//
                    List<UnitModel> temp = list.get(position).getUnits();
                    if (temp!=null && temp.size()>0) {
                        holder.strUnit[position] = temp.get(pos).getUnit();
                        holder.strUnitIn[position] = temp.get(pos).getUnitIn();
                        holder.currentprice.setText(context.getString(R.string.currency) + temp.get(pos).getCurrentprice());
                        holder.price.setText(context.getString(R.string.currency) + temp.get(pos).getBuingprice().trim());
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

//            holder.price.setText( list.get(position).getPrice().trim());
//            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.addToWishList.setImageDrawable(
                    context.getDrawable(list.get(position).isWishlist()?
                            R.drawable.ic_heart_red:
                            R.drawable.ic_heart_black));

//            double currentPrice = Double.parseDouble("0"+list.get(position).getCurrentprice().trim());
//            double price = Double.parseDouble("0"+list.get(position).getPrice().trim());
//            double discount = (price - currentPrice) *100/price;
//            NumberFormat nf = NumberFormat.getInstance();
//            nf.setMaximumFractionDigits(2);
//            holder.txt_discountOff.setText(""+nf.format(discount*-1)+"%");

        } catch ( Exception ignored){

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.ID, list.get(position).getId());
                intent.putExtra(ProductDetailActivity.LIST, new Gson().toJson(list));
                context.startActivity(intent);
              /*  Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.ID, list.get(position).getId());
                context.startActivity(intent);*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ProductModel> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

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

        public MyViewHolder(@NonNull View itemView) {
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

            addToWishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppSharedPreferences preferences = null;

                    if (context instanceof ProductListActivity)
                        preferences = new AppSharedPreferences(((ProductListActivity) context).getApplication());
                    else if (context instanceof ProductListActivity2)
                        preferences = new AppSharedPreferences(((ProductListActivity2) context).getApplication());

                    if (preferences != null) {
                        if (!preferences.getLoginUserLoginId().isEmpty()) {

                            if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {

                                UtilMethods.INSTANCE.addtowishlist(context, list.get(MyViewHolder.this.getAdapterPosition()).getId(),
                                        preferences.getLoginUserLoginId(), strUnitIn[getAdapterPosition()],strUnit[getAdapterPosition()]
                                        , new mCallBackResponse() {
                                            @Override
                                            public void success(String from, String message) {
                                                AddWishListResponse response = new Gson().fromJson(message, AddWishListResponse.class);
                                                if (response != null && response.getMessage() != null && response.getMessage().equalsIgnoreCase("success")) {
                                                    list.get(getAdapterPosition()).setWishlist(true);
                                                    notifyDataSetChanged();
                                                }
                                            }

                                            @Override
                                            public void fail(String from) {

                                            }
                                        });
                            } else {
                                UtilMethods.INSTANCE.internetNotAvailableMessage(context);
                            }

                        } else {
                            Toast.makeText(context, "Login to create a wishlist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            ll_Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_Add.setVisibility(View.GONE);
                    ll_addQuan.setVisibility(View.VISIBLE);
                    txtQuan.setText(""+minteger);
                    MyViewHolder.this.addToBag(""+minteger);
                }
            });

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyViewHolder.this.increaseInteger();

                    if (Float.parseFloat(txtQuan.getText().toString()) == minteger) {
                        minus.setOnClickListener(v1 -> ProductListGridAdapter.MyViewHolder.this.decreaseInteger());
                    } else if (Float.parseFloat(txtQuan.getText().toString()) > minteger) {
                        minus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v1) {
                                MyViewHolder.this.decreaseInteger();
                            }
                        });
                    }
                }
            });
        }


        public void increaseInteger() {
            minteger = minteger + 1;
            display(minteger);
        }

        public void decreaseInteger() {
            if (minteger == minValue) {
                minteger = minValue;
                display(minteger);
                ll_addQuan.setVisibility(View.GONE);
                ll_Add.setVisibility(View.VISIBLE);
            }
            else {
                minteger = minteger - 1;
                display(minteger);
            }
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
                   "","");
            items.add(task);
            new SaveProductList(context,items).execute();
            Toolbar_Set.INSTANCE.getCartList((Activity) context);
        }

    }

    public  void delete(final Activity activity, final WishItem task) {

        class GetTasks extends AsyncTask<Void, Void, ArrayList<Task>> {

            @Override
            protected ArrayList<Task> doInBackground(Void... voids) {
                DatabaseClient.getmInstance(activity.getApplicationContext())
                        .getAppDatabase()
                        .wishDao()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Task> tasks) {

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
