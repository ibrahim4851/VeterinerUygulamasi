package com.example.veterineruygulamas.Activities.RestApi;

import com.example.veterineruygulamas.Activities.Adapters.DeleteAnswerModel;
import com.example.veterineruygulamas.Activities.Models.AnswersModel;
import com.example.veterineruygulamas.Activities.Models.AsiModel;
import com.example.veterineruygulamas.Activities.Models.AskQuestionModel;
import com.example.veterineruygulamas.Activities.Models.KampanyaModel;
import com.example.veterineruygulamas.Activities.Models.LoginModel;
import com.example.veterineruygulamas.Activities.Models.PetModel;
import com.example.veterineruygulamas.Activities.Models.RegisterPojo;

import java.util.List;

import retrofit2.Call;

public class ManagerAll extends BaseManager{
    private  static ManagerAll ourInstance = new ManagerAll();

    public  static synchronized ManagerAll getInstance()
    {
        return  ourInstance;
    }

    public Call<RegisterPojo> kayitOl(String mail , String kadi, String parola)
    {
        Call<RegisterPojo> x = getRestApi().registerUser(mail, kadi, parola);
        return  x ;
    }

    public Call<LoginModel> girisYap(String mail , String pass)
    {
        Call<LoginModel> x = getRestApi().loginUser(mail, pass);
        return  x ;
    }

    public Call<List<PetModel>> getPets(String id)
    {
        Call<List<PetModel>> x = getRestApi().getPets(id);
        return  x ;
    }

    public Call<AskQuestionModel> soruSor(String id , String soru)
    {
        Call<AskQuestionModel> x = getRestApi().soruSor(id, soru);
        return  x ;
    }

    public Call<List<AnswersModel>> getAnswers(String id)
    {
        Call<List<AnswersModel>> x = getRestApi().getAnswers(id);
        return  x ;
    }

    public Call<DeleteAnswerModel> deleteAnswer(String cevap , String soru)
    {
        Call<DeleteAnswerModel> x = getRestApi().deleteAnswer(cevap, soru);
        return  x ;
    }

    public Call<List<KampanyaModel>> getKampanya()
    {
        Call<List<KampanyaModel>> x = getRestApi().getKampanya();
        return  x ;
    }

    public Call<List<AsiModel>> getAsi(String id)
    {
        Call<List<AsiModel>> x = getRestApi().getAsi(id);
        return  x ;
    }

    public Call<List<AsiModel>> getGecmisAsi(String id, String pet_id)
    {
        Call<List<AsiModel>> x = getRestApi().getGecmisAsi(id, pet_id);
        return  x ;
    }
}
