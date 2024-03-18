package asminiproject.miniproject.dc;

import java.util.List;

public class Review {
    public int id;
    public Restaurant restaurant;
    public float rating;
    public String review;
    public List<String> picturePaths;


    public Review() { }

    public Review(int id_, Restaurant restaurant_, float rating_, String review_, List<String> picturePaths_) {
        id = id_;
        restaurant = restaurant_;
        rating = rating_;
        review = review_;
        picturePaths = picturePaths_;
    }
}
