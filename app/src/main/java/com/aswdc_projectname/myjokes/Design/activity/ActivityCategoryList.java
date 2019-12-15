package com.aswdc_projectname.myjokes.Design.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.aswdc_projectname.myjokes.Design.adapter.AdapterCategory;
import com.aswdc_projectname.myjokes.OnTextChange;
import com.aswdc_projectname.myjokes.bal.BALCategory;
import com.aswdc_projectname.myjokes.model.BeanCategory;
import com.aswdc_projectname.myjokes.R;
import com.aswdc_projectname.myjokes.util.Constant;

import java.util.ArrayList;



public class ActivityCategoryList extends BaseActivity implements OnTextChange {

    RecyclerView recyclerView;
    ArrayList<BeanCategory> arrayCategory;
    AdapterCategory adapterCategory;
    TextView tvNoData;
    EditText searchBar;
    boolean backPress = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_commonlist);
        super.onCreate(savedInstanceState);
        removeElevation();
        databaseData();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        setDataToRecyclerView();
        searchBarCode(searchBar, this);
    }

    @Override
    public void initVariables() {
        recyclerView = findViewById(R.id.rcvList);
        searchBar = findViewById(R.id.et_search_bar);
    }

    @Override
    public void bindWidgetEvents() {

    }

    @Override
    public void onChange(String filterValue) {
        filter(filterValue);
    }

    private void setDataToRecyclerView() {
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        adapterCategory = new AdapterCategory(arrayCategory, this);
        recyclerView.setAdapter(adapterCategory);
    }

    private void databaseData() {
        arrayCategory = BALCategory.getInstance().selectAllCategory();
    }

    private void filter(String searchText) {
        ArrayList<BeanCategory> filterdList = new ArrayList<>();
        for (BeanCategory item : arrayCategory) {
            if (item.getCategoryName().toLowerCase().contains(searchText.toLowerCase())) {
                filterdList.add(item);
            }
        }

        adapterCategory.filterList(filterdList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){

            case R.id.item3:
                Intent updateintent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id="+getPackageName()));
                startActivity(updateintent);
                return true;
            case R.id.item4:
                Intent inte = new Intent(ActivityCategoryList.this, ActivityFeedback.class);
                startActivity(inte);
                return true;

            case R.id.item5:
                Intent sharee = new Intent();
                sharee.setAction("android.intent.action.SEND");
                sharee.setType("text/plain");
                sharee.putExtra("android.intent.extra.TEXT", Constant.AppPlayStoreLink );
                startActivity(sharee);
                return true;
            case R.id.item6:
                Intent moreappsintent = new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=pub:Darshan+Institute+of+Engineering+%26+Technology"));
                startActivity(moreappsintent);
                return true;
            case R.id.item7:
                Intent in = new Intent(ActivityCategoryList.this, DeveloperActivity.class);
                startActivity(in);
                return true;

            case R.id.favorite:
                Intent i = new Intent(this, ActivityFavoriteList.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(findViewById(android.R.id.content),"Please click BACK again to exit",Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
