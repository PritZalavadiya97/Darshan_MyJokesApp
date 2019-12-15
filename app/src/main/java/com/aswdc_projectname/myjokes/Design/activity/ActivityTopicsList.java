package com.aswdc_projectname.myjokes.Design.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.aswdc_projectname.myjokes.Design.adapter.AdapterTopics;
import com.aswdc_projectname.myjokes.OnTextChange;
import com.aswdc_projectname.myjokes.bal.BALTopic;
import com.aswdc_projectname.myjokes.model.BeanTopics;
import com.aswdc_projectname.myjokes.R;


import java.util.ArrayList;


public class ActivityTopicsList extends BaseActivity implements OnTextChange {

    RecyclerView recyclerView;
    ArrayList<BeanTopics> arrayTopic;
    AdapterTopics adapterTopics;
    int categoryID;
    String categoryTitle;
    EditText searchBar;
    TextView tvNoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_commonlist);
        super.onCreate(savedInstanceState);
        removeElevation();
        getTopicID();
        databaseData();
        setDataToRecyclerView();
        searchBarCode(searchBar, this);

    }



    private void databaseData() {
        arrayTopic = BALTopic.getInstance().selectAllTopic(categoryID);
    }

    private void getTopicID() {

        Intent mIntent = getIntent();
        categoryID = mIntent.getIntExtra(getString(R.string.TopicID), 10);
        Log.d("topicID",""+categoryID);
        categoryTitle = mIntent.getStringExtra(getString(R.string.CategoryTitle));
        setTitle(categoryTitle);

    }

    @Override
    public void onChange(String filterValue) {
        filter(filterValue);
    }

    @Override
    public void initVariables() {
        searchBar = findViewById(R.id.et_search_bar);
        recyclerView = findViewById(R.id.rcvList);
        tvNoData = findViewById(R.id.noData);
    }

    @Override
    public void bindWidgetEvents() {

    }

    private void filter(String searchText){
        ArrayList<BeanTopics> filterdList = new ArrayList<>();

        for (BeanTopics item : arrayTopic){
            if (item.getTopicName().toLowerCase().contains(searchText.toLowerCase())){
                filterdList.add(item);
            }
        }

        adapterTopics.filterList(filterdList);
    }

    private void setDataToRecyclerView() {

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        adapterTopics = new AdapterTopics(arrayTopic, this);
        recyclerView.setAdapter(adapterTopics);

    }


}
