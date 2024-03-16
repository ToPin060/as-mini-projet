package asminiproject.miniproject.firebase.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddressController {

    private static final String TAG = "AddressController.class";
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference tableAddress = firestore.collection("Address");

    public void addAddress(Address address) {
        if (address == null) throw new IllegalArgumentException("Address to add to FireStore is null.");
        tableAddress.add(address).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "Address " + address + " successfully added to db.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error when adding address to db. \n" + e.getMessage() + "\n" + e);
            }
        });
    }

    public List<Address> getAllAddresses() {
        List<Address> allAddresses = new ArrayList<>();
        tableAddress.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        allAddresses.add(documentSnapshot.toObject(Address.class));
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Could not read Address table. \n" +
                        e + "\n" +
                        e.getMessage());
            }
        });

        if (allAddresses.size() == 0) {
            return Collections.emptyList();
        } else {
            return allAddresses;
        }
    }
}
