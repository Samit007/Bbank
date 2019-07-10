package com.example.bloodbankmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import Adapter.AdminDetailsAdapter;
import Adapter.EventAdapter;
import Adapter.EventAdminAdapter;
import Api.EventApi;
import Api.UserApi;
import Model.Event;
import Model.User;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewEvent extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        recyclerView = findViewById(R.id.recyclerview);
        btnview=findViewById(R.id.btnview);
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewEvent.this,AddEvent.class);
                startActivity(intent);
            }
        });
        showEvents();
    }

    private void showEvents() {
            Retrofit retrofit = Url.getInstance();
            EventApi eventApi = retrofit.create(EventApi.class);

            Call<List<Event>> listCall = eventApi.getEvent();
            listCall.enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    Toast.makeText(ViewEvent.this, "load Events", Toast.LENGTH_SHORT).show();
                    List<Event> events = response.body();
                    EventAdminAdapter eventAdminAdapter = new EventAdminAdapter(ViewEvent.this, events);
                    recyclerView.setAdapter(eventAdminAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewEvent.this));
                }
                @Override
                public void onFailure(Call<List<Event>> call, Throwable t) {

                }
            });
    }
}

