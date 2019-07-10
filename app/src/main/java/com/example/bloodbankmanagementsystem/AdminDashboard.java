package com.example.bloodbankmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import Adapter.AdminDetailsAdapter;
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
    private Button btnviewevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        recyclerView = findViewById(R.id.recyclerview);
        btnviewevent=findViewById(R.id.btnViewevent);
        btnviewevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this,ViewEvent.class);
                startActivity(intent);
            }
        });

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
                    AdminDetailsAdapter adminDetailsAdapter = new AdminDetailsAdapter(AdminDashboard.this, users);
                    recyclerView.setAdapter(adminDetailsAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AdminDashboard.this));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
