package com.example.bloodbankmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import Api.UserApi;
import Model.DeleteResponse;
import Model.User2;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDetailsActivity extends AppCompatActivity {
    ImageView imgProfile;
    TextView tvName, tvPhoneNo, tvEmail, tvAddress, tvGender, tvBlood, tvdob, tvlast, tvid;
    Button btndeladmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details);
        imgProfile = findViewById(R.id.imgProfile2);
        tvlast = findViewById(R.id.tvlastnameadmin);
        tvName = findViewById(R.id.tvNameadmin);
        tvPhoneNo = findViewById(R.id.tvPhoneNoadmin);
        tvEmail = findViewById(R.id.tvEmailadmin);
        tvAddress = findViewById(R.id.tvAddressadmin);
        tvBlood = findViewById(R.id.tvBloodadmin);
        tvGender = findViewById(R.id.tvGenderadmin);
        tvdob = findViewById(R.id.tvDobadmin);
        btndeladmin = findViewById(R.id.btndeladmin);
        btndeladmin = findViewById(R.id.btndeladmin);


        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvName.setText(bundle.getString("firstname"));
            tvlast.setText(bundle.getString("lastname"));
            tvPhoneNo.setText(bundle.getString("phone"));
            tvEmail.setText(bundle.getString("email"));
            tvAddress.setText(bundle.getString("address"));
            tvGender.setText(bundle.getString("gender"));
            tvBlood.setText(bundle.getString("blood_group"));
            tvdob.setText(bundle.getString("dob"));
            String a = bundle.getString("imagename");
            StrictMode();


            try {
                URL url = new URL(a);
                imgProfile.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        btndeladmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminDetailsActivity.this);
                builder.setTitle("Delete User");
                builder.setMessage("Are you sure want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserApi userApi = Url.getInstance().create(UserApi.class);
                        String userid = bundle.getString("userid");

                        User2 user2 = new User2(userid);
                        Call<DeleteResponse> listCall = userApi.deleteUsers(user2);

                        listCall.enqueue(new Callback<DeleteResponse>() {
                            @Override
                            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                                DeleteResponse deleteResponse = response.body();
                                if (deleteResponse.getMessage().equals("success")) {

                                    Toast.makeText(AdminDetailsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(AdminDetailsActivity.this, ViewEvent.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(AdminDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                                Toast.makeText(AdminDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AdminDetailsActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


}

