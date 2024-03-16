package asminiproject.miniproject.firebase.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import asminiproject.miniproject.firebase.model.Restaurant;

public class RestaurantService {
    private static final String TAG = "RestaurantService";
    private static final String ENTITY_NAME = "Restaurant";

    private static Boolean isRestaurantInvalid(Restaurant restaurant) {
        return restaurant.getMeals().isEmpty() ||
                        restaurant.getAddress() == null ||
                        restaurant.getName().isEmpty();
    }

    public static void addRestaurant(FirebaseFirestore database, Restaurant restaurant) {
        if (restaurant == null || database == null || isRestaurantInvalid(restaurant))
            throw new IllegalArgumentException("Restaurant is null or required field is missing.");

        CollectionReference collectionReferenceRestaurant = database.collection(ENTITY_NAME);
        collectionReferenceRestaurant.add(restaurant).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "Successfully added restaurant to db: \n" + restaurant.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "An error occurred while adding the restaurant. \n"
                + e.getMessage() + "\n" + e);
            }
        });
    }

    public static void getAllRestaurants(FirebaseFirestore database, SimpleCallback<List<Restaurant>> callback) {
        if (database == null) throw new IllegalArgumentException("Database is null.");

        List<Restaurant> allRestaurants = new ArrayList<>();

        CollectionReference collectionReferenceRestaurant = database.collection(ENTITY_NAME);
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
