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
import asminiproject.miniproject.misc.SimpleCallback;

public class RestaurantService {
    private static final String TAG = "RestaurantService";
    private static final String COLLECTION_PATH = "Restaurant";
    private final FirebaseFirestore _database;

    private Map<String, Restaurant> _cache;

    public void getAllRestaurants(SimpleCallback<List<Restaurant>> callback) {

        // Query cached ?
        if (_cache != null) {
            callback.onDataUpdate(new ArrayList<>(_cache.values()));
            return;
        }

        Map<String, Restaurant> restaurants = new HashMap<>();
        CollectionReference collectionReferenceRestaurant = _database.collection(COLLECTION_PATH);
        collectionReferenceRestaurant
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        task.getResult().forEach(document -> {
                            restaurants.put(
                                document.getId(),
                                document.toObject(Restaurant.class));
                        });
                    }

                    // Save the result on cache
                    _cache = restaurants;

                    callback.onDataUpdate(new ArrayList<>(_cache.values()));
                }})
            .addOnFailureListener(e -> Log.e(TAG, "An error occurred while fetching all the restaurants."));
    }

    public Restaurant getRestaurantById(String id) {
        if (_cache.get(id) == null) throw new RuntimeException();
        return _cache.get(id);
    }

    /*
        Singleton properties
     */
    private static RestaurantService _instance;
    private RestaurantService() {
        _instance = this;
        _database = FirebaseFirestore.getInstance();
    }
    public static RestaurantService getInstance() {
        if (RestaurantService._instance == null) {
            new RestaurantService();
        }

        return _instance;
    }
}
