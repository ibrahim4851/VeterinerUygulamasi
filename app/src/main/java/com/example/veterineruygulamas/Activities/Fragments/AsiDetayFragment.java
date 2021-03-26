package com.example.veterineruygulamas.Activities.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.veterineruygulamas.Activities.Adapters.SanalKarneGecmisAsiAdapter;
import com.example.veterineruygulamas.Activities.Models.AsiModel;
import com.example.veterineruygulamas.Activities.RestApi.ManagerAll;
import com.example.veterineruygulamas.Activities.Utils.ChangeFragments;
import com.example.veterineruygulamas.Activities.Utils.GetSharedPreferences;
import com.example.veterineruygulamas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AsiDetayFragment extends Fragment {

    private View view;
    private String musid, petId;
    private GetSharedPreferences getSharedPreferences;
    private RecyclerView asiDetayRecView;
    private SanalKarneGecmisAsiAdapter adapter;
    private List<AsiModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_asi_detay, container, false);
        tanimla();
        getGecmisAsi();
        return view;
    }

    public void tanimla()
    {
        petId = getArguments().getString("petid").toString();
        getSharedPreferences = new GetSharedPreferences(getActivity());
        musid = getSharedPreferences.getSession().getString("id",null);
        asiDetayRecView = view.findViewById(R.id.asiDetayRecView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        asiDetayRecView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
    }

    public void getGecmisAsi()
    {
        Call<List<AsiModel>> req = ManagerAll.getInstance().getGecmisAsi("30",petId);
        req.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {
                if (response.body().get(0).isTf())
                {
                    list = response.body();
                    adapter = new SanalKarneGecmisAsiAdapter(list,getContext());
                    asiDetayRecView.setAdapter(adapter);
                    Toast.makeText(getContext(), "Petinize ait "+list.size()+
                                    " adet geçmişte yapılan aşı bulunmaktadır."
                            , Toast.LENGTH_LONG).show();
                }
                else
                {
                    ChangeFragments changeFragments = new ChangeFragments(getContext());
                    changeFragments.change(new SanalKarnePetler());
                    Toast.makeText(getContext(), "Petinize ait geçmişte yapılan bir aşı verisi bulunmamaktadır.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {

            }
        });
    }

}