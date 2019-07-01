package Fragments;


import android.content.Intent;
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

public class LoginPage extends Fragment  {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvIncorrect;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_login_page, container, false);
        etUsername = view.findViewById(R.id.etUsernamelogin);
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

        String username=etUsername.getText().toString();
        String password=etPassword.getText().toString();
        UserApi userApi = Url.getInstance().create(UserApi.class);

        User user = new User(username,password);

        Call<LoginResponse> call  = userApi.getResponse(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().isStatus()) {
                    Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Dashboard.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty((etUsername.getText().toString()))) {
            etUsername.setError("Please enter Username");
            etUsername.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((etPassword.getText().toString()))) {
            etPassword.setError("Please enter Password");
            etPassword.requestFocus();
            return true;
        }
        return false;
    }
}