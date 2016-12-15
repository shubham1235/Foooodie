package com.example.shubham_v.foodie.Fragment;


import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubham_v.foodie.Database.Fooddatabase;
import com.example.shubham_v.foodie.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotelInfo extends Fragment {



    Fooddatabase fooddatabase;
    public String review;
    public String rating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_hotel_info, container, false);
        final Button navigationButton = (Button)view.findViewById(R.id.navigation_button_id);
        ImageView hotelImage = (ImageView)view.findViewById(R.id.hotel_image_view_id);
        ImageView hotelMenu_card =(ImageView)view.findViewById(R.id.hotel_menu_card);
        TextView hotelCuision = (TextView)view.findViewById(R.id.hotel_cuisines_id);
        TextView hotelAddress = (TextView)view.findViewById(R.id.hotel_address_id);
        TextView hotelCostOfTwo =(TextView)view.findViewById(R.id.cost_of_two_id);
        TextView hotelId = (TextView)view.findViewById(R.id.hotel_id);
        TextView hotelname = (TextView)view.findViewById(R.id.hotel_name_Id);
         final EditText EnterReview = (EditText)view.findViewById(R.id.userReview_insert_id);
        final EditText EnterRating = (EditText)view.findViewById(R.id.user_insert_rat_id);
        Button   Reviewsubmit =(Button)view.findViewById(R.id.submit_button_id);
        TextView ReviewshowTextview = (TextView)view.findViewById(R.id.user_show_review_id);
        TextView RatingshowTextview =  (TextView)view.findViewById(R.id.user_reating_show_id);


        EnterRating.setRawInputType(Configuration.KEYBOARD_12KEY);



        fooddatabase = new Fooddatabase(getActivity());


        Bundle bundle = getArguments();
        final String hotelIdForSearchdata = bundle.getString("hotelId");
        Cursor rs =  fooddatabase.GetData(hotelIdForSearchdata);
        rs.moveToFirst();


        String hotel_Id_db = rs.getString(rs.getColumnIndex("hotel_Id"));
        String hotel_name_db = rs.getString(rs.getColumnIndex("hotel_name"));
        String hotel_cuision_db = rs.getString(rs.getColumnIndex("cuision"));
        String  hotel_costfortew_db = rs.getString(rs.getColumnIndex("cost_of_two"));
        String hotel_Address_db = rs.getString(rs.getColumnIndex("hotel_address"));

        String hotel_Image_url_db = rs.getString(rs.getColumnIndex("hotel_pic_url"));

        String hotel_Menu_url_db = rs.getString(rs.getColumnIndex("menu_card_url"));

        review = rs.getString(rs.getColumnIndex("hotel_review"));
        rating  = rs.getString(rs.getColumnIndex("hotel_rating"));
        //Toast.makeText(getActivity(), hotel_Address_db, Toast.LENGTH_SHORT).show();


        hotelId.setText("Hotel ID" +hotel_Id_db);
        hotelId.setTextColor(Color.BLUE);

        hotelname.setText(hotel_name_db);
        hotelname.setTextColor(Color.RED);
        hotelAddress.setText(hotel_Address_db);
        hotelCostOfTwo.setText("CostOFTwo "+ hotel_costfortew_db);
        hotelCostOfTwo.setTextColor(Color.rgb(0	,127,255));
        hotelCuision.setText( hotel_cuision_db);
        hotelCuision.setTextColor(Color.rgb(0	,127,255));

        Picasso.with(getActivity()).load(hotel_Image_url_db).into(hotelImage);
        Picasso.with(getActivity()).load(hotel_Menu_url_db).into(hotelMenu_card);


        //review
        ReviewshowTextview.setText(review);
        RatingshowTextview.setText("your last rating : " +rating);
        RatingshowTextview.setTextColor(Color.RED);


        Reviewsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getArguments();
                String hotelIdForSearchdataa = bundle.getString("hotelId");

                String ratingnumbercheck = null;
                ratingnumbercheck  = (EnterRating.getText().toString());
                String reviewcheck = null;
                       reviewcheck = String.valueOf(EnterRating.getText());


                 if (!ratingnumbercheck.equals("") && !reviewcheck.equals("")) {

                     int rating = 0;
                          rating =  Integer.parseInt(ratingnumbercheck);

                     if ( rating    >= 0 && rating <= 5) {
                         fooddatabase.UpdateReview(hotelIdForSearchdataa, String.valueOf(EnterReview.getText()), String.valueOf(EnterRating.getText()));
                     } else {
                         Toast.makeText(getActivity(), "Please enter the number 0 to 5", Toast.LENGTH_SHORT).show();
                     }
                 }

                else {
                     Toast.makeText(getActivity(), "Enter Review and rating in Review and rating box", Toast.LENGTH_SHORT).show();
                 }

            }
        });



        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "button is click", Toast.LENGTH_SHORT).show();
                Bundle bundle = getArguments();
                Double Lattitude = Double.valueOf(bundle.getString("latitude"));
                Double Logitude  = Double.valueOf(bundle.getString("logitude"));
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)",Lattitude,Logitude, "Where the party is at");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

            }
        });

        return view;
    }





}
