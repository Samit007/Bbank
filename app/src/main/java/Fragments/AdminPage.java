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

import com.example.bloodbankmanagementsystem.AdminDashboard;
import com.example.bloodbankmanagementsystem.Dashboard;
import com.example.bloodbankmanagementsystem.R;

import java.util.List;

import Api.AdminApi;
import Model.Admin;
import Model.LoginResponse;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPage extends Fragment {
    private EditText etPasswordAdmin, etUsernameAdmin;
    private Button btnLogin;
    private TextView tvIncorrect;

    public AdminPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_admin_page, container, false);
        etUsernameAdmin = view.findViewById(R.id.etUsernameloginAdmin);
        etPasswordAdmin = view.findViewById(R.id.etPasswordloginAdmin);
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

        final String username = etUsernameAdmin.getText().toString();
        final String password = etPasswordAdmin.getText().toString();
        AdminApi adminApi = Url.getInstance().create(AdminApi.class);

        Admin admin= new Admin(username,password);

        Call<LoginResponse> call  = adminApi.getAdmin(admin);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
              {
                    if (response.body().isStatus()) {
                        Intent intent = new Intent(getActivity(), AdminDashboard.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty((etUsernameAdmin.getText().toString()))) {
            etUsernameAdmin.setError("Please enter Username");
            etUsernameAdmin.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((etPasswordAdmin.getText().toString()))) {
            etPasswordAdmin.setError("Please enter Password");
            etPasswordAdmin.requestFocus();
            return true;
        }
        return false;
    }
}



