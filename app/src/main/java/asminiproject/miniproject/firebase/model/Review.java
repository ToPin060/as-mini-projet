package asminiproject.miniproject.firebase.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Review {
    private Restaurant restaurant;
    private User user;
    private float rating;
    private List<String> photos;
    private String review;

    public Review() { }

    public Review(Restaurant _restaurant, User _user, float _rating, List<String> _photos, String _review) {
        this.restaurant = _restaurant;
        this.user = _user;
        this.rating = _rating;
        this.photos = _photos;
        this.review = _review;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("*********************************\n");
        sb.append("Review : \n");
        sb.append("Reviewer : ");
        sb.append(this.getUser().toString());
        sb.append("\n Restaurant : ");
        sb.append(this.getRestaurant().toString());
        sb.append("\n Rating : ");
        sb.append(this.getRating());
        sb.append("\n Review : ");
        sb.append(this.getReview());
        sb.append("*********************************\n");

        return sb.toString();
    }
}
