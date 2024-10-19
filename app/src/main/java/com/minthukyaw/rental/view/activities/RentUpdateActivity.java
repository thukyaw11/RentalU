package com.minthukyaw.rental.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.minthukyaw.rental.R;
import com.minthukyaw.rental.utils.DatabaseHelper;

public class RentUpdateActivity extends BaseActivity {

    AppCompatButton btnCancel, btnUpdate,btnDelete;
    RadioGroup radioGroup;
    RadioButton radio1,radio2,radio3;
    EditText edt_refno,edtdate, edtprice, edtremark, edtReporter;
    String ref_no,property_type,no_of_rooms,date,rental_price,type_of_furniture,remark,reporter_name;
    DatabaseHelper dbHelper=new DatabaseHelper(this);
    String username,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        username=getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");

        btnUpdate = findViewById(R.id.UpdateButton);
        btnCancel = findViewById(R.id.Cancel);
        edt_refno = findViewById(R.id.edt_ref_no);
        Spinner sppropertytype = findViewById(R.id.spinner_property_type);
        Spinner spbedrooms = findViewById(R.id.spinner_bedroom);
        edtdate = findViewById(R.id.Date);
        edtdate.setEnabled(false);
        edtprice = findViewById(R.id.Price);
        radioGroup = findViewById(R.id.radgp);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        edtremark = findViewById(R.id.Remark);
        edtReporter = findViewById(R.id.Reporter);

        dbHelper = new DatabaseHelper(this);

        ref_no = getIntent().getStringExtra("ref_no");
        String old_ref_no = ref_no;
        property_type = getIntent().getStringExtra("type_of_property");
        no_of_rooms = getIntent().getStringExtra("bedrooms");
        date = getIntent().getStringExtra("date");
        rental_price = getIntent().getStringExtra("rental_price");
        type_of_furniture = getIntent().getStringExtra("furniture_type");
        remark = getIntent().getStringExtra("remark");
        reporter_name = getIntent().getStringExtra("name");

        edt_refno.setText(ref_no);
        edt_refno.setEnabled(false);
        edtReporter.setEnabled(false);

        String[] type = new String[]{"Select Property Type", "Flat", "House", "Bungalow"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_items, type);
        sppropertytype.setAdapter(adapter1);
        sppropertytype.setSelection(adapter1.getPosition(property_type));

        String[] type1 = new String[]{"Select Bedrooms", "Studio", "One", "Two"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.bedrooms, type1);
        spbedrooms.setAdapter(adapter2);
        spbedrooms.setSelection(adapter2.getPosition(no_of_rooms));

        edtdate.setText(date);
        edtprice.setText(rental_price);

        if (type_of_furniture.equalsIgnoreCase("Furnished")) {
            radio1.setChecked(true);
        } else if (type_of_furniture.equalsIgnoreCase("Part Furnished")) {
            radio2.setChecked(true);
        } else {
            radio3.setChecked(true);
        }
        edtremark.setText(remark);
        edtReporter.setText(reporter_name);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int int_ref_no = Integer.parseInt(edt_refno.getText().toString());
                property_type = sppropertytype.getSelectedItem().toString();
                no_of_rooms = spbedrooms.getSelectedItem().toString();
                date = edtdate.getText().toString();
                rental_price = edtprice.getText().toString();
                RadioButton fur = findViewById(radioGroup.getCheckedRadioButtonId());
                type_of_furniture = fur.getText().toString();
                remark = edtremark.getText().toString();
                reporter_name = edtReporter.getText().toString();

                dbHelper.updateProperty(old_ref_no, int_ref_no, property_type, no_of_rooms, date, rental_price, type_of_furniture, remark, reporter_name);
                Toast.makeText(RentUpdateActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RentUpdateActivity.this, MyRentActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

    }

    public void TxtClickCancel(View view)
    {
        btnCancel = findViewById(R.id.Cancel);
        Intent intent = new Intent(this,MyRentActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("email",email);
        startActivity(intent);

    }


}