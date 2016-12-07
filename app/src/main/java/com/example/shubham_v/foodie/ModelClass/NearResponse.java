package com.example.shubham_v.foodie.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shubham_v on 06-12-2016.
 */

public class NearResponse {

        @SerializedName("nearby_restaurants")
       private ArrayList<Restaurant> restaurant;

    public ArrayList<Restaurant> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(ArrayList<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }


    public  class  Restaurant{

        @SerializedName("restaurant")
        private RestaurantDetail restaurantDetail;

        public RestaurantDetail getRestaurantDetail() {
            return restaurantDetail;
        }

        public void setRestaurantDetail(RestaurantDetail restaurantDetail) {
            this.restaurantDetail = restaurantDetail;
        }


        public class RestaurantDetail{

            @SerializedName("name")
            private String name;
            @SerializedName("cuisines")
            private String cuisines;
            @SerializedName("average_cost_for_two")
            private int average_cost_for_two;
            @SerializedName("thumb")
            private String thumb;
            @SerializedName("location")
            private Locations locations;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCuisines() {
                return cuisines;
            }

            public void setCuisines(String cuisines) {
                this.cuisines = cuisines;
            }

            public int getAverage_cost_for_two() {
                return average_cost_for_two;
            }

            public void setAverage_cost_for_two(int average_cost_for_two) {
                this.average_cost_for_two = average_cost_for_two;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public Locations getLocations() {
                return locations;
            }

            public void setLocations(Locations locations) {
                this.locations = locations;
            }

            public class Locations{

            @SerializedName("address")
            private String address;
            @SerializedName("latitude")
            private String lattiudes;
            @SerializedName("longitude")
             private String longitudes;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getLattiudes() {
                    return lattiudes;
                }

                public void setLattiudes(String lattiudes) {
                    this.lattiudes = lattiudes;
                }

                public String getLongitudes() {
                    return longitudes;
                }

                public void setLongitudes(String longitudes) {
                    this.longitudes = longitudes;
                }
            }


        }
    }
}
