package com.example.veterineruygulamas.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veterineruygulamas.Activities.Models.RegisterPojo;
import com.example.veterineruygulamas.Activities.RestApi.ManagerAll;
import com.example.veterineruygulamas.Activities.Utils.Warnings;
import com.example.veterineruygulamas.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitOlActivity extends AppCompatActivity {

    private Button kayitOlButton;
    private EditText registerMailAdress, registerUserName, registerPassword;
    private TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        tanimla();
        registerToUser();
        changeActivity();
    }

    public void tanimla() {
        registerMailAdress = findViewById(R.id.registerMailAdress);
        registerUserName = findViewById(R.id.registerUserName);
        registerPassword = findViewById(R.id.registerPassword);
        kayitOlButton = findViewById(R.id.kayitOlButon);
        registerText = findViewById(R.id.registerText);
    }

    public void registerToUser()
    {
        kayitOlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = registerMailAdress.getText().toString();
                String userName = registerUserName.getText().toString();
                String password = registerPassword.getText().toString();
                register(mail, userName, password);
            }
        });
    }

    public void register(String userMailAdres, String userName, String userPass)
    {
        Call<RegisterPojo> req = ManagerAll.getInstance().kayitOl(userMailAdres, userName, userPass);
        req.enqueue(new Callback<RegisterPojo>() {
            @Override
            public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                if (response.body().isTf()){
                    Toast.makeText(getApplicationContext(), response.body().getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(KayitOlActivity.this, GirisYapActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(KayitOlActivity.this, response.body().getText(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterPojo> call, Throwable t) {
                Toast.makeText(KayitOlActivity.this, Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changeActivity()
    {
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KayitOlActivity.this, GirisYapActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}