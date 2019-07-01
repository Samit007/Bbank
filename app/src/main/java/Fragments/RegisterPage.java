package Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbankmanagementsystem.ImageResponse;
import com.example.bloodbankmanagementsystem.R;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import Api.UserApi;
import Model.RegisterResponse;
import Model.User;
import Url.Url;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class RegisterPage extends Fragment implements DatePickerDialog.OnDateSetListener {
    private EditText etFirstname,etLastname, etPhoneNo, etEmail, etAddres, etUsername, etPassword, etPassword2;
    private TextView etDOB;
    private Spinner etGender, etBloodGroup;
    private Button btnRegister;
    private ImageView btnImage;
    private String imagePath, imageName;

    public RegisterPage() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register_page, container, false);
        etFirstname = view.findViewById(R.id.etFirstname);
        etLastname = view.findViewById(R.id.etLastname);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etPassword2 = view.findViewById(R.id.etPassword2);
        etPhoneNo = view.findViewById(R.id.etPhoneNo);
        etEmail = view.findViewById(R.id.etEmail);
        etAddres = view.findViewById(R.id.etAddress);
        etGender = view.findViewById(R.id.etGender);
        etDOB = view.findViewById(R.id.etDOB);
        etBloodGroup = view.findViewById(R.id.etBloodgroup);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnImage = view.findViewById(R.id.btnImg);

        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), RegisterPage.this, year, month, day);
                datePickerDialog.show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {
                    login();
                }
            }



            private void login() {
                String pass = etPassword.getText().toString();
                String pass2 = etPassword.getText().toString();
                if (pass.equals(pass2)) {
                    SaveUser();
                }
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
        return view;
    }

    private void BrowseImage(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(data == null){
                Toast.makeText(getActivity(), "Select an image", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        imagePath = getRealPathFromUri(uri);
        previewImage(imagePath);
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), uri,
                projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }
    private void previewImage(String ivImag) {
        File imgFile = new File(ivImag);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            btnImage.setImageBitmap(myBitmap);
        }
    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    private void SaveImageOnly() {
        File file = new File(imagePath);

        RequestBody requestBody = RequestBody.create
                (MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData
                ("imageFile", file.getName(), requestBody);
        UserApi clothesApi = Url.getInstance().create(UserApi.class);
        Call<ImageResponse> responseCall = clothesApi.uploadImage(body);

        StrictMode();
        try{
            Response<ImageResponse> imageResponseResponse = responseCall.execute();
            imageName = imageResponseResponse.body().getFilename();
        }catch (IOException e){
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void SaveUser(){

        SaveImageOnly();
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
        String userimagename = imageName;


        User user= new User(firstname,lastname,username,password,email,phone,address,gender,blood_group,date_of_birth,userimagename);
        Call<RegisterResponse> listCall = userApi.addUsers(user);

        listCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getMessage().equals("phone number already registered")){
                    Toast.makeText(getActivity(), "Phone number already registered", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_SHORT).show();
                }
                etFirstname.setText("");
                etLastname.setText("");
                etAddres.setText("");
                etDOB.setText("");
                etEmail.setText("");
                etPassword.setText("");
                etPassword2.setText("");
                etUsername.setText("");
                etPhoneNo.setText("");

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

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