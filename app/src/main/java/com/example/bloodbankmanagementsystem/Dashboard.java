package com.example.bloodbankmanagementsystem;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import Api.UserApi;
import Model.RegisterResponse;
import Model.User;
import Url.Url;
import retrofit2.Call;

public class Dashboard extends Activity {
    private EditText etFirstname,etLastname, etPhoneNo, etEmail, etAddres, etUsername, etPassword, etPassword2;
    private TextView etDOB;
    private Spinner etGender, etBloodGroup;
    private Button btnup,btndel;
    private ImageView btnImage;
    private String imagePath, imageName;

    public Dashboard(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        etFirstname = findViewById(R.id.upFirstname);
        etLastname = findViewById(R.id.upLastname);
        etUsername = findViewById(R.id.upUsername);
        etPassword = findViewById(R.id.upPassword);
        etPassword2 = findViewById(R.id.upPassword2);
        etPhoneNo = findViewById(R.id.upPhoneNo);
        etEmail = findViewById(R.id.upEmail);
        etAddres = findViewById(R.id.upAddress);
        etGender = findViewById(R.id.upGender);
        etDOB = findViewById(R.id.upDOB);
        etBloodGroup = findViewById(R.id.upBloodgroup);
        btnImage = findViewById(R.id.upImg);
        btnup=findViewById(R.id.btnUp);
        btndel=findViewById(R.id.btnDel);

        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {
                    update();
                }
            }

            private void update() {
                UserApi userApi= Url.getInstance().create(UserApi.class);
                final String firstname = etFirstname.getText().toString();
                String lastname = etLastname.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhoneNo.getText().toString();
                String address = etAddres.getText().toString();
                String date_of_birth = etDOB.getText().toString();
                String gender = etGender.getSelectedItem().toString();
                String blood_group = etBloodGroup.getSelectedItem().toString();

                User user= new User(firstname,lastname,username,password,email,phone,address,gender,blood_group,date_of_birth);
                Call<List<User>> listCall = userApi.updateUsers(user);
            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty((etFirstname.getText().toString()))) {
            etFirstname.setError("Please enter First Name");
            etFirstname.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((etLastname.getText().toString()))) {
            etLastname.setError("Please enter Last Name");
            etLastname.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((etUsername.getText().toString()))) {
            etUsername.setError("Please enter Username");
            etUsername.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((etPassword.getText().toString()))) {
            etPassword.setError("Please enter Password");
            etPassword.requestFocus();
            return true;
        }else if (TextUtils.isEmpty((etPassword2.getText().toString()))) {
            etPassword2.setError("Please enter Password");
            etPassword2.requestFocus();
            return true;
        }else if (TextUtils.isEmpty((etPhoneNo.getText().toString()))) {
            etPhoneNo.setError("Please enter Phone number");
            etPhoneNo.requestFocus();
            return true;
        }else if (TextUtils.isEmpty((etEmail.getText().toString()))) {
            etEmail.setError("Please enter Email id");
            etEmail.requestFocus();
            return true;
        }else if (TextUtils.isEmpty((etAddres.getText().toString()))) {
            etAddres.setError("Please enter Address");
            etAddres.requestFocus();
            return true;
        }
        return false;
    }
}
