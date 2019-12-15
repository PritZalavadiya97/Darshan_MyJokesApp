package com.aswdc_projectname.myjokes.Design.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aswdc_projectname.myjokes.Design.adapter.AdapterTopics;
import com.aswdc_projectname.myjokes.OnTextChange;
import com.aswdc_projectname.myjokes.R;
import com.aswdc_projectname.myjokes.bal.BALTopic;
import com.aswdc_projectname.myjokes.model.BeanTopics;

import java.util.ArrayList;


public class ActivityFavoriteList extends BaseActivity implements OnTextChange {

    RecyclerView recyclerView;
    ArrayList<BeanTopics> arrayTopic;
    AdapterTopics adapterTopics;
    int categoryID;
    TextView tvNoData;
    EditText searchBar;
    Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_commonlist);
        super.onCreate(savedInstanceState);
        setTitle("Favorite List");

    }

    @Override
    public void onChange(String filterValue) {
        filter(filterValue);
    }

    @Override
    public void initVariables() {
        tvNoData = findViewById(R.id.noData);
        searchBar = findViewById(R.id.et_search_bar);
        recyclerView = findViewById(R.id.rcvList);
    }

    @Override
    public void bindWidgetEvents() {

    }

    private void getTopicID() {

        mIntent = getIntent();
        categoryID = mIntent.getIntExtra(getString(R.string.TopicID), 10);

    }

    private void filter(String searchText){
        ArrayList<BeanTopics> filterdList = new ArrayList<>();

        for (BeanTopics item : arrayTopic) {
            if (item.getTopicName().toLowerCase().contains(searchText.toLowerCase())){
                filterdList.add(item);
            }
        }
        if(filterdList.size()==0){
            tvNoData.setVisibility(View.VISIBLE);
        }else {
            tvNoData.setVisibility(View.GONE);
        }
        adapterTopics.filterList(filterdList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        removeElevation();
        getTopicID();
        searchBarCode(searchBar, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        databaseData();
        adapterTopics = new AdapterTopics(arrayTopic, this);
        recyclerView.setAdapter(adapterTopics);
        if (arrayTopic.size()==0){
            tvNoData.setVisibility(View.VISIBLE);
            tvNoData.setText("No Favorite data");
        }else {
            tvNoData.setVisibility(View.GONE);
        }
    }

    private void databaseData() {
        arrayTopic = BALTopic.getInstance().showFavorite();
    }
}
