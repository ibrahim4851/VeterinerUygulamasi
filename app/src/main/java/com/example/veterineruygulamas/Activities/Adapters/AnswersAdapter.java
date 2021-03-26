package com.example.veterineruygulamas.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineruygulamas.Activities.Models.AnswersModel;
import com.example.veterineruygulamas.Activities.RestApi.ManagerAll;
import com.example.veterineruygulamas.Activities.Utils.Warnings;
import com.example.veterineruygulamas.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder>{

    List<AnswersModel> list;
    Context context;

    public AnswersAdapter(List<AnswersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cevapitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cevapSoruText.setText("Soru : "+list.get(position).getSoru().toString());
        holder.cevapCevapText.setText("Cevap : "+list.get(position).getCevap().toString());

        holder.cevapSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteToService(list.get(position).getCevapid().toString(), list.get(position).getSoruid().toString(),position);
            }
        });
    }

    public void deleteToList(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void deleteToService(String cevapid, String soruid, int pos)
    {
        Call<DeleteAnswerModel> req = ManagerAll.getInstance().deleteAnswer(cevapid, soruid);
        req.enqueue(new Callback<DeleteAnswerModel>() {
            @Override
            public void onResponse(Call<DeleteAnswerModel> call, Response<DeleteAnswerModel> response) {
                if (response.body().isTf())
                {
                    if (response.isSuccessful())
                    {
                        Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                        deleteToList(pos);                    }
                }
                else
                {
                    Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteAnswerModel> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView cevapSoruText, cevapCevapText;
        Button cevapSilButon;
        //itemView ile listviewin her elemanı için layout ile oluşturduğumuz view tanımlaması işlemi gerçekleşir
        public ViewHolder(View itemView) {
            super(itemView);
            cevapSoruText = itemView.findViewById(R.id.cevapSoruText);
            cevapCevapText= itemView.findViewById(R.id.cevapCevapText);
            cevapSilButon = itemView.findViewById(R.id.cevapSilButon);
        }
    }

}
