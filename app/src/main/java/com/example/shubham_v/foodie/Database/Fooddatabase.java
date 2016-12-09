package com.example.shubham_v.foodie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

/**
 * Created by shubham_v on 08-12-2016.
 */

public class Fooddatabase extends SQLiteOpenHelper {

    public Fooddatabase(Context context) {
        super(context, "FoodDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // primary key
        db.execSQL("Create table foodtable(hotel_Id text NOT NULL UNIQUE,hotel_name text,cuision text,cost_of_two text,hotel_address " +
                "text,menu_card_url text,hotel_pic_url text,hotel_review text,hotel_rating integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS foodtable");
        onCreate(sqLiteDatabase);
    }

   public  Boolean InsertHotelDataDB(String hotel_id,String hotel_name,String cuision, String costoftwo, String hotel_address,String menu_card_url,String hotel_pic_url)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put("hotel_Id", hotel_id);
            contentValues.put("hotel_name", hotel_name);
            contentValues.put("cuision", cuision);
            contentValues.put("cost_of_two",costoftwo);
            contentValues.put("hotel_address", hotel_address);
            contentValues.put("menu_card_url", menu_card_url);
            contentValues.put("hotel_pic_url", hotel_pic_url);
            db.insert("foodtable", null, contentValues);
        }

        catch (Exception e)
        {
            e.getMessage();
        }

        return true;
    }

    public Cursor GetData(String hotelid) {
        SQLiteDatabase db = this.getReadableDatabase();
       Cursor res = db.rawQuery("SELECT * FROM foodtable WHERE hotel_name =?", new String[] {hotelid + ""});
       // Cursor res =  db.rawQuery( "select * from foodtable where id="+hotelid+"",null );
        return res;
    }

    public boolean UpdateReview (String hotelname , String review, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hotel_review", review);
        contentValues.put("hotel_rating", rating);;
        db.update("foodtable", contentValues, "hotel_name = ? ", new String[]{hotelname});
        return true;
    }


}
