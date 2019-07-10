package com.example.bloodbankmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Api.EventApi;
import Api.UserApi;
import Model.DeleteResponse;
import Model.Event;
import Model.UpdateEResponse;
import Model.UpdateResponse;
import Model.User2;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventAdminActivity extends AppCompatActivity {
    private EditText tven, tvea, tvest, tveet, tved;
    private Button btndele, btnupte, btncncl;
    String eventid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_admin);

        tven = findViewById(R.id.tvevname);
        tvea = findViewById(R.id.tvevadd);
        tvest = findViewById(R.id.tvest);
        tveet = findViewById(R.id.tveet);
        tved = findViewById(R.id.tvdat);
        btnupte = findViewById(R.id.btnupte);
        btndele = findViewById(R.id.btndele);
        btncncl = findViewById(R.id.btncncl);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tven.setText(bundle.getString("eventname"));
            tvea.setText(bundle.getString("eventaddress"));
            tvest.setText(bundle.getString("eventstarttime"));
            tveet.setText(bundle.getString("eventendtime"));
            tved.setText(bundle.getString("eventdate"));
            eventid=bundle.getString("eventid");
        }

        btncncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventAdminActivity.this, ViewEvent.class);
                startActivity(intent);
                finish();
            }
        });

        btnupte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventApi eventApi = Url.getInstance().create(EventApi.class);
                String eventname = bundle.getString("eventname");
                String eventaddress = bundle.getString("eventaddress");
                String eventstarttime = bundle.getString("eventstarttime");
                String eventendtime = bundle.getString("eventendtime");
                String eventdate = bundle.getString("eventdate");
                String eventid = bundle.getString("eventid");
                Event event = new Event(eventname, eventaddress, eventstarttime, eventendtime, eventdate, eventid);
                Call<UpdateEResponse> call = eventApi.updateEvent(event);

                call.enqueue(new Callback<UpdateEResponse>() {
                    @Override
                    public void onResponse(Call<UpdateEResponse> call, Response<UpdateEResponse> response) {
                        UpdateEResponse updateEResponse = response.body();
                        if (updateEResponse.getMessage().equals("success")) {
                            Toast.makeText(EventAdminActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EventAdminActivity.this, "err", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UpdateEResponse> call, Throwable t) {
                        Toast.makeText(EventAdminActivity.this, "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btndele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EventAdminActivity.this);
                builder.setTitle("Delete Event");
                builder.setMessage("Are you sure want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventApi eventApi = Url.getInstance().create(EventApi.class);
                        String eventid = bundle.getString("eventid");

                        Event event = new Event(eventid);
                        Call<DeleteResponse> listCall = eventApi.deleteEvent(event);

                        listCall.enqueue(new Callback<DeleteResponse>() {
                            @Override
                            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                                DeleteResponse deleteResponse = response.body();
                                if (deleteResponse.getMessage().equals("success")) {

                                    Toast.makeText(EventAdminActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(EventAdminActivity.this, AdminDashboard.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(EventAdminActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                                Toast.makeText(EventAdminActivity.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(EventAdminActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}
