//Reza Merati S1711794
package com.example.trafficinfo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficinfo.R;
import com.example.trafficinfo.models.TrafficInfoModel;
import com.example.trafficinfo.ui.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class TrafficAdapter extends RecyclerView.Adapter<TrafficAdapter.viewHolder> {

    private List<TrafficInfoModel> dataHolder;
    private List<TrafficInfoModel> myList;
    Context context;

    public TrafficAdapter(List<TrafficInfoModel> dataHolder, Context context) {
        this.dataHolder = dataHolder;
        this.myList = new ArrayList<>(dataHolder);
        this.context=context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        String title = dataHolder.get(position).getTitle();
        String description = dataHolder.get(position).getDescription();
        String link = dataHolder.get(position).getLink();
        String latLng = dataHolder.get(position).getLatLng();
        String pubDate = dataHolder.get(position).getPubDate();

        holder.title.setText(title);
        holder.desc.setText(description);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("link", link);
                intent.putExtra("latLng", latLng);
                intent.putExtra("pubDate", pubDate);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public void filterList(ArrayList<TrafficInfoModel> filteredList) {
        dataHolder = filteredList;
        notifyDataSetChanged();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView title, desc;
        View view;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.tv_title);
            desc=itemView.findViewById(R.id.tv_description);
            view=itemView;
        }
    }
}
