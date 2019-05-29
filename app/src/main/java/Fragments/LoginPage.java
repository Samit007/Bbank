package Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bloodbankmanagementsystem.Dashboard;
import com.example.bloodbankmanagementsystem.R;

import static android.content.Context.MODE_PRIVATE;

public class LoginPage extends Fragment  {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvIncorrect;


    public LoginPage() {

    }

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

//        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("User",MODE_PRIVATE);
//        String username=sharedPreferences.getString("username","");
//        String password=sharedPreferences.getString("password","");
//        if(username.equals(etUsername.getText().toString())||
//                password.equals(etPassword.getText().toString())){
        String u=etUsername.getText().toString();
        String p=etPassword.getText().toString();
        if (u.equals("admin")&& p.equals("admin"))
        {
            Intent intent = new Intent(getActivity(),Dashboard.class);
            startActivity(intent);
        }else{
            tvIncorrect.setText("incorrect username or password");
            tvIncorrect.setError("");
            tvIncorrect.requestFocus();
            return;
        }
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
