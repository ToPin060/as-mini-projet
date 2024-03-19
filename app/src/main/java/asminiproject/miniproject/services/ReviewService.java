package asminiproject.miniproject.services;

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

import asminiproject.miniproject.firebase.model.Review;

public class ReviewService {
    private static final String TAG = "ReviewService";
    private static final String ENTITY_NAME = "Restaurant";

    private static Boolean isReviewInvalid(Review review) {
        return review.getReview().isEmpty()
                || review.getUser() == null
                || review.getRestaurant() == null;
    }

    public static void addReview(FirebaseFirestore database, Review review) {
        if (database == null || review == null || isReviewInvalid(review))
            throw new IllegalArgumentException("Database or review is null.");

        CollectionReference collectionReferenceReview = database.collection(ENTITY_NAME);

        collectionReferenceReview.add(review).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "Successfully added review to db: \n" + review.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "An error occurred while adding the review. \n"
                        + e.getMessage() + "\n" + e);
            }
        });
    }

    public static void getAllReviews(FirebaseFirestore database, SimpleCallback<List<Review>> simpleCallback) {
        if (database == null) throw new IllegalArgumentException("Database is null.");

        List<Review> allReviews = new ArrayList<>();

        CollectionReference collectionReferenceReview = database.collection(ENTITY_NAME);

        collectionReferenceReview.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    task.getResult().forEach(reviewDocument -> {
                        allReviews.add(reviewDocument.toObject(Review.class));
                    });
                }
                simpleCallback.callback(allReviews);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "An error occurred while fetching all the reviews." + "\n" +
                        e.getMessage() + "\n" + e);
            }
        });
    }
}
