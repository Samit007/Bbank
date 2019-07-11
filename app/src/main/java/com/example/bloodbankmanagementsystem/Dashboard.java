package com.example.bloodbankmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import Adapter.EventAdapter;
import Api.EventApi;
import Model.Event;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Dashboard extends AppCompatActivity {
    private Button btnupt;
    private RecyclerView recyclerView;
    private SensorManager sensorManager;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setTitle("Log out");
        builder.setMessage("Are you sure want to Log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Dashboard.this, ViewPagerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Dashboard.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btnupt=findViewById(R.id.btnupt);
        recyclerView = findViewById(R.id.recyclerview);
        proximity();

        showEvents();
        btnupt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,UpdateDashboard.class);
                startActivity(intent);
            }
        });
    }

    private void showEvents() {
        Retrofit retrofit = Url.getInstance();
        EventApi eventApi = retrofit.create(EventApi.class);

        Call<List<Event>> listCall = eventApi.getEvent();
        listCall.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                Toast.makeText(Dashboard.this, "load Events", Toast.LENGTH_SHORT).show();
                List<Event> events = response.body();
                EventAdapter eventAdapter = new EventAdapter(Dashboard.this, events);
                recyclerView.setAdapter(eventAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(Dashboard.this));
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

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
