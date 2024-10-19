package com.minthukyaw.rental.view.activities.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.minthukyaw.rental.R;
import com.minthukyaw.rental.databinding.ActivityLoginBinding;
import com.minthukyaw.rental.model.UserModel;
import com.minthukyaw.rental.utils.DatabaseHelper;
import com.minthukyaw.rental.view.activities.HomeActivity;
import com.minthukyaw.rental.view.activities.ProfileActivity;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter required fields", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkCredentials = databaseHelper.checkAuth(email,password);

                    if(checkCredentials){
                        UserModel me = databaseHelper.getUserByEmail(email);

                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", me.getName());
                        editor.putString("email", me.getEmail());

                        editor.apply();

                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("name",me.getName());
                        intent.putExtra("email",me.getEmail());
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
