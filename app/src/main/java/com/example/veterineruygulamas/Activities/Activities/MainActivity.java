package com.example.veterineruygulamas.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.veterineruygulamas.Activities.Fragments.HomeFragment;
import com.example.veterineruygulamas.Activities.Utils.ChangeFragments;
import com.example.veterineruygulamas.Activities.Utils.GetSharedPreferences;
import com.example.veterineruygulamas.R;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences getSharedPreferences;
    private GetSharedPreferences preferences;
    private ChangeFragments changeFragments;
    private ImageView anasayfaButon, aramaYapButon, cikisYapButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        change();
        tanimla();
        kontrol();
        action();
    }

    public void change()
    {
        Fragment newFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLayout, newFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    public void tanimla()
    {
        preferences = new GetSharedPreferences(MainActivity.this);
        getSharedPreferences = preferences.getSession();
        anasayfaButon = findViewById(R.id.anasayfaButon);
        aramaYapButon = findViewById(R.id.aramaYapButon);
        cikisYapButon = findViewById(R.id.cikisYapButon);
    }

    public void kontrol()
    {
        Log.i("sharedSonuc: ","girdi");
        if (getSharedPreferences.getString("id",null)==null && getSharedPreferences.getString("username",null)==null
        && getSharedPreferences.getString("mailAdres",null)==null)
        {
            Intent intent = new Intent(MainActivity.this, GirisYapActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void action()
    {
        anasayfaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });
        cikisYapButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetSharedPreferences getSharedPreferences = new GetSharedPreferences(MainActivity.this);
                getSharedPreferences.deleteToSession();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        aramaYapButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:05356670986"));
                startActivity(intent);
            }
        });
    }

}