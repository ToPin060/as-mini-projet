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

import asminiproject.miniproject.firebase.model.User;

public class UserService {
    private static final String TAG = "UserService";
    private static final String ENTITY_NAME = "User";

    private static Boolean isUserInvalid(User user) {
        return user.getAddress() == null ||
                user.getName().isEmpty() ||
                user.getFirstName().isEmpty() ||
                user.getPseudo().isEmpty();
    }

    public static void addUser(FirebaseFirestore database, User user) {
        if (database == null || user == null || isUserInvalid(user))
            throw new IllegalArgumentException("Database or user is null or user has unset fields.");

        CollectionReference collectionReferenceUser = database.collection(ENTITY_NAME);

        collectionReferenceUser.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "Successfully added user to db. \n" + user.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "An error occurred while adding the restaurant. \n"
                        + e.getMessage() + "\n" + e);
            }
        });
    }

    public static void getAllUsers(FirebaseFirestore database, SimpleCallback<List<User>> simpleCallback) {
        if (database == null) throw new IllegalArgumentException("Database is null.");

        List<User> allUsers = new ArrayList<>();

        CollectionReference collectionReferenceUser = database.collection(ENTITY_NAME);

        collectionReferenceUser.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    task.getResult().forEach(userDocument -> {
                        allUsers.add(userDocument.toObject(User.class));
                    });
                }
                simpleCallback.callback(allUsers);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "An error occurred while adding the restaurant. \n"
                        + e.getMessage() + "\n" + e);
            }
        });
    }
}
