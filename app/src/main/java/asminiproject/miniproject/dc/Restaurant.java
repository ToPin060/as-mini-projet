package asminiproject.miniproject.dc;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    public int id;
    public String name;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GeoPoint getLocalization() {
        return localization;
    }

    public void setLocalization(GeoPoint localization) {
        this.localization = localization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

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

        compute();
    }
    public Restaurant(int id_, String name_, String address_, GeoPoint localization_, String phone_, Schedule schedule_, List<Review> ratings_) {
        id = id_;
        name = name_;
        address = address_;
        phone = phone_;
        schedule = schedule_;
        localization = localization_;
        reviews = ratings_;

        compute();
    }

    /**
     *  Compute all computed attributes
     */
    private void compute() {
        ratingsNumber = reviews.size();

        float sum = 0;
        for (int i = 0; i < ratingsNumber; i++) {
            sum += reviews.get(i).rating;
        }
        overallRating = (float)((int) (((float) sum)/ratingsNumber * 10) / 10);
    }
}
