package com.example.veterineruygulamas.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineruygulamas.Activities.Models.KampanyaModel;
import com.example.veterineruygulamas.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KampanyaAdapter extends RecyclerView.Adapter<KampanyaAdapter.ViewHolder>{

    List<KampanyaModel> list;
    Context context;


    public KampanyaAdapter(List<KampanyaModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kampanyaitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.kampanyaBaslik.setText(list.get(position).getBaslik());
        holder.kampanyaIcerik.setText(list.get(position).getText());
        Picasso.get().load(list.get(position).getResim()).into(holder.kampanyaResim);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView kampanyaBaslik, kampanyaIcerik;
        ImageView kampanyaResim;
        //itemView ile listviewin her elemanı için layout ile oluşturduğumuz view tanımlaması işlemi gerçekleşir
        public ViewHolder(View itemView) {
            super(itemView);
            kampanyaBaslik = itemView.findViewById(R.id.kampanyaBaslik);
            kampanyaIcerik= itemView.findViewById(R.id.kampanyaIcerik);
            kampanyaResim = itemView.findViewById(R.id.kampanyaResim);
        }
    }

}
