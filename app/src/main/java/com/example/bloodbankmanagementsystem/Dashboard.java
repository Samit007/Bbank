package com.example.bloodbankmanagementsystem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Api.UserApi;
import Fragments.RegisterPage;
import Model.RegisterResponse;
import Model.UpdateResponse;
import Model.User;
import Url.Url;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends Activity implements DatePickerDialog.OnDateSetListener {
    private EditText etFirstname, etLastname, etEmail, etAddres, etUsername, etPassword, etPassword2;
    private TextView etDOB, tvphone;
    private Spinner etGender, etBloodGroup;
    private Button btnup, btndel;
    private ImageView btnImage;
    private String imagePath, imageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        etFirstname = findViewById(R.id.upFirstname);
        etLastname = findViewById(R.id.upLastname);
        etUsername = findViewById(R.id.upUsername);
        etPassword = findViewById(R.id.upPassword);
        etPassword2 = findViewById(R.id.upPassword2);
        etEmail = findViewById(R.id.upEmail);
        etAddres = findViewById(R.id.upAddress);
        etGender = findViewById(R.id.upGender);
        etDOB = findViewById(R.id.upDOB);
        etBloodGroup = findViewById(R.id.upBloodgroup);
        btnImage = findViewById(R.id.upImg);
        btnup = findViewById(R.id.btnUp);
        btndel = findViewById(R.id.btnDel);
        tvphone = findViewById(R.id.tvphone);
        btnImage = findViewById(R.id.upImg);

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        String ph = sharedPreferences.getString("phone", "");
        tvphone.setText(ph);


        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Dashboard.this, Dashboard.this, year, month, day);
                dialog.show();
            }
        });


        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {
                    pwcheck();
                }
            }

            private void pwcheck() {
                String pass = etPassword.getText().toString();
                String pass2 = etPassword.getText().toString();
                if (pass.equals(pass2)) {
                    update();
                }
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
        return;
    }

    private void BrowseImage() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Select an image", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        imagePath = getRealPathFromUri(uri);
        previewImage(imagePath);
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, uri,
                projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void previewImage(String ivImag) {
        File imgFile = new File(ivImag);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            btnImage.setImageBitmap(myBitmap);
        }
    }

    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void SaveImageOnly() {
        File file = new File(imagePath);

        RequestBody requestBody = RequestBody.create
                (MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData
                ("imageFile", file.getName(), requestBody);
        UserApi userApi = Url.getInstance().create(UserApi.class);
        Call<ImageResponse> responseCall = userApi.uploadImage(body);

        StrictMode();
        try {
            Response<ImageResponse> imageResponseResponse = responseCall.execute();
            imageName = imageResponseResponse.body().getFilename();
        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



            private void update() {
                SaveImageOnly();
                UserApi userApi = Url.getInstance().create(UserApi.class);
                final String firstname = etFirstname.getText().toString();
                String lastname = etLastname.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                String phone = tvphone.getText().toString();
                String address = etAddres.getText().toString();
                String date_of_birth = etDOB.getText().toString();
                String gender = etGender.getSelectedItem().toString();
                String blood_group = etBloodGroup.getSelectedItem().toString();
                String userimagename = imageName;

                User user = new User(firstname, lastname, username, password, email, phone, address, gender, blood_group, date_of_birth, userimagename);
                Call<UpdateResponse> listCall = userApi.updateUsers(user);

                listCall.enqueue(new Callback<UpdateResponse>() {
                    @Override
                    public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                        UpdateResponse updateResponse = response.body();
                        if (updateResponse.getMessage().equals("success")) {
                            Toast.makeText(Dashboard.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateResponse> call, Throwable t) {

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
        etDOB.setText(date);
    }
}

