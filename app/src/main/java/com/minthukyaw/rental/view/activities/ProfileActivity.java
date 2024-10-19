package com.minthukyaw.rental.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.minthukyaw.rental.MainActivity;
import com.minthukyaw.rental.R;
import com.minthukyaw.rental.databinding.ActivityProfileBinding;

public class ProfileActivity extends BaseActivity {

    private ActivityProfileBinding binding;
    private String username, email;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ProfileActivity", "ProfileActivity started");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            logoutUser(view);
            }
        });


        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("name");

        if (email == null || username == null) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            email = sharedPreferences.getString("email", "No Email");
            username = sharedPreferences.getString("name", "No Name");
        }

        Log.d("from profile", username);


        binding.profileUsernameTxt.setText(username);
        binding.profileEmailTxt.setText(email);

        setupBottomNavigation();


    }

    public void logoutUser(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
