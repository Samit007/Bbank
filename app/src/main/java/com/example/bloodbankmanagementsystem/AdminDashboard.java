package com.example.bloodbankmanagementsystem;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        recyclerView = findViewById(R.id.recyclerview);
        btnviewevent=findViewById(R.id.btnViewevent);
        proximity();
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
    private void proximity() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        SensorEventListener proxilistener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0] <= 2) {
                    Intent intent=new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(proxilistener,sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }
}
