package com.example.bloodbankmanagementsystem;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import Adapter.DetailsAdapter;
import Model.User;

public class Search_blood extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText etsearchbyblood;
    private Button btnsearchbyblood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);

        recyclerView=findViewById(R.id.recyclerview);
        //create list of details
        List<User> userList = new ArrayList<>();
        DetailsAdapter detailsAdapter=new DetailsAdapter(this,userList);
        recyclerView.setAdapter(detailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

