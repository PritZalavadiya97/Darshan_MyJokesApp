package com.aswdc_projectname.myjokes.Design.adapter;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aswdc_projectname.myjokes.model.BeanCategory;
import com.aswdc_projectname.myjokes.Design.activity.ActivityTopicsList;
import com.aswdc_projectname.myjokes.R;

import java.util.ArrayList;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.CategoryHolder>{

    ArrayList<BeanCategory> beanCategory;
    AppCompatActivity context;
    LayoutInflater layoutInflater;

    public AdapterCategory(ArrayList<BeanCategory> beanCategory, AppCompatActivity context){
        this.beanCategory = beanCategory;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.activity_commonlist_design,null);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryHolder holder, int position) {
        holder.tvCategoryName.setText(beanCategory.get(position).getCategoryName());

        holder.tvCategoryPosition.setText(""+(position+1));
        holder.categoryID = beanCategory.get(position).getCategoryID();
        holder.CategoryTitle = beanCategory.get(position).getCategoryName();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityTopicsList.class);
                intent.putExtra("topicID",holder.categoryID);
                intent.putExtra("CategoryTitle",holder.CategoryTitle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanCategory.size();
    }

    public void filterList(ArrayList<BeanCategory> filteredList){
        beanCategory = filteredList;
        notifyDataSetChanged();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{

        TextView tvCategoryName;
        TextView tvCategoryPosition;
        int categoryID;
        String CategoryTitle;

        public CategoryHolder(View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tv_common_list);
            tvCategoryPosition = itemView.findViewById(R.id.tv_common_number);
        }
    }

}
