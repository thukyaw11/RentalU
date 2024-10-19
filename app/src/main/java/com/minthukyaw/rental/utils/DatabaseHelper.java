package com.minthukyaw.rental.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.minthukyaw.rental.model.PropertyModel;
import com.minthukyaw.rental.model.UserModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "rentalU.db";
    private static final int DB_VERSION = 1;
    private static final String USER_TABLE="users";
    private static final String RENT_TABLE="rents";

    private static final String refNoCol="ref_no";
    private static final String propertyTypeCol="property_type";
    private static final String noOfRoomsCol="no_of_rooms";
    private static final String dateCol="date";
    private static final String rentalPriceCol="rental_price";
    private static final String typeOfFurnitureCol="type_of_furniture";
    private static final String remarkCol="remark";
    private static final String reporterNameCol ="reporter_name";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        String userTableCreateQuery = "CREATE TABLE " + USER_TABLE +
                " (id TEXT PRIMARY KEY,username TEXT, email TEXT, password TEXT)";
        String rentTableCreateQuery = "CREATE TABLE "+RENT_TABLE+"("+
                refNoCol+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                propertyTypeCol+" TEXT,"+noOfRoomsCol+" TEXT,"+
                dateCol+" TEXT,"+rentalPriceCol+" TEXT,"+
                typeOfFurnitureCol+" TEXT,"+remarkCol+" TEXT,"+
                reporterNameCol+" TEXT)";
        MyDatabase.execSQL(userTableCreateQuery);
        MyDatabase.execSQL(rentTableCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("DROP TABLE if exists " + USER_TABLE);
        MyDatabase.execSQL("DROP TABLE IF EXISTS "+RENT_TABLE);
    }



    public Boolean insertUser(String name,String email,String hashedPassword){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues userContentValue = new ContentValues();
        String uuid = UUID.randomUUID().toString();

        userContentValue.put("id", uuid);
        userContentValue.put("username",name);
        userContentValue.put("email",email);
        userContentValue.put("password",hashedPassword);
        long result = MyDatabase.insert(USER_TABLE,null, userContentValue);

        return result != -1;
    }

    public void insertProperty(String property_type,String no_of_rooms,String date,String rental_price,String type_of_furniture,String remark,String reporter)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues propertyContentValue =new ContentValues();

        propertyContentValue.put(propertyTypeCol,property_type);
        propertyContentValue.put(noOfRoomsCol,no_of_rooms);
        propertyContentValue.put(dateCol,date);
        propertyContentValue.put(rentalPriceCol,rental_price);
        propertyContentValue.put(typeOfFurnitureCol,type_of_furniture);
        propertyContentValue.put(remarkCol,remark);
        propertyContentValue.put(reporterNameCol,reporter);

        database.insert(RENT_TABLE,null,propertyContentValue);

        database.close();
    }

    public ArrayList<PropertyModel> getAllProperty()
    {
        SQLiteDatabase database= this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor=database.rawQuery("SELECT * FROM "+RENT_TABLE,null);
        ArrayList<PropertyModel> propertyList= new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do{
                propertyList.add(new PropertyModel(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7)));
            }while (cursor.moveToNext());
        }
        database.close();
        return propertyList;
    }

    public ArrayList<PropertyModel> getMyUploadedProperty(String userName)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.rawQuery("SELECT * FROM "+RENT_TABLE+" where reporter_name= '"+ userName +"'",null);

        ArrayList<PropertyModel> property_modelArrayList = new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do{
                property_modelArrayList.add(new PropertyModel(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7)));

            }while (cursor.moveToNext());
        }
        db.close();
        return property_modelArrayList;
    }

    public Boolean checkEmailExist(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + USER_TABLE +" where email = ?", new String[]{email});

        return cursor.getCount() > 0;
    }

    public Boolean checkAuth(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT password FROM " + USER_TABLE + " WHERE email = ?", new String[]{email});

        if (cursor.moveToFirst()) {
            String storedHashedPassword = cursor.getString(0);
            String hashedInputPassword = HashUtil.hashPassword(password);

            return storedHashedPassword.equals(hashedInputPassword);
        }

        cursor.close();
        return false;
    }

    public UserModel getUserByEmail(String email) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        UserModel userModel = null;

        try {
            String query = "SELECT username, email FROM " + USER_TABLE + " WHERE email = ?";
            Log.d("DatabaseHelper", "Executing query: " + query + " with email: " + email);
            cursor = MyDatabase.rawQuery(query, new String[]{email});

            if (cursor != null && cursor.moveToFirst()) {
                String userName = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                userModel = new UserModel(userName, userEmail);
            } else {
                Log.e("DatabaseHelper", "No user found with email: " + email);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error fetching user by email: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userModel;
    }


    public void updateProperty(String originalRefNo,int newRefNo,String propertyType,String noOfRooms,String date,String rentalPrice,String typeOfFurniture,String remark,String reporter)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(refNoCol,newRefNo);
        contentValues.put(propertyTypeCol,propertyType);
        contentValues.put(noOfRoomsCol,noOfRooms);
        contentValues.put(dateCol,date);
        contentValues.put(rentalPriceCol,rentalPrice);
        contentValues.put(typeOfFurnitureCol,typeOfFurniture);
        contentValues.put(remarkCol,remark);
        contentValues.put(reporterNameCol,reporter);

        db.update(RENT_TABLE,contentValues,"ref_no=?",new String[]{originalRefNo});

    }

    public void removeProperty(int refNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RENT_TABLE, "ref_no=?", new String[]{String.valueOf(refNo)});
        db.close();
    }

    public void removePropertyWithString(String refNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RENT_TABLE, "ref_no=?", new String[]{refNo});
        db.close();
    }





}
