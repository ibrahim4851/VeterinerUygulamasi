package com.example.veterineruygulamas.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineruygulamas.Activities.Models.PetModel;
import com.example.veterineruygulamas.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder>{

    List<PetModel> list;
    Context context;

    public PetsAdapter(List<PetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.petlistitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.petLayoutCinsName.setText("Pet Cinsi : "+list.get(position).getPetcins().toString());
        holder.petLayoutPetName.setText("Pet İsmi : "+list.get(position).getPetisim().toString());
        holder.petLayoutTurName.setText("Pet Türü : "+list.get(position).getPettur().toString());
        Picasso.get().load(list.get(position).getPetresim()).into(holder.petLayoutPetImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView petLayoutPetName, petLayoutCinsName, petLayoutTurName;
        CircleImageView petLayoutPetImage;
        //itemView ile listviewin her elemanı için layout ile oluşturduğumuz view tanımlaması işlemi gerçekleşir
        public ViewHolder(View itemView) {
            super(itemView);
            petLayoutPetName = itemView.findViewById(R.id.petLayoutPetName);
            petLayoutCinsName = itemView.findViewById(R.id.petLayoutCinsName);
            petLayoutTurName = itemView.findViewById(R.id.petLayoutTurName);
            petLayoutPetImage = itemView.findViewById(R.id.petLayoutPetImage);
        }
    }

}
