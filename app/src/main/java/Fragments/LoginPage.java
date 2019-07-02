package Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbankmanagementsystem.Dashboard;
import com.example.bloodbankmanagementsystem.R;

import Api.UserApi;
import Model.LoginResponse;
import Model.User;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginPage extends Fragment  {
    private EditText etPhone, etPassword;
    private Button btnLogin;
    private TextView tvIncorrect;
    private  String a;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_login_page, container, false);
        etPhone = view.findViewById(R.id.etPhonelogin);
        etPassword = view.findViewById(R.id.etPasswordlogin);
        tvIncorrect = view.findViewById(R.id.tvIncorrect);
        btnLogin = view.findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {
                    checkUser();
                }
            }
        });
        return view;
    }

    private void checkUser() {

        final String phone=etPhone.getText().toString();
        String password=etPassword.getText().toString();
        UserApi userApi = Url.getInstance().create(UserApi.class);

        final User user = new User(phone,password);

        Call<LoginResponse> call  = userApi.getResponse(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().isStatus()) {
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("User",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("phone",etPhone.getText().toString());
                    editor.commit();

                    Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Dashboard.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty((etPhone.getText().toString()))) {
            etPhone.setError("Please enter Phone number");
            etPhone.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((etPassword.getText().toString()))) {
            etPassword.setError("Please enter Password");
            etPassword.requestFocus();
            return true;
        }
        return false;
    }
}