package com.example.bloodbankmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import Adapter.DetailsAdapter;
import Api.UserApi;
import Model.User;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminDashboard extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        recyclerView = findViewById(R.id.recyclerview);

        showAllUsers();


    }

    private void showAllUsers() {
        Retrofit retrofit = Url.getInstance();
        UserApi userApi = retrofit.create(UserApi.class);

        Call<List<User>> listCall = userApi.getUsers();

        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.body() != null) {
                    List<User> users = response.body();
                    DetailsAdapter detailsAdapter = new DetailsAdapter(AdminDashboard.this, users);
                    recyclerView.setAdapter(detailsAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AdminDashboard.this));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
