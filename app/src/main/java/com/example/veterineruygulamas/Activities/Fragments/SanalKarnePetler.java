package com.example.veterineruygulamas.Activities.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.veterineruygulamas.Activities.Adapters.PetsAdapter;
import com.example.veterineruygulamas.Activities.Adapters.SanalKarnePetAdapter;
import com.example.veterineruygulamas.Activities.Models.PetModel;
import com.example.veterineruygulamas.Activities.RestApi.ManagerAll;
import com.example.veterineruygulamas.Activities.Utils.ChangeFragments;
import com.example.veterineruygulamas.Activities.Utils.GetSharedPreferences;
import com.example.veterineruygulamas.Activities.Utils.Warnings;
import com.example.veterineruygulamas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SanalKarnePetler extends Fragment {

    private View view;
    private RecyclerView sanalKarnePetler;
    private List<PetModel> petList;
    private ChangeFragments changeFragments;
    private String mus_id;
    private GetSharedPreferences getSharedPreferences;
    private SanalKarnePetAdapter sanalKarnePetAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_pets, container, false);
        tanimla();
        getPets(mus_id);
        return view;
    }

    public void tanimla()
    {
        petList = new ArrayList<>();
        sanalKarnePetler = view.findViewById(R.id.petListRecyclerView);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(),1);
        sanalKarnePetler.setLayoutManager(mng);
        changeFragments = new ChangeFragments(getContext());
        getSharedPreferences = new GetSharedPreferences(getActivity());
        mus_id = getSharedPreferences.getSession().getString("id",null);
    }

    public void getPets(String mus_id)
    {
        Call<List<PetModel>> req = ManagerAll.getInstance().getPets(mus_id);
        req.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
                if (response.body().get(0).isTf())
                {
                    petList = response.body();
                    sanalKarnePetAdapter = new SanalKarnePetAdapter(petList, getContext());
                    sanalKarnePetler.setAdapter(sanalKarnePetAdapter);

                }
                else
                {
                    Toast.makeText(getContext(), "Sistemde kayıtlı hayvanınız bulunmamaktadır", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
            }
        });
    }
}