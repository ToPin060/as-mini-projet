package asminiproject.miniproject.dc;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    public int id;
    public String name;
    public String address;
    public GeoPoint localization;
    public String phone;
    public Schedule schedule;
    public List<Review> reviews;

    public float overallRating;
    public int ratingsNumber;

    public Restaurant() { }
    public Restaurant(int id_, String name_, String address_, GeoPoint localization_, String phone_, Schedule schedule_) {
        id = id_;
        name = name_;
        address = address_;
        localization = localization_;
        phone = phone_;
        schedule = schedule_;
        reviews = new ArrayList<Review>();

        computeAttributes();
    }
    public Restaurant(int id_, String name_, String address_, GeoPoint localization_, String phone_, Schedule schedule_, List<Review> ratings_) {
        id = id_;
        name = name_;
        address = address_;
        phone = phone_;
        schedule = schedule_;
        localization = localization_;
        reviews = ratings_;

        computeAttributes();
    }

    private void computeAttributes() {
        ratingsNumber = reviews.size();

        float sum = 0;
        for (int i = 0; i < ratingsNumber; i++) {
            sum += reviews.get(i).rating;
        }
        overallRating = (float)((int) (((float) sum)/ratingsNumber * 10) / 10);
    }
}
