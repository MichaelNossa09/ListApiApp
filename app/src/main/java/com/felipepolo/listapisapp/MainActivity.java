package com.felipepolo.listapisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.felipepolo.listapisapp.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;

    private String getSchoolsWithA = "https://api.schooldigger.com/v1.2/autocomplete/schools?q=A&appID=175f03b6&appKey=47dcbc1bc2fae1804dce60c33469b2d2";
    private String getSchoolsCountInCaUrl = "https://api.schooldigger.com/v1.2/schools?st=CA&appID=175f03b6&appKey=47dcbc1bc2fae1804dce60c33469b2d2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        setContentView(binding.getRoot());


        binding.bgetApiList.setOnClickListener(this);
        binding.bGetMetrics.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bgetApiList:
                getSchoolsWithA();
                break;
            case R.id.bGetMetrics:
                getNumberOfSchoolsInCa();
                break;
        }
    }

    public void getSchoolsWithA() {
        RequestQueue cola = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, getSchoolsWithA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new  Gson();
                SchoolModel model = gson.fromJson(response, SchoolModel.class);
                ArrayList<String> lista = new  ArrayList();
                for (int i = 0; i < model.schoolMatches.size(); i++){
                    lista.add(model.schoolMatches.get(i).schoolName);
                }
                binding.lvSchools.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, lista));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }
        });
        cola.add(solicitud);
    }

    public void getNumberOfSchoolsInCa() {
        RequestQueue cola = Volley.newRequestQueue(this);
        StringRequest solicitud = new StringRequest(Request.Method.GET, getSchoolsCountInCaUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new  Gson();
                SchoolMetrix metrix = gson.fromJson(response, SchoolMetrix.class);
                binding.tvSchools.setText("Hay " + metrix.numberOfSchools + " EN CALIFORNIA");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }
        });
        cola.add(solicitud);
    }

}