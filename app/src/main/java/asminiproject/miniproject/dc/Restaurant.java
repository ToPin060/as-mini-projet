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
    public List<Rating> ratings;

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
        ratings = new ArrayList<Rating>();

        computeAttributes();
    }
    public Restaurant(int id_, String name_, String address_, GeoPoint localization_, String phone_, Schedule schedule_, List<Rating> ratings_) {
        id = id_;
        name = name_;
        address = address_;
        phone = phone_;
        schedule = schedule_;
        localization = localization_;
        ratings = ratings_;

        computeAttributes();
    }

    private void computeAttributes() {
        ratingsNumber = ratings.size();

        int sum = 0;
        for (int i = 0; i < ratingsNumber; i++) {
            sum += ratings.get(i).value;
        }
        overallRating = (float)((int) (((float) sum)/ratingsNumber * 10) / 10);
    }
}
