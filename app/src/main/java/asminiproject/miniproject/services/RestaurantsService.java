package asminiproject.miniproject.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import asminiproject.miniproject.controllers.DataBaseController;
import asminiproject.miniproject.dc.Restaurant;

public class RestaurantsService {
    private static RestaurantsService _instance;
    private static final String TAG = "RestaurantsService";

    private DataBaseController _databaseController;

    private List<Restaurant> _restaurants;

    private RestaurantsService() {
        _instance = this;
        _databaseController = DataBaseController.getInstance();
    }

    public static RestaurantsService getInstance() {
        if (RestaurantsService._instance == null) {
            new RestaurantsService();
        }

        return _instance;
    }

    public List<Restaurant> getRestaurants() {
        _restaurants = DataBaseController.getInstance().getRestaurants();
        return _restaurants;
    }
    public Restaurant getRestaurantById(int id) {
        return _restaurants.get(id);
    }

    public void getAllRestaurants(FirebaseFirestore database, SimpleCallback<List<Restaurant>> callback) {
        if (database == null) throw new IllegalArgumentException("Database is null.");

        List<Restaurant> allRestaurants = new ArrayList<>();

        CollectionReference collectionReferenceRestaurant = database.collection("Restaurant");
        collectionReferenceRestaurant.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    task.getResult().forEach(restaurantDocument -> {
                        allRestaurants.add(restaurantDocument.toObject(Restaurant.class));
                    });
                }
                callback.callback(allRestaurants);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "An error occurred while fetching all the restaurants." + "\n" +
                        e.getMessage() + "\n" + e);
            }
        });
    }
}
