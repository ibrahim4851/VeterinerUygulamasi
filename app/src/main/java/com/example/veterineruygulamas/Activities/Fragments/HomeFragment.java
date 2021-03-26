package com.example.veterineruygulamas.Activities.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.veterineruygulamas.Activities.Adapters.AnswersAdapter;
import com.example.veterineruygulamas.Activities.Models.AnswersModel;
import com.example.veterineruygulamas.Activities.Models.AskQuestionModel;
import com.example.veterineruygulamas.Activities.Models.KampanyaModel;
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

import static com.example.veterineruygulamas.Activities.RestApi.ManagerAll.getInstance;


public class HomeFragment extends Fragment {

    public View view;
    private LinearLayout petlerimLayout, soruSorLinearLayout, cevapLayout, kampanyaLinearLayout, asiTakipLayout,
            sanalKarneLayout;
    private ChangeFragments changeFragments;
    private GetSharedPreferences getSharedPreferences;
    private String id;
    private AnswersAdapter answersAdapter;
    private List<AnswersModel> answersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        tanimla();
        action();
        return view;
    }

    public void tanimla() {
        petlerimLayout = view.findViewById(R.id.petlerimLayout);
        cevapLayout = view.findViewById(R.id.cevapLayout);
        soruSorLinearLayout = view.findViewById(R.id.soruSorLinearLayout);
        kampanyaLinearLayout = view.findViewById(R.id.kampanyaLinearLayout);
        sanalKarneLayout = view.findViewById(R.id.sanalKarneLayout);
        asiTakipLayout = view.findViewById(R.id.asiTakipLayout);
        changeFragments = new ChangeFragments(getContext());
        getSharedPreferences = new GetSharedPreferences(getActivity());
        id = getSharedPreferences.getSession().getString("id", null);
        answersList = new ArrayList<>();
    }

    public void action() {
        petlerimLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new UserPetsFragment());
            }
        });
        soruSorLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuestionAlert();
            }
        });
        cevapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAnswers(id);
            }
        });
        kampanyaLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new KampanyaFragment());
            }
        });
        asiTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new AsiFragment());
            }
        });
        sanalKarneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new SanalKarnePetler());
            }
        });
    }

    public void openQuestionAlert() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sorusoralertlayout, null);

        final EditText soruSorEditText = view.findViewById(R.id.soruSorEditText);
        Button soruGonderButton = view.findViewById(R.id.soruGonderButton);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        AlertDialog alertDialog = alert.create();
        soruGonderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soru = soruSorEditText.getText().toString();
                soruSorEditText.setText("");
                alertDialog.cancel();
                askQuestion(id, soru, alertDialog);
            }
        });
        alertDialog.show();
    }

    public void askQuestion(String mus_id, String text, AlertDialog alr) {
        Call<AskQuestionModel> req = ManagerAll.getInstance().soruSor(mus_id, text);
        req.enqueue(new Callback<AskQuestionModel>() {
            @Override
            public void onResponse(Call<AskQuestionModel> call, Response<AskQuestionModel> response) {
                if (response.body().isTf()) {
                    Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                    alr.cancel();
                } else {
                    Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AskQuestionModel> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openAnswersAlert() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.cevapalertlayout, null);

        RecyclerView cevapRecyclerView = view.findViewById(R.id.cevapRecyclerView);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        AlertDialog alertDialog = alert.create();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        cevapRecyclerView.setLayoutManager(layoutManager);
        cevapRecyclerView.setAdapter(answersAdapter);
        alertDialog.show();
    }

    public void getAnswers(String mus_id)
    {
        Call<List<AnswersModel>> req = ManagerAll.getInstance().getAnswers(mus_id);
        req.enqueue(new Callback<List<AnswersModel>>() {
            @Override
            public void onResponse(Call<List<AnswersModel>> call, Response<List<AnswersModel>> response) {
                if (response.body().get(0).isTf())
                {
                    if (response.isSuccessful())
                    {
                        answersList = response.body();
                        answersAdapter = new AnswersAdapter(answersList,getContext());
                        openAnswersAlert();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Herhangi bir cevap yok", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AnswersModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }


}