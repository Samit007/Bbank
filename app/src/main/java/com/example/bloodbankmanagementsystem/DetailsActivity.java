package com.example.bloodbankmanagementsystem;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity {
    ImageView imgProfile;
    TextView tvName, tvPhoneNo, tvEmail, tvAddress, tvGender, tvBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        imgProfile=findViewById(R.id.imgProfile);
        tvName = findViewById(R.id.tvName);
        tvPhoneNo = findViewById(R.id.tvPhoneNo);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        tvBlood = findViewById(R.id.tvBlood);
        tvGender = findViewById(R.id.tvGender);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvName.setText(bundle.getString("firstname"));
            tvPhoneNo.setText(bundle.getString("phone"));
            tvEmail.setText(bundle.getString("email"));
            tvAddress.setText(bundle.getString("address"));
            tvGender.setText(bundle.getString("gender"));
            tvBlood.setText(bundle.getString("blood_group"));
            String a = bundle.getString("imagename");
            StrictMode();

            try{
                URL url = new URL(a);
                imgProfile.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}

