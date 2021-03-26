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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @FormUrlEncoded
    @POST("kayitol.php")
    Call<RegisterPojo> registerUser(@Field("mailAdres") String mailAdres, @Field("kadi") String kadi, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("girisyap.php")
    Call<LoginModel> loginUser(@Field("mailAdres") String mailAdres, @Field("sifre") String pass);

    @FormUrlEncoded
    @POST("petlerim.php")
    Call<List<PetModel>> getPets(@Field("musid") String mus_id);

    @FormUrlEncoded
    @POST("sorusor.php")
    Call<AskQuestionModel> soruSor(@Field("id") String id, @Field("soru") String soru);

    @FormUrlEncoded
    @POST("cevaplar.php")
    Call<List<AnswersModel>> getAnswers(@Field("mus_id") String mus_id);

    @FormUrlEncoded
    @POST("cevapsil.php")
    Call<DeleteAnswerModel> deleteAnswer(@Field("cevap") String cevapid, @Field("soru") String soruid);

    @GET("kampanya.php")
    Call<List<KampanyaModel>> getKampanya();

    @FormUrlEncoded
    @POST("asitakip.php")
    Call<List<AsiModel>> getAsi(@Field("id") String id);

    @FormUrlEncoded
    @POST("gecmisasi.php")
    Call<List<AsiModel>> getGecmisAsi(@Field("id") String id, @Field("petid") String pet_id);
}
