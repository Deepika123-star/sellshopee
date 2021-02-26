package com.smartwebarts.ecoosa.productlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.smartwebarts.ecoosa.R;
import com.smartwebarts.ecoosa.database.SaveProductList;
import com.smartwebarts.ecoosa.database.Task;
import com.smartwebarts.ecoosa.models.ProductDetailImagesModel;
import com.smartwebarts.ecoosa.models.ProductDetailModel;
import com.smartwebarts.ecoosa.models.ProductModel;
import com.smartwebarts.ecoosa.models.Productbrand;
import com.smartwebarts.ecoosa.models.SubCategoryModel;
import com.smartwebarts.ecoosa.models.UnitModel;
import com.smartwebarts.ecoosa.retrofit.AttributModel;
import com.smartwebarts.ecoosa.retrofit.UtilMethods;
import com.smartwebarts.ecoosa.retrofit.mCallBackResponse;
import com.smartwebarts.ecoosa.utils.ApplicationConstants;
import com.smartwebarts.ecoosa.utils.CustomSlider;
import com.smartwebarts.ecoosa.utils.MyGlide;
import com.smartwebarts.ecoosa.utils.Toolbar_Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductDetailActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {

    public static final String LIST = "list";
    private com.daimajia.slider.library.SliderLayout viewPager;
    private int currentPage = 0;
    private ArrayList<String> sliderImage= new ArrayList<String>();
    private ProductModel addToCartProductItem;
  private List<AttributModel>attributModelList;
    private Productbrand productbrand;
    private TextView brandName,tvName, txt_vName, tvDescription2, tvPrice,tvPricen, tvCurrentPrice, tvDiscount, tvOffer,color1;
    private CardView cvoffer;
    private ImageView ivVeg,brandImages;
    public static final String ID = "id";
    private String pid;
    private RecyclerView recyclerView,productImagesGrid,colorrecyclerView;
    private String unit="", unitIn="", currentPrice="0", buingPrice = "0", discount = "0";
    private int id=0;
    UnitAdapter unitAdapter;
    List<ProductModel> list;
/*for attribute*/
ArrayList<String> titlearry=new ArrayList<String>();
    ArrayList<String> valuearry=new ArrayList<String>();
    Spinner title,value;
    String titlegetValues,valuesOfattr ;
    private boolean isFirstTime = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar_Set.INSTANCE.setToolbar(this);
        viewPager = findViewById(R.id.viewPager);
        tvName = findViewById(R.id.txt_pName);
        txt_vName = findViewById(R.id.txt_vName);
        brandName = findViewById(R.id.brandName);
        brandImages = findViewById(R.id.brandImages);


//        tvDescription = findViewById(R.id.txt_pInfo);
        tvDescription2 = findViewById(R.id.tvDescription);
        tvDiscount = findViewById(R.id.txt_discount);
        ivVeg = findViewById(R.id.iv_veg);
        tvPrice = findViewById(R.id.txt_price);
        tvPricen = findViewById(R.id.txt_pricen);
        tvCurrentPrice = findViewById(R.id.txt_current_price);
        //tvOffer = (TextView) findViewById(R.id.tvoffer);
        //cvoffer = (CardView) findViewById(R.id.cvoffer);
        recyclerView = findViewById(R.id.recyclerView);
        colorrecyclerView = findViewById(R.id.colorrecyclerView);


        String temp = getIntent().getExtras().getString(LIST, "");

        if (temp!=null && !temp.isEmpty()) {
            Type type = new TypeToken<List<ProductModel>>(){}.getType();
            list = new Gson().fromJson(temp, type);
            setAdapter(list);
            setItemList(list, isFirstTime);
            isFirstTime = false;
        }

        productImagesGrid = findViewById(R.id.productImagesGrid);
        pid = getIntent().getExtras().getString(ID);
        getDetails();
        attibute();
        MyView();
    }

    private void setItemList(List<ProductModel> list, boolean isSet) {
        if (isSet) unitAdapter.setList(list);
    }

    private void MyView() {
     /*   title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttributModel itemOfTitle= (AttributModel) parent.getItemAtPosition(position);
                titlegetValues=itemOfTitle.getTitle();
            }
        });

        value.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttributModel itemOfTitle= (AttributModel) parent.getItemAtPosition(position);
                valuesOfattr=itemOfTitle.getValue();
            }
        });*/
    }


    private void setUpImageSlider(List<ProductDetailImagesModel> list) {

//        ImageAdapter imageAdapter = new ImageAdapter(this, list);

//        viewPager.setAdapter(imageAdapter);
//
//        /*After setting the adapter use the timer */
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == sliderImage.size()) {
//                    currentPage = 0;
//                }
//                viewPager.setCurrentItem(currentPage++, true);
//            }
//        };
//
//        Timer timer = new Timer(); // This will create a new Thread
//
//        //delay in milliseconds before task is to be executed
//        long DELAY_MS = 500;
//
//        // time in milliseconds between successive task executions.
//        long PERIOD_MS = 3000;
//
//        timer.schedule(new TimerTask() { // task to be scheduled
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, DELAY_MS, PERIOD_MS);

        for ( ProductDetailImagesModel data : list) {
            CustomSlider textSliderView = new CustomSlider(this);
            // initialize a SliderLayout
            textSliderView
                    .description("")
                    .image(ApplicationConstants.INSTANCE.PRODUCT_IMAGES + data.getThumbnail())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra","");

            viewPager.addSlider(textSliderView);
        }

        viewPager.getPagerIndicator().setDefaultIndicatorColor (0xff68B936, 0xffD0D0D0);
        viewPager.startAutoCycle(10000, 10000, true);
        viewPager.setCurrentPosition(0);
    }

    public void addToBag(View view) {

        if (addToCartProductItem !=null){
            List<Task> items = new ArrayList<>();
            Task task = new Task(addToCartProductItem, "1", unit, unitIn, currentPrice, currentPrice,titlegetValues,valuesOfattr);
            items.add(task);
            new SaveProductList(this,items).execute();
            Toolbar_Set.INSTANCE.getCartList(this);
        }

    }

     public  void getDetails() {

         if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

             UtilMethods.INSTANCE.getProductDetails(this, pid, new mCallBackResponse() {
                 @Override
                 public void success(String from, String message) {
                     if (!message.isEmpty()){
                         Type listType = new TypeToken<ArrayList<ProductDetailModel>>(){}.getType();
                         List<ProductDetailModel> list = new Gson().fromJson(message, listType);

                         addToCartProductItem = new ProductModel(list.get(0));
                         tvName.setText(addToCartProductItem.getName().trim());
                         if (list.get(0)!=null && list.get(0).getVendorName()!=null) {
                             txt_vName.setText("("+list.get(0).getVendorName().trim()+")");
                         }


//                         tvDescription.setText(addToCartProductItem.getDescription().trim());
                         tvDescription2.setText(addToCartProductItem.getDescription().trim());
                         if (addToCartProductItem.getUnits()!=null && addToCartProductItem.getUnits().size()>0) {
                             unit = addToCartProductItem.getUnits().get(0).getUnit().trim();
                             unitIn = addToCartProductItem.getUnits().get(0).getUnitIn().trim();
                             currentPrice = addToCartProductItem.getUnits().get(0).getCurrentprice().trim();
                             buingPrice = addToCartProductItem.getUnits().get(0).getBuingprice().trim();
                         }

                         try {
                             int a = (int) Double.parseDouble("0"+currentPrice);
                             int b = (int) Double.parseDouble("0"+buingPrice);
                             int c = b-a;
                             discount = ""+c;

                             try {
                                 int p = c*100/b;
                                 tvOffer.setText(p + "%");
                             } catch (Exception ignored){
                                 cvoffer.setVisibility(View.GONE);
                             }
                         } catch (Exception ignored) {}


                         tvCurrentPrice.setText(getString(R.string.currency) + " " + currentPrice);
                         tvPrice.setText(  unit + unitIn);
                        brandName.setText(list.get(0).getBrandName());
                         MyGlide.with(ProductDetailActivity.this, ApplicationConstants.INSTANCE.BRAND_IMAGES + list.get(0).getBrandImage(),brandImages);

                        // brandImages.setImageDrawable(getDrawable(R.drawable.ic_bag));
                         tvPricen.setText( getString(R.string.currency) + " " + buingPrice);
                         tvPricen.setPaintFlags(tvPricen.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                         tvDiscount.setText(getString(R.string.currency) + discount);

                /*         if (addToCartProductItem.getProductType().equalsIgnoreCase("Non-Vegetarian")) {
                             ivVeg.setImageDrawable(getDrawable(R.drawable.nonveg));
                             ivVeg.setVisibility(View.VISIBLE);
                         } else if (addToCartProductItem.getProductType().equalsIgnoreCase("Vegetarian")){
                             ivVeg.setImageDrawable(getDrawable(R.drawable.veg));
                             ivVeg.setVisibility(View.VISIBLE);
                         } else {
                             ivVeg.setVisibility(View.GONE);
                         }*/

                         setRecycler(addToCartProductItem.getUnits());

                     }
                 }

                 @Override
                 public void fail(String from) {

                 }
             });

             UtilMethods.INSTANCE.getProductImages(this, pid, new mCallBackResponse() {
                 @Override
                 public void success(String from, String message) {
                     if (!message.isEmpty()){
                         Type listType = new TypeToken<ArrayList<ProductDetailImagesModel>>(){}.getType();
                         List<ProductDetailImagesModel> list = new Gson().fromJson(message, listType);
                         setUpImageSlider(list);
                     }
                 }

                 @Override
                 public void fail(String from) {

                 }
             });
         } else {
             UtilMethods.INSTANCE.internetNotAvailableMessage(this);
         }
     }


    private void setAdapter(List<ProductModel> myitem) {
       unitAdapter=new UnitAdapter(ProductDetailActivity.this,myitem);
        colorrecyclerView.setAdapter(unitAdapter);
    }



    private void setRecycler(List<UnitModel> list) {
        UnitListAdapter adapter = new UnitListAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    public void onClick(int position) {

        if (addToCartProductItem.getUnits().get(position) !=null) {
            UnitModel temp = addToCartProductItem.getUnits().get(position);
            unit = temp.getUnit();
            unitIn = temp.getUnitIn();
            currentPrice = temp.getCurrentprice();
            buingPrice = temp.getBuingprice();
            tvCurrentPrice.setText(getString(R.string.currency) + " " + currentPrice);
            tvPricen.setText(getString(R.string.currency) + " " + buingPrice);
            tvPricen.setPaintFlags(tvPricen.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvPrice.setText(  unit + unitIn);

            try {
                int a = (int) Double.parseDouble("0"+currentPrice);
                int b = (int) Double.parseDouble("0"+buingPrice);
                int c = b-a;
                discount = ""+c;
            } catch (Exception ignored) {}

            tvDiscount.setText(getString(R.string.currency) + discount);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toolbar_Set.INSTANCE.getCartList(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
    private void attibute() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(ProductDetailActivity.this)) {

            UtilMethods.INSTANCE.attribut(ProductDetailActivity.this,pid, new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                        /*Set Data In List View */
                    Type type = new TypeToken<List<AttributModel>>(){}.getType();
                    attributModelList = new Gson().fromJson(message, type);
                    setRecycler1(attributModelList);
                    /*Type type = new TypeToken<List<AttributModel>>(){}.getType();
                    List<AttributModel> attr = new Gson().fromJson(message, type);
                    Log.d("Attribute===========", "success: "+attr);
*/
                   /* showDialog(holder, position, list.get(position), couponList);*/
                   /* ArrayList<String> attribut =new ArrayList<>();
                    for (int i=0;i<attr.size();i++){
                        attribut.add(attr.get(i).getTitle());
                    }*/

                   /* String abc=attr.get(0).getId();*/
                  /*  Spinner_Adapter adapter = new Spinner_Adapter(ProductDetailActivity.this,attr);
                    title.setAdapter(adapter);*/
                   // ArrayList<String> attributvalu =new ArrayList<>();
                  /*  for (int i=0;i<attr.size();i++){
                        attributvalu.add(attr.get(i).getValue());
                    }
                    ValuesAdapter adapter2 = new ValuesAdapter(ProductDetailActivity.this,attr);
                    value.setAdapter(adapter2);*/

                 /*   ArrayAdapter<String> adaptervalue = new ArrayAdapter<String>(ProductDetailActivity.this,android.R.layout.simple_spinner_item, titlearry);
                    value.setAdapter(adaptervalue);*/

                }

                @Override
                public void fail(String from) {
                  /*  new SweetAlertDialog(ProductDetailActivity.this)
                            .setTitleText("No Attribute ")
                            .setContentText("No Attribute available for this item")
                            .show();*/
                }
            });
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(ProductDetailActivity.this);
        }
    }

    private void setRecycler1(List<AttributModel> attributModelList) {
        ColorAdapterForAttribut colorAdapterForAttribut=new ColorAdapterForAttribut(this,attributModelList);
        productImagesGrid.setAdapter(colorAdapterForAttribut);

    }

}
