package com.example.bloodbankmanagementsystem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import Api.EventApi;
import Api.UserApi;
import CreateChannel.CreateChannel;
import Fragments.RegisterPage;
import Model.Event;
import Model.EventResponse;
import Model.RegisterResponse;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText etname, etaddress;
    private TextView etstime, etetime, etdate;
    private Button btnaddev, btnbck;

    NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddEvent.this, ViewEvent.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        etname = findViewById(R.id.eteventname);
        etaddress = findViewById(R.id.eteventaddress);
        etstime = findViewById(R.id.eteventstarttime);
        etetime = findViewById(R.id.eteventendtime);
        etdate = findViewById(R.id.eteventdate);
        btnaddev = findViewById(R.id.btnAddev);
        btnbck=findViewById(R.id.btnbck);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        CreateChannel channel = new CreateChannel(this);

        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddEvent.this, AddEvent.this, year, month, day);
                datePickerDialog.show();
            }
        });

        etstime.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Calendar mcurrentTime = Calendar.getInstance();
                                           int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                           int minute = mcurrentTime.get(Calendar.MINUTE);
                                           TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                                               @Override
                                               public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                   etstime.setText(hourOfDay + ":" + minute);
                                               }
                                           }, hour, minute, false);
                                           timePickerDialog.show();
                                       }
                                   });
        etetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etetime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        btnbck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddEvent.this,ViewEvent.class);
                startActivity(intent);
                finish();
            }
        });
        btnaddev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {
                    EventApi eventApi = Url.getInstance().create(EventApi.class);
                    String eventid = "";
                    String eventname = etname.getText().toString();
                    String eventaddress = etaddress.getText().toString();
                    String eventstarttime = etstime.getText().toString();
                    String eventendtime = etetime.getText().toString();
                    String eventdate = etdate.getText().toString();

                    Event event = new Event(eventid, eventname, eventaddress, eventstarttime, eventendtime, eventdate);
                    Call<RegisterResponse> eventResponseCall = eventApi.addEvent(event);

                    eventResponseCall.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            RegisterResponse registerResponse = response.body();
                            if (registerResponse.getMessage().equals("error")) {
                                Toast.makeText(AddEvent.this, "Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddEvent.this, "Succesfully added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddEvent.this, AdminDashboard.class);
                                startActivity(intent);
                                finish();
                                DisplayNotification1();

                            }
                        }


                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {

                        }
                    });
                }
            }



            private boolean isEmpty() {
                if (TextUtils.isEmpty((etname.getText().toString()))) {
                    etname.setError("Please enter Event Name");
                    etname.requestFocus();
                    return true;
                } else if (TextUtils.isEmpty((etaddress.getText().toString()))) {
                    etaddress.setError("Please enter Address");
                    etaddress.requestFocus();
                    return true;
                } else if (TextUtils.isEmpty((etstime.getText().toString()))) {
                    etstime.setError("Please enter Start time");
                    etstime.requestFocus();
                    return true;
                } else if (TextUtils.isEmpty((etetime.getText().toString()))) {
                    etetime.setError("Please enter End time");
                    etetime.requestFocus();
                    return true;
                } else if (TextUtils.isEmpty((etdate.getText().toString()))) {
                    etdate.setError("Please enter Date");
                    etdate.requestFocus();
                    return true;
                }
                return false;
            }

        });
    }


    int id = 1;
    private void DisplayNotification1(){
        Notification notification = new NotificationCompat.Builder(this,CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.blood_logo)
                .setContentTitle("Booked")
                .setContentText("Hotel has been booked on"+btnaddev)
                .setCategory(NotificationCompat.CATEGORY_SYSTEM)
                .build();


        notificationManagerCompat.notify(id, notification);
        id++;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String mon = "";
        switch (month) {
            case 0:
                mon = "jan";
                break;
            case 1:
                mon = "feb";
                break;
            case 2:
                mon = "mar";
                break;
            case 3:
                mon = "Apr";
                break;
            case 4:
                mon = "May";
                break;
            case 5:
                mon = "Jun";
                break;
            case 6:
                mon = "Jul";
                break;
            case 7:
                mon = "Aug";
                break;
            case 8:
                mon = "Sep";
                break;
            case 9:
                mon = "Oct";
                break;
            case 10:
                mon = "Nov";
                break;
            case 11:
                mon = "Dec";
                break;
        }
        String date = "" + mon + "," + dayOfMonth + "-" + year;
        etdate.setText(date);
    }
}
