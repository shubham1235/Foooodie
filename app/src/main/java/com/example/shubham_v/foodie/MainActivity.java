package com.example.shubham_v.foodie;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.shubham_v.foodie.Database.Fooddatabase;
import com.example.shubham_v.foodie.ModelClass.NearResponse;
import com.example.shubham_v.foodie.ModelClass.RestLatLong;
import com.example.shubham_v.foodie.RestApis.ApiClient;
import com.example.shubham_v.foodie.RestApis.ApiInterface;
import com.example.shubham_v.foodie.frahment.HotelInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    final ArrayList<RestLatLong> restNameLatLongsList = new ArrayList<>();
    SupportMapFragment mapFragment;
    HotelInfo hotelInfo;
    public Fooddatabase fooddatabase;

    int hotelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fooddatabase = new Fooddatabase(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
                  mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);

        Zomatodata();
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(12.983264,77.585473) , 13.0f) );

       }

    public void SetMarkerOnMap(String hotelName,LatLng hotelLocation) // array list type function
    {
        mMap.addMarker(new MarkerOptions().position(hotelLocation).title(hotelName));


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            mapFragment.getView().setVisibility(View.VISIBLE);
            hotelInfo.getView().setVisibility(View.GONE);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        String hotelName = marker.getTitle();
        LatLng latLng = marker.getPosition();

        Toast.makeText(this,marker.getTitle(), Toast.LENGTH_SHORT).show();
              hotelInfo   = new HotelInfo();

        Bundle bundle = new Bundle();
        bundle.putString("hotelId", hotelName);
        bundle.putString("latitude", String.valueOf(latLng.latitude));
        bundle.putString("logitude", String.valueOf(latLng.longitude));

        hotelInfo.setArguments(bundle);


        FragmentManager fragmentManager = getFragmentManager();
        mapFragment.getView().setVisibility(View.GONE);

        // send data to fragment

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.hotelInfoFragment,hotelInfo).commit();
        marker.getTitle();


        return false;
    }


  public  ArrayList<RestLatLong> Zomatodata()
    {

        String lat = "12.983675";
        String lon = "77.585521";
        final ApiInterface apiServices = ApiClient.getClient().create(ApiInterface.class);
        Call<NearResponse> call = apiServices.getRestaurants(lat,lon);

        call.enqueue(new Callback<NearResponse>() {
            @Override
            public void onResponse(Response<NearResponse> response, Retrofit retrofit) {

                for(int i=0;i<response.body().getRestaurant().size();i++) {

                    String resNam = response.body().getRestaurant().get(i).getRestaurantDetail().getName();
                    int hotelId = response.body().getRestaurant().get(i).getRestaurantDetail().getHotelId();


                    String cuision = response.body().getRestaurant().get(i).getRestaurantDetail().getCuisines();
                    int costOfTwo  = response.body().getRestaurant().get(i).getRestaurantDetail().getAverage_cost_for_two();
                    String htl_img_url =  response.body().getRestaurant().get(i).getRestaurantDetail().getThumb();
                    String hotel_address =response.body().getRestaurant().get(i).getRestaurantDetail().getLocations().getAddress();
                    String hotel_menu_url =   response.body().getRestaurant().get(i).getRestaurantDetail().getMenuUrl();
                    LatLng latLng = new LatLng(Double.parseDouble(response.body().getRestaurant().get(i).getRestaurantDetail().getLocations().getLattiudes())
                            ,Double.parseDouble(response.body().getRestaurant().get(i).getRestaurantDetail().getLocations().getLongitudes()));
                    SetMarkerOnMap(resNam,latLng);
                    fooddatabase.InsertHotelDataDB(String.valueOf((hotelId)),resNam,cuision, String.valueOf((costOfTwo)),hotel_address,hotel_menu_url,htl_img_url);
                    Log.d("this  is array size ", String.valueOf(hotelId));
                    }


            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
          return restNameLatLongsList;
    }

}
