package com.minthukyaw.rental.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.minthukyaw.rental.R;

import com.minthukyaw.rental.databinding.ActivityAddNewBinding;
import com.minthukyaw.rental.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.minthukyaw.rental.utils.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNewActivity extends BaseActivity {

    AppCompatButton Cancel,Add;
    RadioGroup radioGroup;
    EditText edtdate,price, edtremark,name;
    String property_type,no_of_rooms,date,rental_price,type_of_furniture,remark,reporter_name;

    DatabaseHelper dbHelper=new DatabaseHelper(this);

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date,username,email;
    ActivityAddNewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        username=getIntent().getStringExtra("username");
        email=getIntent().getStringExtra("email");

        Add=findViewById(R.id.InsertButton);
        Cancel = findViewById(R.id.Cancel);
        Spinner sppropertytype= findViewById(R.id.spinner_property_type);
        Spinner spbedrooms= findViewById(R.id.spinner_bedroom);


        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpleDateFormat.format(calendar.getTime());
        edtdate =findViewById(R.id.Date);
        edtdate.setText(Date);
        edtdate.setEnabled(false);


        price=findViewById(R.id.Price);
        radioGroup=findViewById(R.id.radio_furniture_type);
        edtremark =findViewById(R.id.Remark);
        name = findViewById(R.id.reporter_name);


        String username = getIntent().getStringExtra("username");


        if (username == null) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            username = sharedPreferences.getString("name", "No Name");
        }


        name.setText(username);


        String email = getIntent().getStringExtra("email");
        if (email == null) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            email = sharedPreferences.getString("email", "No Email");
        }


        name.setEnabled(false);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.property_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sppropertytype.setAdapter(adapter);

        String[] type1= new String[]{"Select Bedrooms","Studio","One","Two"};
        ArrayAdapter<String> adapter2= new ArrayAdapter<>(this,R.layout.bedrooms,type1);
        spbedrooms.setAdapter(adapter2);


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                property_type=sppropertytype.getSelectedItem().toString();
                no_of_rooms=spbedrooms.getSelectedItem().toString();
                date=edtdate.getText().toString();
                rental_price=price.getText().toString();
                RadioButton fur=findViewById(radioGroup.getCheckedRadioButtonId());
                type_of_furniture=fur.getText().toString();
                //type_of_furniture=((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                remark=edtremark.getText().toString();
                reporter_name=name.getText().toString();
                System.out.println(property_type+" "+no_of_rooms+" "+date+" "+rental_price+" "+type_of_furniture+" "+remark+" reporter name: "+reporter_name);
                if(property_type.isEmpty()||no_of_rooms.isEmpty()||date.isEmpty()||rental_price.isEmpty()||type_of_furniture.isEmpty()||reporter_name.isEmpty())
                {
                    Toast.makeText(AddNewActivity.this, "Enter all data", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbHelper.insertProperty(property_type,no_of_rooms,date,rental_price,type_of_furniture,remark,reporter_name);
                    Toast.makeText(AddNewActivity.this, "New property added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MyRentActivity.class);
                    intent.putExtra("username","");
                    intent.putExtra("email","");
                    startActivity(intent);
                }
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        setupBottomNavigation();
    }


    public void TxtClickCancel(View view)
    {

        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);

    }
}