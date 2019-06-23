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
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPage extends Fragment {
    private EditText etPasswordAdmin, etUsernameAdmin;
    private Button btnLogin;
    private TextView tvIncorrect;

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

        final String uname = etUsernameAdmin.getText().toString();
        final String pass = etPasswordAdmin.getText().toString();
        AdminApi adminApi = Url.getInstance().create(AdminApi.class);
        Call<List<Admin>> listCall = adminApi.getAdmin();

        listCall.enqueue(new Callback<List<Admin>>() {
            @Override
            public void onResponse(Call<List<Admin>> call, Response<List<Admin>> response) {
                List<Admin> adminList =response.body();
                for (Admin admin:adminList){
                    String Auname = admin.getAdminUsername();
                    String Apass = admin.getAdminPassword();

                    if (uname.equals(Auname)&& pass.equals(Apass)){
                        Toast.makeText(getActivity(), "Welcome Admin", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),AdminDashboard.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Admin>> call, Throwable t) {
                Toast.makeText(getActivity(), "login Unsuccessfull", Toast.LENGTH_SHORT).show();
                tvIncorrect.setText("Incorrect Username or password");
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



