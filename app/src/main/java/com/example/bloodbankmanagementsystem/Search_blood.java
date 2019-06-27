package com.example.bloodbankmanagementsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import Adapter.DetailsAdapter;
import Api.UserApi;
import Model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Url.Url;

@SuppressWarnings("ALL")
public class Search_blood extends AppCompatActivity {
    private RecyclerView recyclerView;
//    private EditText etsearchbyblood;
//    private Button btnsearchbyblood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);

        recyclerView = findViewById(R.id.recyclerview);

        showUsers();
    }

    private void showUsers() {
        Retrofit retrofit = Url.getInstance();
        UserApi userApi = retrofit.create(UserApi.class);

        Call<List<User>> listCall=userApi.getUsers();
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {


                Toast.makeText(Search_blood.this, "load Users", Toast.LENGTH_SHORT).show();
                List<User> users = response.body();
                DetailsAdapter detailsAdapter = new DetailsAdapter(Search_blood.this, users);
                recyclerView.setAdapter(detailsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(Search_blood.this));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }
}

