package com.minthukyaw.rental.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minthukyaw.rental.R;
import com.minthukyaw.rental.model.PropertyModel;
import com.minthukyaw.rental.view.activities.HomeActivity;

import java.util.ArrayList;

public class PropertyRVAdapter extends RecyclerView.Adapter<PropertyRVAdapter.ViewHolder> {
    private ArrayList<PropertyModel> property_modelArrayList;
    private Context context;
    String username,email;

    public PropertyRVAdapter(ArrayList<PropertyModel> property_modelArrayList, Context context,String username,String email) {
        this.property_modelArrayList = property_modelArrayList;
        this.context = context;
        this.username = username;
        this.email = email;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PropertyModel property_model=property_modelArrayList.get(position);

        holder.ref_no.setText(String.valueOf(property_model.getRefNo()));
        holder.type_of_property.setText(property_model.getPropertyType());
        holder.bedrooms.setText("No of rooms " + property_model.getNoOfRooms());
        holder.date.setText(property_model.getDate());
        holder.rental_price.setText("$ " + property_model.getRentalPrice());
        holder.furniture_type.setText(property_model.getTypeOfFurniture());
        holder.remarks.setText(property_model.getRemark());
        holder.name.setText("Rented by " + property_model.getReporterName());


    }

    @Override
    public int getItemCount() {
        return property_modelArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView ref_no,type_of_property,bedrooms,date,rental_price,furniture_type,remarks,name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ref_no=itemView.findViewById(R.id.tv_ref_no);
            type_of_property=itemView.findViewById(R.id.tv_property_type);
            bedrooms=itemView.findViewById(R.id.tv_bedrooms);
            date=itemView.findViewById(R.id.tv_date);
            rental_price=itemView.findViewById(R.id.tv_price);
            furniture_type=itemView.findViewById(R.id.tv_furniture);
            remarks=itemView.findViewById(R.id.tv_remark);
            name=itemView.findViewById(R.id.tv_reporter);


        }
    }
}
