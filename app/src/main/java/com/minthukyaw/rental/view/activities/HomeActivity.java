package com.minthukyaw.rental.view.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.minthukyaw.rental.adapter.PropertyRVAdapter;
import com.minthukyaw.rental.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.minthukyaw.rental.model.PropertyModel;
import com.minthukyaw.rental.utils.DatabaseHelper;
import com.minthukyaw.rental.R;


import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private ArrayList<PropertyModel> property_modelArrayList;
    private DatabaseHelper dbHelper;
    private PropertyRVAdapter propertyRVAdapter;
    private RecyclerView propertyRV;
    ActivityHomeBinding binding;
    FloatingActionButton add_button;
    String username,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        username=getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");

        propertyRV = findViewById(R.id.recyclerView);


        property_modelArrayList=new ArrayList<>();
        dbHelper=new DatabaseHelper(this);

        property_modelArrayList=dbHelper.getAllProperty();

        propertyRVAdapter=new PropertyRVAdapter(property_modelArrayList,HomeActivity.this,username,email);
        propertyRV=findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(HomeActivity.this,RecyclerView.VERTICAL,false);
        propertyRV.setLayoutManager(linearLayoutManager);
        propertyRV.setAdapter(propertyRVAdapter);

        setupBottomNavigation();

    }

}