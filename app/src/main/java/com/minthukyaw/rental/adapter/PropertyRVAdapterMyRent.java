package com.minthukyaw.rental.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.minthukyaw.rental.R;
import com.minthukyaw.rental.model.PropertyModel;
import com.minthukyaw.rental.utils.DatabaseHelper;
import com.minthukyaw.rental.view.activities.RentUpdateActivity;

import java.util.ArrayList;

public class PropertyRVAdapterMyRent extends RecyclerView.Adapter<PropertyRVAdapterMyRent.ViewHolder> {
    private ArrayList<PropertyModel> propertyModelArrayList;
    private Context context;
    private DatabaseHelper dbHelper;

    String username, email;

    public PropertyRVAdapterMyRent(ArrayList<PropertyModel> propertyModelArrayList, Context context, String username, String email) {
        this.propertyModelArrayList = propertyModelArrayList;
        this.context = context;
        this.username = username;
        this.email = email;

        // Initialize DatabaseHelper
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public PropertyRVAdapterMyRent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item_upload, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PropertyModel propertyModel = propertyModelArrayList.get(position);
        holder.ref_no.setText(String.valueOf(propertyModel.getRefNo()));
        holder.type_of_property.setText(propertyModel.getPropertyType());
        holder.bedrooms.setText(propertyModel.getNoOfRooms());
        holder.date.setText(propertyModel.getDate());
        holder.rental_price.setText(propertyModel.getRentalPrice());
        holder.furniture_type.setText(propertyModel.getTypeOfFurniture());
        holder.remarks.setText(propertyModel.getRemark());
        holder.name.setText(propertyModel.getReporterName());

        holder.itemView.setOnLongClickListener(v -> {
            showDeleteConfirmationDialog(propertyModel);
            return true; // Indicates that the long press event is consumed
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, RentUpdateActivity.class);

                intent.putExtra("ref_no",String.valueOf(propertyModel.getRefNo()));
                intent.putExtra("type_of_property",propertyModel.getPropertyType());
                intent.putExtra("bedrooms",propertyModel.getNoOfRooms());
                intent.putExtra("date",propertyModel.getDate());
                intent.putExtra("rental_price",propertyModel.getRentalPrice());
                intent.putExtra("furniture_type",propertyModel.getTypeOfFurniture());
                intent.putExtra("remark",propertyModel.getRemark());
                intent.putExtra("name",propertyModel.getReporterName());
                intent.putExtra("username",username);
                intent.putExtra("email",email);
                context.startActivity(intent);
            }
        });
    }

    private void showDeleteConfirmationDialog(PropertyModel propertyModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Property")
                .setMessage("Are you sure you want to delete this property?")
                .setPositiveButton("Yes", (dialog, which) -> deleteProperty(propertyModel))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteProperty(PropertyModel propertyModel) {
        dbHelper.removeProperty(propertyModel.getRefNo()); // Ensure dbHelper is used here
        propertyModelArrayList.remove(propertyModel);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return propertyModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ref_no, type_of_property, bedrooms, date, rental_price, furniture_type, remarks, name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ref_no = itemView.findViewById(R.id.tv_ref_no);
            type_of_property = itemView.findViewById(R.id.tv_property_type);
            bedrooms = itemView.findViewById(R.id.tv_bedrooms);
            date = itemView.findViewById(R.id.tv_date);
            rental_price = itemView.findViewById(R.id.tv_price);
            furniture_type = itemView.findViewById(R.id.tv_furniture);
            remarks = itemView.findViewById(R.id.tv_remark);
            name = itemView.findViewById(R.id.tv_reporter);
        }
    }
}
