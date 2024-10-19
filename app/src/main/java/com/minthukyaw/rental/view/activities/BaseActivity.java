package com.minthukyaw.rental.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.minthukyaw.rental.R;

public class BaseActivity extends AppCompatActivity {

    protected String username, email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure to set the content view in each derived activity before this point.
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");

        setupBottomNavigation();
    }

    @SuppressLint("NonConstantResourceId")
    protected void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (bottomNavigationView == null) {
            String layoutFileName = getClass().getSimpleName() + ".java";
            Log.e("BottomNavigation", "BottomNavigationView is null! Check your layout file: " + layoutFileName);
            return;
        }


        if (this instanceof HomeActivity) {
            bottomNavigationView.setSelectedItemId(R.id.home);
        } else if (this instanceof ProfileActivity) {
            bottomNavigationView.setSelectedItemId(R.id.myProfile);
        } else if ( this instanceof AddNewActivity) {
            bottomNavigationView.setSelectedItemId(R.id.addNew);
        } else if ( this instanceof MyRentActivity) {
            bottomNavigationView.setSelectedItemId(R.id.myRent);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    if (!(this instanceof HomeActivity)) {
                        navigateToActivity(HomeActivity.class);
                    }
                    return true;
                case R.id.addNew:
                    Intent intent = new Intent(getApplicationContext(),AddNewActivity.class);
                    intent.putExtra("username",username);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();
                    return true;

                case R.id.myRent:
                    Intent rentIntent = new Intent(getApplicationContext(),MyRentActivity.class);
                    rentIntent.putExtra("name",username);
                    rentIntent.putExtra("email",email);
                    startActivity(rentIntent);
                    finish();
                    return true;
                case R.id.myProfile:
                    if (!(this instanceof ProfileActivity)) {
                        navigateToActivity(ProfileActivity.class);
                    }
                    return true;
            }
            return false;
        });
    }

    private void navigateToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}
