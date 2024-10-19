package com.minthukyaw.rental.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.minthukyaw.rental.adapter.PropertyRVAdapterMyRent;
import com.minthukyaw.rental.databinding.ActivityMyRentBinding;
import com.minthukyaw.rental.databinding.ActivityProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.minthukyaw.rental.model.PropertyModel;
import com.minthukyaw.rental.utils.DatabaseHelper;
import com.minthukyaw.rental.R;

import java.util.ArrayList;

public class MyRentActivity extends BaseActivity {

    AppCompatButton btnUpdate;
    private ArrayList<PropertyModel> property_modelArrayList;
    private DatabaseHelper dbHelper;
    private PropertyRVAdapterMyRent propertyRVAdapterUpload;
    private RecyclerView propertyRV;
    String username,email;
    ActivityMyRentBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyRentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("name");

        if (email == null || username == null) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            email = sharedPreferences.getString("email", "No Email");
            username = sharedPreferences.getString("name", "No Name");
        }

        Log.d("from rent", username);


        propertyRV = findViewById(R.id.recyclerView);


        property_modelArrayList=new ArrayList<>();
        dbHelper=new DatabaseHelper(this);

        property_modelArrayList=dbHelper.getMyUploadedProperty(username);

        propertyRVAdapterUpload=new PropertyRVAdapterMyRent(property_modelArrayList,MyRentActivity.this,username,email);
        propertyRV=findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MyRentActivity.this,RecyclerView.VERTICAL,false);
        propertyRV.setLayoutManager(linearLayoutManager);
        propertyRV.setAdapter(propertyRVAdapterUpload);


        setupBottomNavigation();
    }



}