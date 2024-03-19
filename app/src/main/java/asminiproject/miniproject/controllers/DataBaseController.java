package asminiproject.miniproject.controllers;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import asminiproject.miniproject.dc.Restaurant;
import asminiproject.miniproject.dc.Review;
import asminiproject.miniproject.dc.Schedule;
import asminiproject.miniproject.dc.TimeSlot;
import asminiproject.miniproject.services.RestaurantsService;

public class DataBaseController {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private static DataBaseController _instance;
    private DataBaseController() {
        _instance = this;
    }

    private static Boolean isReviewInvalid(Review review) {
        return review.getReview().isEmpty()
                || review.getRestaurant() == null;
    }

    public List<Restaurant> getRestaurants() {
        List<Restaurant> allRestaurants = new ArrayList<>();
        RestaurantsService.getInstance().getAllRestaurants(database, allRestaurants::addAll);

        // Convert as Restaurant from DC
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        for (int i = 0; i < 10; i++) {
            GeoPoint localization = new GeoPoint(
                    43.6047 + (Math.random() - 0.5) * 0.01,
                    1.4442 + (Math.random() - 0.5) * 0.01);

            ArrayList<Pair<TimeSlot, TimeSlot>> schedules = new ArrayList<Pair<TimeSlot, TimeSlot>>();
            schedules.add(new Pair<>(new TimeSlot(11,00,15,00), null));
            schedules.add(new Pair<>(new TimeSlot(11,00,15,00), null));
            schedules.add(new Pair<>(new TimeSlot(11,00,15,00), null));
            schedules.add(new Pair<>(new TimeSlot(11,00,15,00), null));
            schedules.add(new Pair<>(new TimeSlot(11,00,15,00), null));
            schedules.add(new Pair<>(
                new TimeSlot(11, 00, 15, 00),
                new TimeSlot(19, 00, 23, 30)));
            schedules.add(new Pair<>(new TimeSlot(19, 00, 23, 30), null));

            Restaurant restaurant = new Restaurant(
                    i, "Restaurant " + i,
                    "7 Rue Aristide Berges, 31270 Cugnaux", localization,
                    "05 61 59 32 80", new Schedule(schedules));

            restaurant.ratingsNumber = (int) (Math.random()*100);
            restaurant.overallRating = Math.abs((int) (Math.random()*10) - 5);

            restaurants.add(restaurant);
        }

        return Stream.of(restaurants, allRestaurants)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public boolean addReview(Review review) {
        // TODO: Database insertion

        // Insertion status
        return true;
    }

    public void addReviewV2(Review review) {
        if (database == null || review == null || isReviewInvalid(review))
            throw new IllegalArgumentException("Database or review is null.");

        CollectionReference collectionReferenceReview = database.collection("Review");

        collectionReferenceReview.add(review).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("ReviewService", "Successfully added review to db: \n" + review.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ReviewService", "An error occurred while adding the review. \n"
                        + e.getMessage() + "\n" + e);
            }
        });
    }

    public static DataBaseController getInstance() {
        if (DataBaseController._instance == null) {
            new DataBaseController();
        }
        return _instance;
    }
}
