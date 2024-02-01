package asminiproject.miniproject.dc;

import android.graphics.Bitmap;

import java.util.List;

public class Review {
    public int id;
    public Restaurant restaurant;
    public float rating;
    public String review;
    public List<Bitmap> picturePaths;


    public Review() { }

    public Review(Restaurant restaurant_, float rating_, String review_, List<Bitmap> picturePaths_) {
        restaurant = restaurant_;
        rating = rating_;
        review = review_;
        picturePaths = picturePaths_;
    }

    public Review(int id_, Restaurant restaurant_, float rating_, String review_, List<Bitmap> picturePaths_) {
        id = id_;
        restaurant = restaurant_;
        rating = rating_;
        review = review_;
        picturePaths = picturePaths_;
    }

    public int getId() {
        return this.id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public List<Bitmap> getPicturePaths() {
        return picturePaths;
    }

    public void setPicturePaths(List<Bitmap> picturePaths) {
        this.picturePaths = picturePaths;
    }
}
