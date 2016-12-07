package com.example.shubham_v.foodie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.provider.SyncStateContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubham_v.foodie.HotelInfoFragment.HotelInfo;
import com.example.shubham_v.foodie.ModelClass.NearResponse;
import com.example.shubham_v.foodie.ModelClass.RestLatLong;
import com.example.shubham_v.foodie.RestApis.ApiClient;
import com.example.shubham_v.foodie.RestApis.ApiInterface;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    final ArrayList<RestLatLong> restNameLatLongsList = new ArrayList<>();
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this,marker.getTitle(), Toast.LENGTH_SHORT).show();
        HotelInfo hotelInfo  = new HotelInfo();
        FragmentManager fragmentManager = getFragmentManager();
        mapFragment.getView().setVisibility(View.GONE);
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
                    LatLng latLng = new LatLng(Double.parseDouble(response.body().getRestaurant().get(i).getRestaurantDetail().getLocations().getLattiudes())
                            ,Double.parseDouble(response.body().getRestaurant().get(i).getRestaurantDetail().getLocations().getLongitudes()));
                    RestLatLong restLatLong = new RestLatLong(resNam,latLng);
                    restNameLatLongsList.add(restLatLong);
                    SetMarkerOnMap(resNam,latLng);
                    Log.d("this  is array size ", String.valueOf(restNameLatLongsList.size()));
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
