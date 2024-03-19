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
}
