package asminiproject.miniproject.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asminiproject.miniproject.dc.Restaurant;
import asminiproject.miniproject.dc.Review;
import asminiproject.miniproject.misc.SimpleCallback;

public class ReviewService {
    private static final String TAG = "RestaurantService";
    private static final String COLLECTION_PATH = "Review";
    private final FirebaseFirestore _database;

    private Map<String, Review> _reviewsCache;

    public void getAllRestaurants(SimpleCallback<List<Review>> callback) {

        // Query cached ?
        if (_reviewsCache != null) {
            callback.onDataUpdate(new ArrayList<>(_reviewsCache.values()));
            return;
        }

        Map<String, Review> review = new HashMap<>();
        CollectionReference collectionReferenceRestaurant = _database.collection(COLLECTION_PATH);
        collectionReferenceRestaurant
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        task.getResult().forEach(restaurantDocument -> {
                            review.put(
                                restaurantDocument.getId(),
                                restaurantDocument.toObject(Review.class));
                        });
                    }

                    // Save the result on cache
                    _reviewsCache = review;

                    callback.onDataUpdate(new ArrayList<>(_reviewsCache.values()));
                }})
            .addOnFailureListener(e -> Log.e(TAG, "An error occurred while fetching all the restaurants."));
    }

    /*
        Singleton properties
     */
    private static ReviewService _instance;
    private ReviewService() {
        _instance = this;
        _database = FirebaseFirestore.getInstance();
    }
    public static ReviewService getInstance() {
        if (ReviewService._instance == null) {
            new ReviewService();
        }

        return _instance;
    }

    public boolean addReview(Review review) {
        return true;
    }
}
