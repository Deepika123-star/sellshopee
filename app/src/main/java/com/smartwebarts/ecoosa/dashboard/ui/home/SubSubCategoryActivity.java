package com.smartwebarts.ecoosa.dashboard.ui.home;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartwebarts.ecoosa.R;
import com.smartwebarts.ecoosa.category.CategoryListAdapter;
import com.smartwebarts.ecoosa.models.CategoryModel;
import com.smartwebarts.ecoosa.models.SubCategoryModel;
import com.smartwebarts.ecoosa.models.SubSubCategoryModel;
import com.smartwebarts.ecoosa.productlist.ProductAdapter;
import com.smartwebarts.ecoosa.retrofit.UtilMethods;
import com.smartwebarts.ecoosa.retrofit.mCallBackResponse;
import com.smartwebarts.ecoosa.utils.Toolbar_Set;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SubSubCategoryActivity extends AppCompatActivity {

    public static final String CID = "cid";
    public static final String SID = "sid";
    public static final String SNAME = "sname";
    RecyclerView rvSubSubCategories;
    String sname;
    private CategoryModel category;
    private SubCategoryModel subCategory;

    //    private List<CategoryModel> subCat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subsubcategory);
Toolbar_Set.INSTANCE.setToolbar(this);

        category = (CategoryModel) getIntent().getExtras().get(CID);
        subCategory = (SubCategoryModel) getIntent().getExtras().get(SID);
        sname = getIntent().getExtras().getString(SNAME, "");
        rvSubSubCategories=findViewById(R.id.rvSubSubCategories);
        UtilMethods.INSTANCE.subSubCategory(this, category.getId(), subCategory.getId(), new mCallBackResponse() {
            @Override
            public void success(String from, String message) {
                Type listType = new TypeToken<ArrayList<SubSubCategoryModel>>(){}.getType();
                List<SubSubCategoryModel> list = new Gson().fromJson(message, listType);
                Log.e("TAG", "success: " + message);

                //Todo : set recycler
               setAdpter(list);


            }

            @Override
            public void fail(String from) {
            }
        });

    }

    private void setAdpter(List<SubSubCategoryModel> list) {
        ProductAdapter adapter = new ProductAdapter(this, list, category, subCategory);
        rvSubSubCategories.setItemAnimator(new DefaultItemAnimator());
        rvSubSubCategories.setLayoutManager(new GridLayoutManager(this,3));
        rvSubSubCategories.setAdapter(adapter);
    }


}
