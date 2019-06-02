package Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bloodbankmanagementsystem.ImageResponse;
import com.example.bloodbankmanagementsystem.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import Helper.MyHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class RegisterPage extends Fragment {
    private EditText etFullname, etPhoneNo, etEmail, etAddres, etUsername, etPassword, etPassword2;
    private Spinner etGender, etBloodGroup;
    private Button btnRegister, btnReset;
    private ImageView btnImage;
    private String imagePath, imageName;
    Bitmap bitmap;
    View view;
    ByteArrayOutputStream byteArrayOutputStream;
    File file;
    FileOutputStream fileOutputStream;

    public RegisterPage() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register_page, container, false);
        etFullname = view.findViewById(R.id.etFullname);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etPassword2 = view.findViewById(R.id.etPassword2);
        etPhoneNo = view.findViewById(R.id.etPhoneNo);
        etEmail = view.findViewById(R.id.etEmail);
        etAddres = view.findViewById(R.id.etAddress);
        etGender = view.findViewById(R.id.etGender);
        etBloodGroup = view.findViewById(R.id.etBloodgroup);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnImage = view.findViewById(R.id.btnImg);
        byteArrayOutputStream = new ByteArrayOutputStream();
        checkPermission();

        final MyHelper myHelper = new MyHelper(getActivity());
        final SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = etPassword.getText().toString();
                String pass2 = etPassword.getText().toString();
                if (!isEmpty()) {
                    login();
                }

            }

            private void login() {
                String pass = etPassword.getText().toString();
                String pass2 = etPassword.getText().toString();
                if (pass.equals(pass2)) {
                    SignUp();
                } else {
                    Toast.makeText(getActivity(), "wrong username or password", Toast.LENGTH_SHORT).show();
                }

            }


            private void SignUp() {
                long id = myHelper.InsertData(etFullname.getText().toString(),etUsername.getText().toString()
                        ,etPassword.getText().toString(),etEmail.getText().toString(),
                        etPhoneNo.getText().toString(), etAddres.getText().toString(),etGender.getSelectedItem().toString(),
                        etBloodGroup.getSelectedItem().toString(), sqLiteDatabase);
                if (id>0){
                    Toast.makeText(getActivity(), "Successfully registered", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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

    private boolean isEmpty() {
        if (TextUtils.isEmpty((etFullname.getText().toString()))) {
            etFullname.setError("Please enter Name");
            etFullname.requestFocus();
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


    private void BrowseImage() {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i,0);
    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(data == null){
                Toast.makeText(this, "Please Select An Image", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        imagePath = getRealPathFromUri(uri);
        previewImage(imagePath);
    }
    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri,
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
        ClothesApi clothesApi = Url.getInstance().create(ClothesApi.class);
        Call<ImageResponse> responseCall = clothesApi.uploadImage(body);

        StrictMode();
        try{
            Response<ImageResponse> imageResponseResponse = responseCall.execute();
            imageName = imageResponseResponse.body().getFilename();
        }catch (IOException e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

//    private void SaveItem() {
//
//        SaveImageOnly();
//        ClothesApi clothesApi = Url.getInstance().create(ClothesApi.class);
//        String itemName = etItemName.getText().toString();
//        String itemPrice = etItemPrice.getText().toString();
//        String itemDescription = etItemDescription.getText().toString();
//        String itemImageName = imageName;
//
//        Clothes clothes = new Clothes(itemName,itemPrice,itemDescription,itemImageName);
//        Call<Void> listCall = clothesApi.addItems(clothes);
//
//        listCall.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                Toast.makeText(AddItem.this, "Added Item Successfully", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(AddItem.this, "Item Not Added", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
