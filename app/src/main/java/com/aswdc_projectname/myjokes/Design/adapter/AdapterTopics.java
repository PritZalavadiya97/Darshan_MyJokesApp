package com.aswdc_projectname.myjokes.Design.adapter;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aswdc_projectname.myjokes.model.BeanTopics;
import com.aswdc_projectname.myjokes.Design.activity.ActivityWebView;
import com.aswdc_projectname.myjokes.R;
import com.aswdc_projectname.myjokes.util.Constant;

import java.util.ArrayList;


public class AdapterTopics extends RecyclerView.Adapter<AdapterTopics.TopicHolder>{

    ArrayList<BeanTopics> beanTopics;
    AppCompatActivity context;
    LayoutInflater layoutInflater;

    public AdapterTopics(ArrayList<BeanTopics> beanTopics, AppCompatActivity context){
        this.beanTopics = beanTopics;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.activity_commonlist_design,null);
        return new TopicHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TopicHolder holder, final int position) {
        holder.tvTopicName.setText(beanTopics.get(position).getTopicName());
        holder.tvTopicPosition.setText(""+(position+1));
        holder.TopicName = beanTopics.get(position).getTopicName();
        holder.htmlTopic = (beanTopics.get(position).getTopicWEB());
        holder.isFavorite = beanTopics.get(position).getIsFavorite();
        holder.id = beanTopics.get(position).getTopicID();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityWebView.class);
                intent.putExtra("HtmlPage",holder.htmlTopic);
                intent.putExtra("TopicName",holder.TopicName);
                intent.putExtra("TopicID",beanTopics.get(position).getTopicID());
                intent.putExtra(Constant.IS_FAVORITE, holder.isFavorite);
                intent.putExtra(Constant.TOPIC_ID, holder.id);
                context.startActivity(intent);
            }
        });
    }

    public void filterList(ArrayList<BeanTopics> filteredList){
        beanTopics = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return beanTopics.size();
    }

    class TopicHolder extends RecyclerView.ViewHolder{

        TextView tvTopicName;
        TextView tvTopicPosition;
        String htmlTopic;
        String TopicName;
        int id;
        int isFavorite;

        public TopicHolder(View itemView) {
            super(itemView);

            tvTopicPosition = itemView.findViewById(R.id.tv_common_number);
            tvTopicName = itemView.findViewById(R.id.tv_common_list);
        }
    }
}
